package com.rad.ms.corona_view.access.Service;

import com.rad.ms.corona_view.access.Entities.Permission;
import com.rad.ms.corona_view.access.Entities.Role;
import com.rad.ms.corona_view.access.Entities.User;
import com.rad.ms.corona_view.access.ErrorHandling.InvalidInputException;
import com.rad.ms.corona_view.access.ErrorHandling.UserNotFoundException;
import com.rad.ms.corona_view.access.Repositories.RoleRepository;
import com.rad.ms.corona_view.access.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoleAccessService implements IRoleAccessService {

    @Autowired
    private RoleRepository roleRepository;


    @Override
    public List<Role> getAllRoles() {
        return null;
    }

    @Override
    public Role addRole(Role role) {
        return null;
    }

    @Override
    public Role getRole(String roleId) {
        return null;
    }

    @Override
    public Role updateRole(String roleId, Role role) {
        return null;
    }

    @Override
    public void deleteRole(String roleId) {

    }

    @Override
    public Permission getPermission(String permissionId) {
        return null;
    }
}
