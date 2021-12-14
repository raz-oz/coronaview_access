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

    @PostMapping(value = "/registration")
    public String register(@RequestBody RegistrationRequest request) {
        return registrationService.register(request);
    }

    @GetMapping(path = "/registration/confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }

}
