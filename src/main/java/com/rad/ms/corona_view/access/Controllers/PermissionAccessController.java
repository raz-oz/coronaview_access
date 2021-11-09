package com.rad.ms.corona_view.access.Controllers;


import com.rad.ms.corona_view.access.Entities.User;
import com.rad.ms.corona_view.access.Service.IUserAccessService;
import com.rad.ms.corona_view.access.Service.PermissionAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PermissionAccessController {

    @Autowired
    private PermissionAccessService accessService;

//    @GetMapping("/permissions")
//    public List<User> getAllUsers() {
//        return accessService.();
//    }
}
