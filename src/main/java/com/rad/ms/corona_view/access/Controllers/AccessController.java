package com.rad.ms.corona_view.access.Controllers;

import com.rad.ms.corona_view.access.Registration.IRegistrationService;
import com.rad.ms.corona_view.access.Registration.RegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@RestController
public class AccessController {
    @Autowired
    private IRegistrationService registrationService;

    @GetMapping(value="/")

    public String goHome(Principal user){
        if (user!=null)
            return ("Hello " + user.getName());
        else
            return "Hello guest";
    }

//     default login
//    @GetMapping(value = "/login")
//    public String Login(){
//        return "login.html";
//    }

    @GetMapping(value = "/login/token={token}")
    public String tokenLogin(@PathVariable String token){
        return "/login";
    }

    @GetMapping(value = "/login/username={username}")
    public String usernameLogin(@PathVariable String username){
        return "/login";
    }

    @GetMapping(value = "/login/OAthe2")
    public String OAthe2Login(){
        return "/login";
    }

    @GetMapping(value = "/logout")
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
