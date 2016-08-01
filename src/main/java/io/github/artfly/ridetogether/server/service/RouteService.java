package io.github.artfly.ridetogether.server.service;

import io.github.artfly.ridetogether.server.service.security.CurrentUser;
import io.github.artfly.ridetogether.server.web.dto.route.RouteDto;

import java.util.List;

public interface RouteService {
    RouteDto addRoute(CurrentUser currentUser, RouteDto routeDto);

    RouteDto getRoute(Long routeId);

    void deleteRoute(CurrentUser currentUser, Long routeId);

    List<RouteDto> getRoutes(String place, Integer count, Long since, String routeType);
}
