package com.rad.ms.corona_view.access.Repositories;

import com.rad.ms.corona_view.access.Entities.Permission;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PermissionRepository extends MongoRepository<Permission, String> {
}
