package org.eshishkin.edu.librarymanager.portal;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public Jackson2ObjectMapperBuilder jacksonMapperBuilder() {
        return new Jackson2ObjectMapperBuilder()
                .indentOutput(true)
                .failOnUnknownProperties(false)
                .serializationInclusion(JsonInclude.Include.NON_NULL);
    }

}
