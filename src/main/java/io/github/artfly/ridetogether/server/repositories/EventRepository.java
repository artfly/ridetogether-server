package io.github.artfly.ridetogether.server.repositories;

import io.github.artfly.ridetogether.server.entities.Event;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends BaseRepository<Event, Long> {
    List<Event> findByDateLessThanAndPlaceIdEquals(Long date, Long placeId, Pageable pageable);
}
