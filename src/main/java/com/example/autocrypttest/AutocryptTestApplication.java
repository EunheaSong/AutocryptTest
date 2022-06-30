package com.example.autocrypttest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class AutocryptTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutocryptTestApplication.class, args);
    }

}
