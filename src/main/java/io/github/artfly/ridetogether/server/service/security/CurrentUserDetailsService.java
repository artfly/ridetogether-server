package io.github.artfly.ridetogether.server.service.security;

import io.github.artfly.ridetogether.server.repository.entities.User;
import io.github.artfly.ridetogether.server.service.exceptions.NotFoundException;
import io.github.artfly.ridetogether.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public CurrentUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(String.format("No such user : %s", username)));
        return new CurrentUser(user);
    }
}
