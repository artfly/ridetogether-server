package io.github.artfly.ridetogether.server.exceptions;


public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long userId) {
        super("no user with id " + userId);
    }
}
