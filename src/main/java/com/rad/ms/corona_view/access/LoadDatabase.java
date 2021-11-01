package com.rad.ms.corona_view.access;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(UserRepository repository) {
        return args -> {
            User demo = new User();
            demo.setUsername("danroiz");
            demo.setPassword("12Dan@Dan");
            repository.save(demo);
        };
    }
}
