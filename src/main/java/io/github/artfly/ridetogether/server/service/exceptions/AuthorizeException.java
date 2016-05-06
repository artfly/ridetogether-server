package io.github.artfly.ridetogether.server.service.exceptions;


public class AuthorizeException extends RuntimeException {
    public AuthorizeException(String username) {
        super(String.format("Authorization failed for user %s", username));
    }
}
