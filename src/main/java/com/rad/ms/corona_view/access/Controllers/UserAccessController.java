package com.rad.ms.corona_view.access.Controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rad.ms.corona_view.access.Entities.User;
import com.rad.ms.corona_view.access.Service.IUserAccessService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

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

}
