package com.rad.ms.corona_view.access;

import com.rad.ms.corona_view.access.Service.IAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccessController {

    @Autowired
    private IAccessService accessService;

    @GetMapping("/users}")
    public List<User> getAllUsers() {
        return accessService.getUsers();
    }

    @GetMapping("/users/{user_id}")
    public User getUser(@PathVariable(value = "user_id") String userId) {
        return accessService.getUser(userId);
    }

    @PostMapping("/users")
    public User addUser(@RequestBody User user){
        return accessService.addUser(user);
    }

    // TO DO: update specific field logic
    @PatchMapping("/users/{user_id}")
    public User updateUser(@PathVariable(value = "user_id") String userId, @RequestBody User user){
        return accessService.updateUser(userId, user);
    }

    @DeleteMapping("/users/{user_id}")
    public boolean deleteUser(@PathVariable(value = "user_id") String userId){
        return accessService.deleteUser(userId);
    }

}
