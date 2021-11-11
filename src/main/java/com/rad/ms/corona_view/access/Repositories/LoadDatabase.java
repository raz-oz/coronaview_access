package com.rad.ms.corona_view.access.Repositories;

import com.rad.ms.corona_view.access.Entities.Permission;
import com.rad.ms.corona_view.access.Entities.Role;
import com.rad.ms.corona_view.access.Entities.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class LoadDatabase {



    @Bean
    CommandLineRunner loadPredefinedData(PermissionRepository repository) {
        String[] permission_names = {"all", "data_read", "data_write", "user_read", "user_write", "role_read", "role_write"};
        return args -> {
            for (String name : permission_names) {
                if (!repository.existsById(name))
                    repository.save(new Permission(name));
            }
        };
    }

    @Bean
    CommandLineRunner initRoleData(RoleRepository roleRepository, UserRepository userRepository) {
        Permission all = new Permission("all");
        Permission data_read = new Permission("data_read");
        Permission user_read = new Permission("user_read");
        Permission role_read = new Permission("role_read");

        Role admin = new Role("1", "Admin", List.of(all));
        Role Operator = new Role("2", "Operator", List.of(data_read, user_read, role_read));
        Role Monitor = new Role("3", "Monitor ", List.of(data_read));

        return args -> {
            for (Role role : List.of(admin, Operator, Monitor)) {
                if (!roleRepository.existsById(role.getId()))
                    roleRepository.save(role);
            }
        };
    }

//    @Bean
//    CommandLineRunner initDatabase(UserRepository userRepository, RoleRepository roleRepository) {
//        return args -> {
//            User admin = new User();
////            Role adminRole = new Role("1", "Admin", List.of(new Permission("all")));
////            admin.setRoleId(adminRole);
//            admin.setUsername("Shahar");
//            admin.setPassword("Admin_Test123");
//            Role adminRole = roleRepository.findById("1").orElseThrow();
//            admin.setRoleId(adminRole);
//            userRepository.save(admin);
//        };
//    }
}



