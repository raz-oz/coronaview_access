package com.rad.ms.corona_view.access.Repositories;

import com.rad.ms.corona_view.access.Entities.Permission;
import com.rad.ms.corona_view.access.Entities.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role, String> {
}
