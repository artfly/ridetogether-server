package io.github.artfly.ridetogether.server.service;

import io.github.artfly.ridetogether.server.repository.ImageRepository;
import io.github.artfly.ridetogether.server.repository.UserRepository;
import io.github.artfly.ridetogether.server.repository.entities.Image;
import io.github.artfly.ridetogether.server.repository.entities.User;
import io.github.artfly.ridetogether.server.service.exceptions.AuthorizeException;
import io.github.artfly.ridetogether.server.service.exceptions.NotFoundException;
import io.github.artfly.ridetogether.server.service.security.CurrentUser;
import io.github.artfly.ridetogether.server.utils.Utils;
import io.github.artfly.ridetogether.server.web.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ImageRepository imageRepository, ModelMapper modelMapper) {
        this.imageRepository = imageRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public UserDto addUser(UserDto userDto) {
        Utils.validate(userDto.getImagePath(), imageRepository);
        User user = modelMapper.map(userDto, User.class);
        userRepository.save(user);
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public void updateUser(CurrentUser currentUser, UserDto userDto) {
        User user = Utils.validate(userDto.getId(), userRepository);
        if (!currentUser.getUsername().equals(userDto.getUsername())) {
            throw new AuthorizeException(currentUser.getUsername());
        }
        Image avatar = Utils.validate(userDto.getImagePath(), imageRepository);
        user.setImage(avatar);
        user.setPassword(userDto.getPassword());
        user.setBikeModel(userDto.getBikeModel());
        user.setPlaceId(userDto.getPlaceId());
        user.setRouteType(userDto.getRouteType());
        userRepository.save(user);
    }

    @Override
    public UserDto getUser(Long userId) {
        return modelMapper.map(Utils.validate(userId, userRepository), UserDto.class);
    }

    @Override
    public UserDto loginUser(CurrentUser currentUser) {
        User user = userRepository.findByUsername(currentUser.getUsername())
                .orElseThrow(() -> new NotFoundException(currentUser.getUsername()));
        return modelMapper.map(user, UserDto.class);
    }
}
