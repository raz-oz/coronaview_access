package com.rad.ms.corona_view.access.Controllers;

import com.rad.ms.corona_view.access.Registration.IRegistrationService;
import com.rad.ms.corona_view.access.Registration.RegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AccessController {

    @Autowired
    private IRegistrationService registrationService;

    @GetMapping(value="/home")
    public String goHome(Principal user){
        String name = "guest";
        if (user!=null) {
            if (user.toString().contains("OAuth2AuthenticationToken")) {
                OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) user;
                OAuth2User oAuth2User = oAuth2AuthenticationToken.getPrincipal();
                name = (String) oAuth2User.getAttributes().get("name");
            }
            else {
                name = user.getName();
            }
        }
        return "Hello "+name;
    }


    @GetMapping(value="/login/oauth2")
    public Map<String, String> oauth2Login(){
        Map<String,String> response = new HashMap<>();
        response.put("login with github","http://localhost:8403/oauth2/authorization/github");
        response.put("login with google", "http://localhost:8403/oauth2/authorization/google");
        return response;
    }

    @PostMapping(value = "/registration")
    public String register(@RequestBody RegistrationRequest request) {
        return registrationService.register(request);
    }

    @GetMapping(path = "/registration/confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }

    @GetMapping(path = "/login/oauth2/test")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String oauth2AuthorityTest(OAuth2AuthenticationToken token){
        Map<String, Object> attributes =  token.getPrincipal().getAttributes();
        return attributes.get("name") + " logged in successfully";
    }

}
