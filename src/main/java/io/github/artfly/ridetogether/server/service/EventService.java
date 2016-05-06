package io.github.artfly.ridetogether.server.service;


import io.github.artfly.ridetogether.server.service.security.CurrentUser;
import io.github.artfly.ridetogether.server.web.dto.EventDto;

import java.util.List;

public interface EventService {
    List<EventDto> getEvents(String place, Integer count, Long since, String routeType);
    EventDto createEvent(CurrentUser currentUser, EventDto eventDto);
    EventDto readEvent(Long eventId);
    void updateEvent(CurrentUser currentUser, EventDto eventDto);
    void deleteEvent(CurrentUser currentUser, Long eventId);
}
