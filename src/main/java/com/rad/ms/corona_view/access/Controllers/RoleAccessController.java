package com.rad.ms.corona_view.access.Controllers;

import com.rad.ms.corona_view.access.Entities.Role;
import com.rad.ms.corona_view.access.Service.IRoleAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleAccessController {

    @Autowired
    private IRoleAccessService RoleAccessService;

    @GetMapping
    @PreAuthorize("hasAuthority('all') || hasAuthority('role_read')")
    public List<Role> getAllRoles () {
        return RoleAccessService.getAllRoles();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('all') || hasAuthority('role_write')")
    public Role createNewRole(@RequestBody Role role){
        return RoleAccessService.addRole(role);
    }

    @DeleteMapping("{roles_id}")
    @PreAuthorize("hasAuthority('all') || hasAuthority('role_write')")
    public void deleteRole(@PathVariable(value = "roles_id") String roles_id){
        RoleAccessService.deleteRole(roles_id);
    }

    @GetMapping("{roles_id}")
    @PreAuthorize("hasAuthority('all') || hasAuthority('role_read')")
    public Role getRoleByID(@PathVariable(value = "roles_id") String roles_id) {
        return RoleAccessService.getRole(roles_id);
    }

    @PutMapping(value="{roles_id}")
    @PreAuthorize("hasAuthority('all') || hasAuthority('role_write')")
    public Role updateRole(@PathVariable(value = "roles_id") String roleId, @RequestBody Role role){
        return RoleAccessService.updateRole(roleId, role);
    }

}
