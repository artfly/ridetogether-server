package io.github.artfly.ridetogether.server.service;

import io.github.artfly.ridetogether.server.repository.EventRepository;
import io.github.artfly.ridetogether.server.repository.entities.Event;
import io.github.artfly.ridetogether.server.repository.entities.User;
import io.github.artfly.ridetogether.server.service.security.CurrentUser;
import io.github.artfly.ridetogether.server.utils.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SubscribeServiceImpl implements SubscribeService {
    private final EventRepository eventRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public SubscribeServiceImpl(EventRepository eventRepository, ModelMapper modelMapper) {
        this.eventRepository = eventRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public void subscribe(CurrentUser user, Long eventId) {
        Event event = Utils.validate(eventId, eventRepository);
        event.addSubscriber(modelMapper.map(user.getUser(), User.class));
        eventRepository.save(event);
    }

    @Override
    public void unsubscribe(CurrentUser user, Long eventId) {
        Event event = Utils.validate(eventId, eventRepository);
        event.removeSubscriberOrParticipant(user.getId());
        eventRepository.save(event);
    }
}
