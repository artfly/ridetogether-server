package io.github.artfly.ridetogether.server.web;

import io.github.artfly.ridetogether.server.repository.entities.Route;
import io.github.artfly.ridetogether.server.repository.RouteRepository;
import io.github.artfly.ridetogether.server.repository.UserRepository;
import io.github.artfly.ridetogether.server.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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
    ResponseEntity<Route> postRoute(@RequestBody Route route) {
        Utils.validate(route.getCreator().getId(), userRepository);
        return new ResponseEntity<>(routeRepository.save(route), HttpStatus.CREATED);
    }

    @RequestMapping(value = "{routeId}", method = RequestMethod.GET)
    ResponseEntity<Route> getRoute(@PathVariable Long routeId) {
        Utils.validate(routeId, routeRepository);
        return new ResponseEntity<>(routeRepository.findOne(routeId), HttpStatus.OK);
    }

    @RequestMapping(value = "{routeId}", method = RequestMethod.DELETE)
    ResponseEntity<HttpStatus> deleteRoute(@PathVariable Long routeId) {
        Utils.validate(routeId, routeRepository);
        routeRepository.delete(routeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    // TODO
//    @RequestMapping(method = RequestMethod.GET)
//    ResponseEntity<List<Route>> getRoutes(@RequestParam Integer count, )

    //TODO : comments

}
