package com.rad.ms.corona_view.access.Repositories;

import com.rad.ms.corona_view.access.Entities.Permission;
import com.rad.ms.corona_view.access.Entities.Role;
import com.rad.ms.corona_view.access.Entities.User;
import com.rad.ms.corona_view.access.Registration.token.ConfirmationTokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.List;

@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Bean("initPermissionsData")
    InitializingBean initPermissionsData() {
        return () -> {
            log.info("Initializing predefined permissions.");
            String[] permission_names = {"all", "data_read", "data_write", "user_read", "user_write", "role_read", "role_write"};
            for (String name : permission_names) {
                if (!permissionRepository.existsById(name))
                    permissionRepository.save(new Permission(name));
            }
            for (String name : permission_names) {
                if (!permissionRepository.existsById(name))
                    permissionRepository.save(new Permission(name));
            }
            log.info("Finished to initialize predefined permissions.");
        };

    }

    @Bean("initRoleData")
    @DependsOn("initPermissionsData")
    InitializingBean initRoleData() {
        return () -> {
            log.info("Initializing predefined roles.");
            Permission all = permissionRepository.findById("all").orElseThrow();
            Permission data_read = permissionRepository.findById("data_read").orElseThrow();
            Permission user_read = permissionRepository.findById("user_read").orElseThrow();
            Permission role_read = permissionRepository.findById("role_read").orElseThrow();
            Permission data_write = permissionRepository.findById("data_write").orElseThrow();
            Role admin = new Role("1", "Admin", List.of(all));
            Role operator = new Role("2", "Operator", List.of(data_read, user_read, role_read));
            Role monitor = new Role("3", "Monitor ", List.of(data_read));
            Role blaa = new Role("0", "Blaa", List.of(user_read,data_write));

            for (Role role : List.of(admin, operator, monitor, blaa)) {
                if (!roleRepository.existsById(role.getId()))
                    roleRepository.save(role);
            }
            log.info("Finished to initialize predefined roles.");
        };
    }

    @Bean("initUsersData")
    @DependsOn("initRoleData")
    InitializingBean initUserData() {
        return () -> {
            log.info("Initializing predefined users.");
            userRepository.save(new User("Raz","1234",roleRepository.findRoleById("1"),true));
            userRepository.save(new User("Shahar","1234",roleRepository.findRoleById("1"),true));
            userRepository.save(new User("Dan","1234",roleRepository.findRoleById("2"),true));
            userRepository.save(new User("Moshe","1234",roleRepository.findRoleById("3"),true));
            userRepository.save(new User("Test","1234",roleRepository.findRoleById("0"),true));
            log.info("Finished to initialize predefined users.");
        };
    }
}



