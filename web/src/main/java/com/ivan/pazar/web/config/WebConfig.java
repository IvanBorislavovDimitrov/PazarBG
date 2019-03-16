package com.ivan.pazar.web.config;

import com.ivan.pazar.web.security.Sha256Encoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebConfig {

    @Bean(value = "sha256PasswordEncoder")
    public PasswordEncoder passwordEncoder() {
        return new Sha256Encoder();
    }
}
