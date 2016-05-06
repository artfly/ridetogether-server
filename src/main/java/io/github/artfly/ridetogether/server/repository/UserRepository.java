package io.github.artfly.ridetogether.server.repository;

import io.github.artfly.ridetogether.server.repository.entities.User;

import java.util.Optional;

public interface UserRepository extends BaseRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
