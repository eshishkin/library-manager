package org.eshishkin.edu.librarymanager.portal.service;

import java.util.Collections;
import java.util.List;
import org.eshishkin.edu.librarymanager.portal.exception.ResourceNotFoundException;
import org.eshishkin.edu.librarymanager.portal.exception.UserAlreadyExistException;
import org.eshishkin.edu.librarymanager.portal.external.user.UserRepository;
import org.eshishkin.edu.librarymanager.portal.external.user.model.SearchRequest;
import org.eshishkin.edu.librarymanager.portal.external.user.model.UserSelector;
import org.eshishkin.edu.librarymanager.portal.model.User;
import org.eshishkin.edu.librarymanager.portal.model.user.UserRegistrationRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    public List<User> search() {
        return search(new UserSelector());
    }

    private List<User> search(UserSelector request) {
        return userRepository
                .getUsers(SearchRequest.of(request))
                .defaultIfEmpty(Collections.emptyList())
                .block();
    }

    public User getUser(String userId) {
        return userRepository.getUser(userId).block();
    }

    public User findUser(String userId) {
        return userRepository.getUser(userId).onErrorResume(ResourceNotFoundException.class, e -> Mono.empty()).block();
    }

    public User create(UserRegistrationRequest request) {
        String email = request.getEmail();
        List<User> results = search(new UserSelector().email(email));
        if (results.isEmpty()) {
            User user = mapper.map(request, User.class);
            return userRepository.create(user).block();
        } else {
            throw new UserAlreadyExistException("User already exist: " + email);
        }
    }
}
