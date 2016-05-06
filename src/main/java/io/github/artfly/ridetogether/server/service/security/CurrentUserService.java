package io.github.artfly.ridetogether.server.service.security;


public interface CurrentUserService {
    boolean canAccessUser(CurrentUser user, Long userId);
}
