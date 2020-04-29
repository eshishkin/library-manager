package org.eshishkin.edu.librarymanager.portal;

import org.eshishkin.edu.librarymanager.portal.external.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ExternalRepositoryConfiguration {

    @Value("${couchdb.url}")
    private String url;

    @Value("${couchdb.user}")
    private String username;

    @Value("${couchdb.password}")
    private String password;

    @Bean
    public UserRepository userRepository() {
        WebClient client = WebClient.builder()
                .defaultHeaders(header -> header.setBasicAuth(username, password))
                .baseUrl(url)
                .build();

        return new UserRepository(client);
    }
}
