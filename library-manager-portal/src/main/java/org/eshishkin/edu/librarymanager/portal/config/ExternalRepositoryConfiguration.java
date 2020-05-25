package org.eshishkin.edu.librarymanager.portal.config;

import org.eshishkin.edu.librarymanager.portal.external.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Bean
    public UserRepository userRepository() {
        WebClient client = webClientBuilder
                .clone()
                .defaultHeaders(header -> header.setBasicAuth(username, password))
                .baseUrl(url)
                .build();

        return new UserRepository(client);
    }

}
