package com.rad.ms.corona_view.access.Controllers;


import com.rad.ms.corona_view.access.Entities.Permission;
import com.rad.ms.corona_view.access.Entities.User;
import com.rad.ms.corona_view.access.Service.IPermissionAccessService;
import com.rad.ms.corona_view.access.Service.IUserAccessService;
import com.rad.ms.corona_view.access.Service.PermissionAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PermissionAccessController {

    @Autowired
    private IPermissionAccessService accessService;

    @GetMapping("/permissions")
    public List<Permission> getAllPermissions() {
        return accessService.getPermissions();
    }

    @GetMapping("/permissions/{id}")
    public Permission getPermission(@PathVariable(value = "id") String id) {
        return accessService.getPermission(id);
    }
}
