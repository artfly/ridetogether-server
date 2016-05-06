package io.github.artfly.ridetogether.server.repository;

import io.github.artfly.ridetogether.server.repository.entities.Event;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EventRepository extends BaseRepository<Event, Long> {
    List<Event> findByDateLessThanAndPlaceIdEqualsAndRoute_RouteTypeEquals(Long date, String placeId, String routeType, Pageable pageable);
    List<Event> findByDateLessThanAndPlaceIdEquals(Long date, String placeId, Pageable pageable);
    List<Event> findByRoute_RouteTypeEqualsAndPlaceIdEquals(String routeType, String placeId, Pageable pageable);
    List<Event> findByPlaceIdEquals(String placeId, Pageable pageable);
}
