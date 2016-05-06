package io.github.artfly.ridetogether.server.service.security;


public class CurrentUserServiceImpl implements CurrentUserService {
    @Override
    public boolean canAccessUser(CurrentUser user, Long userId) {
        return user != null && user.getId().equals(userId);
    }
}
