package org.eshishkin.edu.librarymanager.portal.service;

import java.util.Collections;
import java.util.List;
import org.eshishkin.edu.librarymanager.portal.exception.ResourceNotFoundException;
import org.eshishkin.edu.librarymanager.portal.external.UserRepository;
import org.eshishkin.edu.librarymanager.portal.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Mono<List<User>> search() {
        return userRepository.getUsers().defaultIfEmpty(Collections.emptyList());
    }

    public Mono<User> getUser(String userId) {
        return findUser(userId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("User not found: " + userId)));
    }

    public Mono<User> findUser(String userId) {
        return userRepository.getUser(userId);
    }

    public Mono<User> create(User user) {
        return userRepository.create(user);
    }
}
