package com.ivan.pazar.web.config;

import com.ivan.pazar.persistence.security.DefaultBCryptEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordEncoderBean {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new DefaultBCryptEncoder();
    }
}
