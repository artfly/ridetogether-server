package io.github.artfly.ridetogether.server.web;

import io.github.artfly.ridetogether.server.service.UserService;
import io.github.artfly.ridetogether.server.service.security.CurrentUser;
import io.github.artfly.ridetogether.server.web.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("isAnonymous()")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<UserDto> postUser(@RequestBody UserDto user) {
        return new ResponseEntity<>(userService.addUser(user), HttpStatus.CREATED);
    }

    @RequestMapping(value = "{userId}", method = RequestMethod.GET)
    public ResponseEntity<UserDto> getUser(@PathVariable Long userId) {
        return new ResponseEntity<>(userService.getUser(userId), HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "{userId}", method = RequestMethod.PUT)
    ResponseEntity<HttpStatus> updateUser(@AuthenticationPrincipal CurrentUser currentUser,
                                          @PathVariable Long userId, @RequestBody UserDto user) {
        userService.updateUser(currentUser, user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
