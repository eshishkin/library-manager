package org.eshishkin.edu.librarymanager.portal.external;

import java.util.List;
import lombok.AllArgsConstructor;
import org.eshishkin.edu.librarymanager.portal.model.User;
import org.eshishkin.edu.librarymanager.portal.model.user.UserSearchResponse;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class UserRepository {
    private WebClient webClient;

    public Mono<List<User>> getUsers() {
        String request = "{\"selector\": {}}";
        return webClient
                .post()
                .uri("/users/_find")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(UserSearchResponse.class)
                .map(UserSearchResponse::getDocs);
    }

    public Mono<User> getUser(String id) {
        return webClient
                .get()
                .uri("/users/{userId}", id)
                .retrieve()
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
