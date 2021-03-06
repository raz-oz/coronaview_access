package com.rad.ms.corona_view.access.Repositories;

import com.rad.ms.corona_view.access.Entities.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role, String> {
 Role findRoleById(String id);
 Role findRoleByName(String name);
}
