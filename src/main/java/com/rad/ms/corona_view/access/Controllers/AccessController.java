package com.rad.ms.corona_view.access.Controllers;

import com.rad.ms.corona_view.access.Registration.IRegistrationService;
import com.rad.ms.corona_view.access.Registration.RegistrationRequest;
import com.rad.ms.corona_view.access.Registration.RegistrationService;
import com.rad.ms.corona_view.access.Security.SecurityConfig;
import com.rad.ms.corona_view.access.Security.UserDetailsConfig;
//import com.rad.ms.corona_view.access.Security.UserService;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
//@AllArgsConstructor
public class AccessController {
    @Autowired
    private IRegistrationService registrationService;

    @RequestMapping(value="/")
    public String goHome(Principal user){
        if (user!=null)
            return ("Hello " + user.getName());
        else
            return "Hello guest";
    }

//     default login
    @RequestMapping(value = "/login")
    public String Login(){
        return "/login";
    }

    @RequestMapping(value = "/login/token={token}")
    public String tokenLogin(@PathVariable String token){
        return "/login";
    }

    @RequestMapping(value = "/login/username={username}")
    public String usernameLogin(@PathVariable String username){
        return "/login";
    }

    @RequestMapping(value = "/login/OAthe2")
    public String OAthe2Login(){
        return "/login";
    }

    @RequestMapping(value = "/logout")
    public String Logout(){
        return "/logout";
    }

    @PostMapping(value = "/registration")
    public String register(@RequestBody RegistrationRequest request) {
        return registrationService.register(request);
    }


    @GetMapping(path = "/registration/confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }

}
