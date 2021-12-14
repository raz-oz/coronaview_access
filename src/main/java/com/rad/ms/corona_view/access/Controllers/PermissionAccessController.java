package com.rad.ms.corona_view.access.Controllers;


import com.rad.ms.corona_view.access.Entities.Permission;
import com.rad.ms.corona_view.access.Entities.User;
import com.rad.ms.corona_view.access.Repositories.LoadDatabase;
//import com.rad.ms.corona_view.access.Security.UserService;
import com.rad.ms.corona_view.access.Service.IPermissionAccessService;
import com.rad.ms.corona_view.access.Service.IUserAccessService;
import com.rad.ms.corona_view.access.Service.PermissionAccessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PermissionAccessController {
    @Autowired
    protected UserDetailsService userDetailsService;

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Autowired
    private IPermissionAccessService accessService;

    @GetMapping("/permissions")
    @PreAuthorize("hasAuthority('all') || hasAuthority('role_read')")
    public List<Permission> getAllPermissions() {
            return accessService.getPermissions();
        }


    @GetMapping("/permissions/{id}")
    @PreAuthorize("hasAuthority('all') || hasAuthority('role_read')")
    public Permission getPermission(@PathVariable(value = "id") String id) {
        return accessService.getPermission(id);
    }
}
