package io.github.artfly.ridetogether.server.service;

import io.github.artfly.ridetogether.server.repository.EventRepository;
import io.github.artfly.ridetogether.server.repository.ImageRepository;
import io.github.artfly.ridetogether.server.repository.RouteRepository;
import io.github.artfly.ridetogether.server.repository.entities.Event;
import io.github.artfly.ridetogether.server.repository.entities.User;
import io.github.artfly.ridetogether.server.service.exceptions.AuthorizeException;
import io.github.artfly.ridetogether.server.service.security.CurrentUser;
import io.github.artfly.ridetogether.server.utils.Utils;
import io.github.artfly.ridetogether.server.web.dto.event.EventDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {
    private static final int MAX_COUNT = 200;
    private final EventRepository eventRepository;
    private final RouteRepository routeRepository;
    private final ImageRepository imageRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository, RouteRepository routeRepository,
                            ImageRepository imageRepository, ModelMapper modelMapper) {
        this.eventRepository = eventRepository;
        this.routeRepository = routeRepository;
        this.imageRepository = imageRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<EventDto> getEvents(String place, Integer count, Long since, String routeType) {
        Pageable pageable;
        Type eventDtoType = new TypeToken<List<EventDto>>() {
        }.getType();
        List<Event> events;
        if (count > MAX_COUNT) {
            pageable = new PageRequest(0, MAX_COUNT);
        } else {
            pageable = new PageRequest(0, count);
        }

        if (since != null && routeType != null) {
            events = eventRepository.findByDateLessThanAndRoute_PlaceIdEqualsAndRoute_RouteTypeEquals(since, place, routeType, pageable);
        } else if (since != null) {
            events = eventRepository.findByDateLessThanAndRoute_PlaceIdEquals(since, place, pageable);
        } else if (routeType != null) {
            events = eventRepository.findByRoute_RouteTypeEqualsAndRoute_PlaceIdEquals(routeType, place, pageable);
        } else {
            events = eventRepository.findByRoute_PlaceIdEquals(place, pageable);
        }
        return modelMapper.map(events, eventDtoType);
    }

    @Override
    public EventDto createEvent(CurrentUser currentUser, EventDto eventDto) {
        Utils.validate(eventDto.getImagePath(), imageRepository);
        Utils.validate(eventDto.getRouteId(), routeRepository);
        Event event = modelMapper.map(eventDto, Event.class);
        User creator = currentUser.getUser();
        event.setCreator(creator);
        event.addParticipant(creator);
        eventRepository.save(event);
        return modelMapper.map(event, EventDto.class);
    }

    @Override
    public EventDto readEvent(Long eventId) {
        return modelMapper.map(Utils.validate(eventId, eventRepository), EventDto.class);
    }

    @Override
    public void updateEvent(CurrentUser currentUser, EventDto eventDto) {
        Event event = Utils.validate(eventDto.getId(), eventRepository);
        if (!currentUser.getUsername().equals(event.getCreator().getUsername())) {
            throw new AuthorizeException(currentUser.getUsername());
        }
        event.setRoute(Utils.validate(eventDto.getRouteId(), routeRepository));
        event.setImage(Utils.validate(eventDto.getImagePath(), imageRepository));
        event.setTitle(eventDto.getTitle());
        event.setDescription(eventDto.getDescription());
        event.setDate(eventDto.getDate());
        eventRepository.save(event);
    }

    @Override
    public void deleteEvent(CurrentUser currentUser, Long eventId) {
        Event event = Utils.validate(eventId, eventRepository);
        if (!currentUser.getUsername().equals(event.getCreator().getUsername())) {
            throw new AuthorizeException(currentUser.getUsername());
        }
        eventRepository.delete(eventId);
    }
}
