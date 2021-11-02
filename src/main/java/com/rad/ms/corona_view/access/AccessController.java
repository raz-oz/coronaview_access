package com.rad.ms.corona_view.access;

import com.rad.ms.corona_view.access.Service.IAccessService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccessController {

    @Autowired
    private IAccessService accessService;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return accessService.getUsers();
    }

    @GetMapping("/users/{user_id}")
    public User getUser(@PathVariable(value = "user_id") String userId) {
        return accessService.getUser(userId);
    }

    @RequestMapping(value="/users", method = RequestMethod.POST)
    public User addUser(@RequestBody User user){
        return accessService.addUser(user);
    }

    // TO DO: update specific field logic
    @RequestMapping(value="/users/{user_id}", method = RequestMethod.PATCH)
    public User updateUser(@PathVariable(value = "user_id") String userId, @RequestBody User user){
        return accessService.updateUser(userId, user);
    }

    @RequestMapping(value="/users/{user_id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable(value = "user_id") String userId){
        accessService.deleteUser(userId);
    }
}
