package io.github.artfly.ridetogether.server.service;

import io.github.artfly.ridetogether.server.repository.EventRepository;
import io.github.artfly.ridetogether.server.repository.entities.Event;
import io.github.artfly.ridetogether.server.service.security.CurrentUser;
import io.github.artfly.ridetogether.server.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SubscribeServiceImpl implements SubscribeService {
    private final EventRepository eventRepository;

    @Autowired
    public SubscribeServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }


    @Override
    public void subscribe(CurrentUser user, Long eventId) {
        Event event = Utils.validate(eventId, eventRepository);
        event.addSubscriber(user.getUser());
        eventRepository.save(event);
    }

    @Override
    public void unsubscribe(CurrentUser user, Long eventId) {
        Event event = Utils.validate(eventId, eventRepository);
        event.removeSubscriberOrParticipant(user.getId());
        eventRepository.save(event);
    }
}
