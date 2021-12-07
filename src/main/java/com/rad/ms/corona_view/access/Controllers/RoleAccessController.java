package com.rad.ms.corona_view.access.Controllers;

import com.rad.ms.corona_view.access.Entities.Role;
import com.rad.ms.corona_view.access.Entities.User;
import com.rad.ms.corona_view.access.Service.IRoleAccessService;
import com.rad.ms.corona_view.access.Service.IUserAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleAccessController {

    @Autowired
    private IRoleAccessService RoleAccessService;

    @GetMapping
    public List<Role> getAllRoles () {
        return RoleAccessService.getAllRoles();
    }

    @PostMapping
    public Role createNewRole(@RequestBody Role role){
        return RoleAccessService.addRole(role);
    }

    @DeleteMapping("{roles_id}")
    public void deleteRole(@PathVariable(value = "roles_id") String roles_id){
        RoleAccessService.deleteRole(roles_id);
    }

    @GetMapping("{roles_id}")
    public Role getRoleByID(@PathVariable(value = "roles_id") String roles_id) {
        return RoleAccessService.getRole(roles_id);
    }

    @PutMapping(value="{roles_id}")
    public Role updateRole(@PathVariable(value = "roles_id") String roleId, @RequestBody Role role){
        return RoleAccessService.updateRole(roleId, role);
    }

}
