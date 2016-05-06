package io.github.artfly.ridetogether.server.web;

import io.github.artfly.ridetogether.server.service.EventService;
import io.github.artfly.ridetogether.server.service.SubscribeService;
import io.github.artfly.ridetogether.server.service.security.CurrentUser;
import io.github.artfly.ridetogether.server.web.dto.event.EventDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {
    private static final String DEFAULT_COUNT = "20";
    private final EventService eventService;
    private final SubscribeService subscribeService;

    @Autowired
    EventController(EventService eventService, SubscribeService subscribeService) {
        this.eventService = eventService;
        this.subscribeService = subscribeService;
    }


    @RequestMapping(method = RequestMethod.GET)
    ResponseEntity<List<EventDto>> getEvents(@RequestParam(value = "place") String placeId,
                                             @RequestParam(value = "count", required = false, defaultValue = DEFAULT_COUNT) Integer count,
                                             @RequestParam(value = "since", required = false) Long since,
                                             @RequestParam(value = "route", required = false) String routeType) {

        return new ResponseEntity<>(eventService.getEvents(placeId, count, since, routeType), HttpStatus.OK);
    }


    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<EventDto> postEvent(@AuthenticationPrincipal CurrentUser currentUser, @RequestBody EventDto eventDto) {
        return new ResponseEntity<>(eventService.createEvent(currentUser, eventDto), HttpStatus.CREATED);
    }

    @RequestMapping(value = "{eventId}", method = RequestMethod.GET)
    ResponseEntity<EventDto> getEvent(@PathVariable Long eventId) {
        return new ResponseEntity<>(eventService.readEvent(eventId), HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "{eventId}", method = RequestMethod.DELETE)
    public ResponseEntity<HttpStatus> deleteEvent(@AuthenticationPrincipal CurrentUser currentUser, @PathVariable Long eventId) {
        eventService.deleteEvent(currentUser, eventId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "{eventId}/subscribe", method = RequestMethod.PUT)
    ResponseEntity<HttpStatus> addSubscriber(@AuthenticationPrincipal CurrentUser currentUser, @PathVariable Long eventId) {
        subscribeService.subscribe(currentUser, eventId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "{eventId}/unsubscribe", method = RequestMethod.POST)
    ResponseEntity<HttpStatus> removeSubscriber(@AuthenticationPrincipal CurrentUser currentUser, @PathVariable Long eventId) {
        subscribeService.unsubscribe(currentUser, eventId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "{eventId}", method = RequestMethod.PUT)
    ResponseEntity<HttpStatus> updateEvent(@AuthenticationPrincipal CurrentUser currentUser, @PathVariable Long eventId,
                                           @RequestBody EventDto eventDto) {
        eventService.updateEvent(currentUser, eventDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
