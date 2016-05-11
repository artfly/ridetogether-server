package io.github.artfly.ridetogether.server.service;

import io.github.artfly.ridetogether.server.service.security.CurrentUser;

public interface SubscribeService {
    void subscribe(CurrentUser user, Long eventId);

    void unsubscribe(CurrentUser user, Long eventId);
}
