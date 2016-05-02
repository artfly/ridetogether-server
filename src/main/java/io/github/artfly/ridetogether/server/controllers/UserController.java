package io.github.artfly.ridetogether.server.controllers;

import io.github.artfly.ridetogether.server.entities.Route;
import io.github.artfly.ridetogether.server.entities.User;
import io.github.artfly.ridetogether.server.repositories.ImageRepository;
import io.github.artfly.ridetogether.server.repositories.UserRepository;
import io.github.artfly.ridetogether.server.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;

    @Autowired
    UserController(UserRepository userRepository, ImageRepository imageRepository) {
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<User> postUser(@RequestBody User user) {
        System.out.println(user);
        Utils.validate(user.getImage(), imageRepository);
        return new ResponseEntity<>(userRepository.save(user), HttpStatus.CREATED);
    }

    @RequestMapping(value = "{userId}", method = RequestMethod.GET)
    ResponseEntity<User> getUser(@PathVariable Long userId) {
        Utils.validate(userId, userRepository);
        return new ResponseEntity<>(userRepository.findOne(userId), HttpStatus.OK);
    }

    @RequestMapping(value = "{userId}", method = RequestMethod.PUT)
    ResponseEntity<HttpStatus> updateUser(@PathVariable Long userId, @RequestBody User user) {
        Utils.validate(userId, userRepository);
        Utils.validate(user.getImage(), imageRepository);
        User dbUser = userRepository.findOne(userId);
        dbUser.setBikeModel(user.getBikeModel());
        dbUser.setImage(imageRepository.getOne(user.getImage()));
        dbUser.setPassword(user.getPassword());
        dbUser.setPlaceId(user.getPlaceId());
        dbUser.setRouteType(user.getRouteType());
        userRepository.save(dbUser);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
