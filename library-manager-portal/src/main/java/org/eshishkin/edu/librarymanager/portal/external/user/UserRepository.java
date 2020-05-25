package org.eshishkin.edu.librarymanager.portal.external.user;

import java.util.List;
import lombok.AllArgsConstructor;
import org.eshishkin.edu.librarymanager.portal.exception.ResourceNotFoundException;
import org.eshishkin.edu.librarymanager.portal.external.user.model.SearchRequest;
import org.eshishkin.edu.librarymanager.portal.model.User;
import org.eshishkin.edu.librarymanager.portal.model.user.UserSearchResponse;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@AllArgsConstructor
public class UserRepository {
    private final WebClient webClient;

    public Mono<List<User>> getUsers(SearchRequest request) {
        return webClient
                .post()
                .uri("/users/_find")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .retrieve()
                .bodyToMono(UserSearchResponse.class)
                .map(UserSearchResponse::getDocs);
    }

    public Mono<User> getUser(String id) {
        return webClient
                .get()
                .uri("/users/{userId}", id)
                .retrieve()
                .onStatus(
                        NOT_FOUND::equals,
                        r -> Mono.error(new ResourceNotFoundException("Resource not found: " + id))
                )
                .bodyToMono(User.class);
    }

    public Mono<User> create(User user) {
        return webClient
                .post()
                .uri("/users")
                .body(BodyInserters.fromValue(user))
                .retrieve()
                .bodyToMono(User.class);
    }

}
