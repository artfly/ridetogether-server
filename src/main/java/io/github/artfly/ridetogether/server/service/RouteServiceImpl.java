package io.github.artfly.ridetogether.server.service;

import io.github.artfly.ridetogether.server.repository.CoordinateRepository;
import io.github.artfly.ridetogether.server.repository.RouteRepository;
import io.github.artfly.ridetogether.server.repository.entities.Route;
import io.github.artfly.ridetogether.server.service.exceptions.AuthorizeException;
import io.github.artfly.ridetogether.server.service.security.CurrentUser;
import io.github.artfly.ridetogether.server.utils.Utils;
import io.github.artfly.ridetogether.server.web.dto.route.GeometryDto;
import io.github.artfly.ridetogether.server.web.dto.route.PropertiesDto;
import io.github.artfly.ridetogether.server.web.dto.route.RouteDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RouteServiceImpl implements RouteService {
    private static final int MAX_COUNT = 200;
    private final RouteRepository routeRepository;
    private final CoordinateRepository coordinateRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public RouteServiceImpl(RouteRepository routeRepository, CoordinateRepository coordinateRepository, ModelMapper modelMapper) {
        this.routeRepository = routeRepository;
        this.coordinateRepository = coordinateRepository;
        this.modelMapper = modelMapper;
        modelMapper.addMappings(new PropertyMap<RouteDto, Route>() {
            @Override
            protected void configure() {
                map().setDescription(source.getProperties().getDescription());
                map().setTitle(source.getProperties().getTitle());
                map().setRating(source.getProperties().getRating());
                map().setRouteType(source.getProperties().getRouteType());
                map().setCoordinates(source.getGeometry().getCoordinates());
                map().setPlaceId(source.getProperties().getPlaceId());
            }
        });
        modelMapper.addMappings(new PropertyMap<Route, RouteDto>() {
            @Override
            protected void configure() {
                map().setType("Feature");
                map().setGeometry(new GeometryDto());
                map().getGeometry().setType("LineString");
                map().getGeometry().setCoordinates(source.getCoordinates());
                map().setProperties(new PropertiesDto());
                map().getProperties().setCreatorId(source.getCreator().getId());
                map().getProperties().setAddedAt(source.getAddedAt());
                map().getProperties().setRating(source.getRating());
                map().getProperties().setRouteType(source.getRouteType());
                map().getProperties().setTitle(source.getTitle());
                map().getProperties().setDescription(source.getDescription());
                map().getProperties().setId(source.getId());
                map().getProperties().setPlaceId(source.getPlaceId());
            }
        });
    }

    @Override
    public RouteDto addRoute(CurrentUser currentUser, RouteDto routeDto) {
        Route route = modelMapper.map(routeDto, Route.class);
        route.setCreator(currentUser.getUser());
        coordinateRepository.save(route.getDbCoordinates());
        routeRepository.save(route);
        return modelMapper.map(route, RouteDto.class);
    }

    @Override
    public RouteDto getRoute(Long routeId) {
        return modelMapper.map(Utils.validate(routeId, routeRepository), RouteDto.class);
    }

    @Override
    public void deleteRoute(CurrentUser currentUser, Long routeId) {
        Route route = Utils.validate(routeId, routeRepository);
        if (!currentUser.getUsername().equals(route.getCreator().getUsername())) {
            throw new AuthorizeException(currentUser.getUsername());
        }
        routeRepository.delete(route);
    }

    @Override
    public List<RouteDto> getRoutes(String place, Integer count, String routeType) {
        Pageable pageable;
        if (count > MAX_COUNT) {
            count = MAX_COUNT;
        }
        pageable = new PageRequest(0, count);

        List<Route> routes;
        if (routeType == null) {
            routes = routeRepository.findByPlaceId(place, pageable);
        } else {
            routes = routeRepository.findByPlaceIdAndRouteType(place, routeType, pageable);
        }
        return routes.stream()
                .map(route -> modelMapper.map(route, RouteDto.class))
                .collect(Collectors.toList());
    }
}
