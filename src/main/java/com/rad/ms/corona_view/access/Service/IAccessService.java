package com.rad.ms.corona_view.access.Service;

import com.rad.ms.corona_view.access.User;

import java.util.List;

public interface IAccessService {
    List<User> getUsers();
    User addUser(User user);
    User getUser(String userId);
    User updateUser(String userId, User user);
    void deleteUser(String userId);
}
