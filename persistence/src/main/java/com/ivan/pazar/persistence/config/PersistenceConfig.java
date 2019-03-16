package com.ivan.pazar.persistence.config;

import com.ivan.pazar.persistence.dao.DefaultFileSaver;
import com.ivan.pazar.persistence.dao.FileSaver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersistenceConfig {

    @Bean
    public FileSaver fileSaver() {
        return new DefaultFileSaver();
    }
}
