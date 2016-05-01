package io.github.artfly.ridetogether.server.repositories;

import io.github.artfly.ridetogether.server.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends BaseRepository<Event, Long> {
}
