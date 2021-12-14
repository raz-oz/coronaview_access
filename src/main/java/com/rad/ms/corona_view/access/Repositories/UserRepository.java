package com.rad.ms.corona_view.access.Repositories;

import com.rad.ms.corona_view.access.Entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User getUserByUsername(String username);
}
