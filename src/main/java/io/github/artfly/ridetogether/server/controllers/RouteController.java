package io.github.artfly.ridetogether.server.controllers;

import io.github.artfly.ridetogether.server.entities.Route;
import io.github.artfly.ridetogether.server.repositories.RouteRepository;
import io.github.artfly.ridetogether.server.repositories.UserRepository;
import io.github.artfly.ridetogether.server.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


// TODO : add comments
@RestController
@RequestMapping("/routes")
public class RouteController {
    private final RouteRepository routeRepository;
    private final UserRepository userRepository;

    @Autowired
    RouteController(RouteRepository routeRepository, UserRepository userRepository) {
        this.routeRepository = routeRepository;
        this.userRepository = userRepository;
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<Route> addRoute(@RequestBody Route route) {
        Utils.validate(route.getCreator().getId(), userRepository);
        return new ResponseEntity<>(routeRepository.save(route), HttpStatus.CREATED);
    }

}
