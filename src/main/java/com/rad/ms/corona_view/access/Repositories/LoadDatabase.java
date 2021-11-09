package com.rad.ms.corona_view.access.Repositories;

import com.rad.ms.corona_view.access.Entities.Permission;
import com.rad.ms.corona_view.access.Repositories.PermissionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class LoadDatabase {


    @Bean
    CommandLineRunner loadPredefinedData(PermissionRepository repository) {
        String[] permission_names = {"all", "data_read", "data_write", "user_read", "user_write", "role_read", "role_write"};
        return args -> {
            for (String name: permission_names) {
                if (!repository.existsById(name))
                    repository.save(new Permission(name));
            }
        };
    }

//    @Bean
//    CommandLineRunner initDatabase(UserRepository repository) {
//        return args -> {
//            User demo = new User();
//            demo.setUsername("danroiz");
//            demo.setPassword("12Dan@Dan");
//            repository.deleteAll();
//            repository.save(demo);
//        };
//    }
}
