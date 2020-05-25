package org.eshishkin.edu.librarymanager.portal.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MappingConfig {

    @Bean
    public ModelMapper mapper() {
        return new ModelMapper();
    }
}
