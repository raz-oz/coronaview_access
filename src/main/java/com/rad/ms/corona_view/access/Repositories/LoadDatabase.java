package com.rad.ms.corona_view.access.Repositories;

import com.rad.ms.corona_view.access.Entities.Permission;
import com.rad.ms.corona_view.access.Entities.Role;
import com.rad.ms.corona_view.access.Entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.List;

@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean("initPermissionsData")
    InitializingBean initPermissionsData(PermissionRepository repository) {
        return () -> {
            log.info("Initializing predefined permissions.");
            String[] permission_names = {"all", "data_read", "data_write", "user_read", "user_write", "role_read", "role_write"};
            for (String name : permission_names) {
                if (!repository.existsById(name))
                    repository.save(new Permission(name));
            }
            for (String name : permission_names) {
                if (!repository.existsById(name))
                    repository.save(new Permission(name));
            }
        };

    }

    @Bean("initRoleData")
    @DependsOn("initPermissionsData")
    InitializingBean initRoleData(RoleRepository roleRepository,PermissionRepository permissionRepository) {
        return () -> {
            log.info("Initializing predefined roles.");
            Permission all = permissionRepository.findById("all").orElseThrow();
            Permission data_read = permissionRepository.findById("data_read").orElseThrow();
            Permission user_read = permissionRepository.findById("user_read").orElseThrow();
            Permission role_read = permissionRepository.findById("role_read").orElseThrow();

            Role admin = new Role("1", "Admin", List.of(all));
            Role operator = new Role("2", "Operator", List.of(data_read, user_read, role_read));
            Role monitor = new Role("3", "Monitor ", List.of(data_read));

            for (Role role : List.of(admin, operator, monitor)) {
                if (!roleRepository.existsById(role.getId()))
                    roleRepository.save(role);
            }
        };
    }

    //TODO: Init predefined users
    @Bean
    @DependsOn("initRoleData")
    InitializingBean initUserData(UserRepository userRepository, RoleRepository roleRepository) {
        return () -> {
          User raz = new User(); User shahar = new User();
          User dan = new User(); User moshe = new User();
          User test = new User();
        };
    }
}



