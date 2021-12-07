package com.rad.ms.corona_view.access.Controllers;

import com.rad.ms.corona_view.access.Entities.User;
import com.rad.ms.corona_view.access.ErrorHandling.PermissionException;
import com.rad.ms.corona_view.access.ErrorHandling.UserNotFoundException;
import com.rad.ms.corona_view.access.Security.UserService;
import com.rad.ms.corona_view.access.Service.IUserAccessService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**"""all", "data_read", "data_write", "user_read", "user_write", "role_read", "role_write"""*/
@RestController
@RequestMapping("/users")
public class UserAccessController {

    @Autowired
    public UserService us;

    @Autowired
    private IUserAccessService accessService;


    @GetMapping
    @PreAuthorize("hasAuthoritys('all','user_write')")
    public List<User> getAllUsers(){
        return accessService.getUsers();
    }

    @GetMapping("{user_id}")
    public User getUser(@PathVariable(value = "user_id") String userId) {
        limitAccesses(userId);
        return accessService.getUser(userId);
    }

    @PostMapping
    @PreAuthorize("hasAuthoritys('all','user_write')")
    public User addUser(@RequestBody User user){
        return accessService.addUser(user);
    }

    @PatchMapping("{user_id}")
    @PreAuthorize("hasAuthoritys('all','user_write')")
    public User updateUser(@PathVariable(value = "user_id") String userId, @RequestBody User user){
        return accessService.updateUser(userId, user);
    }

    @DeleteMapping(value="{user_id}")
    @PreAuthorize("hasAuthoritys('all','user_write')")
    public void deleteUser(@PathVariable(value = "user_id") String userId){
        accessService.deleteUser(userId);
    }


    public void limitAccesses(String id){
        if(!us.getCurrUserRole().equals("Admin") && !us.getCurrUSER().getUsername().equals(id)){
            throw  new PermissionException(us.getCurrUSER().getUsername());
        }
    }

    public void adminAccesses(){
        if(!us.getCurrUserRole().equals("Admin")){
            throw  new PermissionException(us.getCurrUSER().getUsername());
        }
    }
}
