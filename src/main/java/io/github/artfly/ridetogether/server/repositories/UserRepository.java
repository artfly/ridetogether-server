package io.github.artfly.ridetogether.server.repositories;

import io.github.artfly.ridetogether.server.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends BaseRepository<User, Long> {
}
