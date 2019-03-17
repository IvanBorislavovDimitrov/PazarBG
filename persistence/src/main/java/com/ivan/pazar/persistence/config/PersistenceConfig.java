package com.ivan.pazar.persistence.config;

import com.ivan.pazar.persistence.dao.DefaultProfilePictureManager;
import com.ivan.pazar.persistence.dao.ProfilePictureManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersistenceConfig {

    @Bean
    public ProfilePictureManager fileSaver() {
        return new DefaultProfilePictureManager();
    }
}
