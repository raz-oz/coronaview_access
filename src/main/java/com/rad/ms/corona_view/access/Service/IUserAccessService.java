package com.rad.ms.corona_view.access.Service;

import com.rad.ms.corona_view.access.Entities.User;

import java.util.List;

public interface IUserAccessService {
    List<User> getUsers();
    String addUser(String username, String password, String roleId);
    User getUser(String userId);
    User updateUser(String userId, User user);
    void deleteUser(String userId);
}
