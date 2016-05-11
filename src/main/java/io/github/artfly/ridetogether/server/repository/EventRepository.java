package io.github.artfly.ridetogether.server.repository;

import io.github.artfly.ridetogether.server.repository.entities.Event;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EventRepository extends BaseRepository<Event, Long> {
    List<Event> findByDateLessThanAndRoute_PlaceIdEqualsAndRoute_RouteTypeEquals(Long date, String placeId, String routeType, Pageable pageable);

    List<Event> findByDateLessThanAndRoute_PlaceIdEquals(Long date, String placeId, Pageable pageable);

    List<Event> findByRoute_RouteTypeEqualsAndRoute_PlaceIdEquals(String routeType, String placeId, Pageable pageable);

    List<Event> findByRoute_PlaceIdEquals(String placeId, Pageable pageable);
}
