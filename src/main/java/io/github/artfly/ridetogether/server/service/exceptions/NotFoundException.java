package io.github.artfly.ridetogether.server.service.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String id) {
        super("object with " + id + " not found");
    }
}
