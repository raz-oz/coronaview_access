package com.rad.ms.corona_view.access.Controllers;

import com.rad.ms.corona_view.access.Entities.User;
import com.rad.ms.corona_view.access.Service.IUserAccessService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserAccessController {

    @Autowired
    private IUserAccessService accessService;


    @GetMapping("/users")
    public List<User> getAllUsers() {
        return accessService.getUsers();
    }

    @GetMapping("/users/{user_id}")
    public User getUser(@PathVariable(value = "user_id") String userId) {
        return accessService.getUser(userId);
    }

    //@RequestMapping(value="/users", method = RequestMethod.POST)
    @PostMapping("/users")
    public User addUser(@RequestBody User user){
        return accessService.addUser(user);
    }


    //@RequestMapping(value="/users/{user_id}", method = RequestMethod.PATCH)
    @PatchMapping("/users/{user_id}")
    public User updateUser(@PathVariable(value = "user_id") String userId, @RequestBody User user){
        return accessService.updateUser(userId, user);
    }

    //@RequestMapping(value="/users/{user_id}", method = RequestMethod.DELETE)
    @DeleteMapping("/users/{user_id}")
    public void deleteUser(@PathVariable(value = "user_id") String userId){
        accessService.deleteUser(userId);
    }

    @GetMapping
    public ResponseEntity<?> refreshToken(){

    }
}
