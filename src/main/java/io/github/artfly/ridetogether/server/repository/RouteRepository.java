package io.github.artfly.ridetogether.server.repository;


import io.github.artfly.ridetogether.server.repository.entities.Route;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RouteRepository extends BaseRepository<Route, Long>  {
    List<Route> findByPlaceId(String placeId, Pageable pageable);
    List<Route> findByPlaceIdAndRouteType(String placeId, String routeType, Pageable pageable);
}
