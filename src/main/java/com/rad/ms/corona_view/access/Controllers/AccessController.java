package com.rad.ms.corona_view.access.Controllers;

import com.rad.ms.corona_view.access.Entities.User;
import com.rad.ms.corona_view.access.Repositories.LoadDatabase;
import com.rad.ms.corona_view.access.Security.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
}
