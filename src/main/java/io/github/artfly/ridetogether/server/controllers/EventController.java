package io.github.artfly.ridetogether.server.controllers;

import io.github.artfly.ridetogether.server.entities.Event;
import io.github.artfly.ridetogether.server.repositories.EventRepository;
import io.github.artfly.ridetogether.server.repositories.ImageRepository;
import io.github.artfly.ridetogether.server.repositories.RouteRepository;
import io.github.artfly.ridetogether.server.repositories.UserRepository;
import io.github.artfly.ridetogether.server.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final RouteRepository routeRepository;
    private final ImageRepository imageRepository;

    @Autowired
    EventController(EventRepository eventRepository,
                    UserRepository userRepository, RouteRepository routeRepository, ImageRepository imageRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.routeRepository = routeRepository;
        this.imageRepository = imageRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    ResponseEntity<List<Event>> getEvents(@RequestParam(value = "count", required = false) Integer count,
                                          @RequestParam(value = "since", required = false) Long since,
                                          @RequestParam(value = "region", required = false  ) Long regionId) {
        count = count > 20 ? 20 : count;
        return new ResponseEntity<>(
                eventRepository.findByDateLessThanAndPlaceIdEquals(since, regionId, new PageRequest(0, count)), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<Event> postEvent(@RequestBody Event event) {
        Utils.validate(event.getCreatorId(), userRepository);
        Utils.validate(event.getRouteId(), routeRepository);
        Utils.validate(event.getImagePath(), imageRepository);
        event.setRoute(routeRepository.getOne(event.getRouteId()));
        return new ResponseEntity<>(eventRepository.save(event), HttpStatus.CREATED);
    }

    @RequestMapping(value = "{event_id}", method = RequestMethod.GET)
    ResponseEntity<Event> getEvent(@PathVariable Long eventId) {
        Utils.validate(eventId, eventRepository);
        return new ResponseEntity<>(eventRepository.findOne(eventId), HttpStatus.OK);
    }

    @RequestMapping(value = "{event_id}", method = RequestMethod.DELETE)
    ResponseEntity<HttpStatus> deleteEvent(@PathVariable Long eventId) {
        Utils.validate(eventId, eventRepository);
        eventRepository.delete(eventId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "{event_id}", method = RequestMethod.PUT)
    ResponseEntity<HttpStatus> updateEvent(@PathVariable Long eventId, @RequestBody Event event) {
        Utils.validate(eventId, eventRepository);
        Utils.validate(event.getCreatorId(), userRepository);
        Utils.validate(event.getRouteId(), routeRepository);
        Utils.validate(event.getImagePath(), imageRepository);
        Event dbEvent = eventRepository.findOne(eventId);
        dbEvent.setDescription(event.getDescription());
        dbEvent.setImage(imageRepository.findOne(   event.getImagePath()));
        dbEvent.setTitle(event.getTitle());
        dbEvent.setDate(event.getDate());
        dbEvent.setRoute(routeRepository.findOne(event.getRouteId()));
        dbEvent.setPlaceId(event.getPlaceId());
        eventRepository.save(dbEvent);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
//    TODO : tokens, subs
//    @RequestMapping(value = "{event_id}/subscribe", method = RequestMethod.PUT)
//    ResponseEntity<Event> subscribeToEvent()
}
