package com.rad.ms.corona_view.access.Controllers;

import com.rad.ms.corona_view.access.Security.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccessController {

    @Autowired
    public UserService us;

    @RequestMapping(value="/")
    public String goHome(){
        if (us.getCurrUSER()!=null)
            return ("Hello " + us.getCurrUSER().getUsername());
        else
            return "Hello guest";
    }

    //    default login
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

}
