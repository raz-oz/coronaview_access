package com.rad.ms.corona_view.access.Repositories;

import com.rad.ms.corona_view.access.Entities.Permission;
import com.rad.ms.corona_view.access.Entities.Role;
import com.rad.ms.corona_view.access.Entities.User;
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
            //
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
          User raz = new User(); raz.setUsername("raz"); raz.setRoleId("1"); raz.setPassword("123");
          User shahar = new User(); shahar.setUsername("shahar"); shahar.setRoleId("1");
          User dan = new User(); dan.setUsername("dan"); dan.setRoleId("2"); dan.setPassword("123");
          User moshe = new User(); moshe.setUsername("moshe"); moshe.setRoleId("3");
          User test = new User(); test.setUsername("Test"); test.setRoleId("0");
          for (User user: List.of(raz,shahar,dan,moshe,test)){
              if (!userRepository.existsById(user.getUsername())){
                  userRepository.save(user);
              }
          }
            log.info("Finished to initialize predefined users.");
        };
    }
}



