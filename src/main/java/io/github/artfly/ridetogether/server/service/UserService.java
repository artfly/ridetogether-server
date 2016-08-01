package io.github.artfly.ridetogether.server.service;

import io.github.artfly.ridetogether.server.service.security.CurrentUser;
import io.github.artfly.ridetogether.server.web.dto.UserDto;

public interface UserService {
    UserDto addUser(UserDto userDto);

    void updateUser(CurrentUser currentUser, UserDto userDto);

    UserDto getUser(Long userId);

    UserDto loginUser(CurrentUser currentUser);
}
