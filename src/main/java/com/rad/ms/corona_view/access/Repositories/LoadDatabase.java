package com.rad.ms.corona_view.access.Repositories;

import com.rad.ms.corona_view.access.Entities.Permission;
import com.rad.ms.corona_view.access.Entities.Role;
import com.rad.ms.corona_view.access.Entities.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.List;

@Configuration
public class LoadDatabase {


    @Bean("loadPredefinedData")
    CommandLineRunner loadPredefinedData(PermissionRepository repository) {
        String[] permission_names = {"all", "data_read", "data_write", "user_read", "user_write", "role_read", "role_write"};
        return args -> {
            for (String name : permission_names) {
                if (!repository.existsById(name))
                    repository.save(new Permission(name));
            }
        };
    }

    @DependsOn({"loadPredefinedData"})
    @Bean("initRoleData")
    CommandLineRunner initRoleData(RoleRepository roleRepository, UserRepository userRepository,PermissionRepository permissionRepository) {
        String[] permission_names = {"all", "data_read", "data_write", "user_read", "user_write", "role_read", "role_write"};
        for (String name : permission_names) {
            if (!permissionRepository.existsById(name))
                permissionRepository.save(new Permission(name));
        }
        Permission all=null;
        Permission data_read=null;
        Permission user_read=null;
        Permission role_read=null;
        if (permissionRepository.findById("all").isPresent()){
            all = permissionRepository.findById("all").get();
        }
        if (permissionRepository.findById("data_read").isPresent()){
            data_read = permissionRepository.findById("data_read").get();
        }
        if (permissionRepository.findById("user_read").isPresent()){
            user_read = permissionRepository.findById("user_read").get();
        }
        if (permissionRepository.findById("role_read").isPresent()){
            role_read = permissionRepository.findById("role_read").get();
        }
        assert data_read != null;
        assert user_read != null;
        assert role_read != null;
        assert all != null;

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



