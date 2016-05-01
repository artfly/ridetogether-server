package io.github.artfly.ridetogether.server.controllers;

import io.github.artfly.ridetogether.server.entities.Event;
import io.github.artfly.ridetogether.server.repositories.EventRepository;
import io.github.artfly.ridetogether.server.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/events")
public class EventController {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Autowired
    EventController(EventRepository eventRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "{event_id}", method = RequestMethod.GET)
    Event getEvent(@PathVariable Long eventId) {
        return this.eventRepository.findOne(eventId);
    }

    @RequestMapping(method = RequestMethod.POST)
    Event postEvent(@RequestBody @Valid Event event) {
        return eventRepository.save(event);
    }
}
