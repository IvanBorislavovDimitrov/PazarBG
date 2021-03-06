package com.ivan.pazar.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.ivan.pazar.persistence"})
@EntityScan(basePackages = {"com.ivan.pazar.domain"})
@ComponentScan(basePackages = {"com.ivan.pazar.persistence", "com.ivan.pazar.web"})
@EnableScheduling
@EnableAsync
public class StartUp {

    public static void main(String[] args) {
        SpringApplication.run(StartUp.class, args);
    }
}
