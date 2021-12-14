package com.rad.ms.corona_view.access.Controllers;

import com.rad.ms.corona_view.access.Entities.User;
import com.rad.ms.corona_view.access.ErrorHandling.PermissionException;
import com.rad.ms.corona_view.access.ErrorHandling.UserNotFoundException;
//import com.rad.ms.corona_view.access.Security.UserService;
import com.rad.ms.corona_view.access.Registration.IRegistrationService;
import com.rad.ms.corona_view.access.Registration.RegistrationRequest;
import com.rad.ms.corona_view.access.Service.IUserAccessService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
/**"""all", "data_read", "data_write", "user_read", "user_write", "role_read", "role_write"""*/
@RestController
@RequestMapping("/users")
public class UserAccessController {

    @Autowired
    protected UserDetailsService userDetailsService;

    @Autowired
    private IUserAccessService accessService;

    @Autowired
    private IRegistrationService registrationService;


    @GetMapping
    @PreAuthorize("hasAuthority('all') || hasAuthority('user_read')")
    public List<User> getAllUsers(){
        return accessService.getUsers();
    }

    @GetMapping("{user_id}")
    public User getUser(@PathVariable(value = "user_id") String userId) {
        limitAccesses(userId);
        return accessService.getUser(userId);
    }

    /* OVERLAPS WITH REGISTER */
    @PostMapping
    @PreAuthorize("hasAuthority('all') || hasAuthority('user_write')")
    public String addUser(@RequestBody RegistrationRequest request){
           return registrationService.register(request);
    }

    @PatchMapping("{user_id}")
    @PreAuthorize("hasAuthority('all') || hasAuthority('user_write')")
    public User updateUser(@PathVariable(value = "user_id") String userId, @RequestBody User user){
        return accessService.updateUser(userId, user);
    }

    @DeleteMapping(value="{user_id}")
    @PreAuthorize("hasAuthority('all') || hasAuthority('user_write')")
    public void deleteUser(@PathVariable(value = "user_id") String userId){
        accessService.deleteUser(userId);
    }


    public void limitAccesses(String id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> Permissions =  auth.getAuthorities();
        if(!(Permissions.toString().contains("all") || Permissions.toString().contains("user_read")) && !auth.getPrincipal().toString().equals(id)){
            throw  new PermissionException(auth.getPrincipal().toString());
        }
    }
//
//    public void adminAccesses(){
//        if(!us.getCurrUserRole().equals("Admin")){
//            throw  new PermissionException(us.getCurrUSER().getUsername());
//        }
//    }
}
