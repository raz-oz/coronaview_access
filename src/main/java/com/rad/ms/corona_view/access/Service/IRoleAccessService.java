package com.rad.ms.corona_view.access.Service;


import com.rad.ms.corona_view.access.Entities.Permission;
import com.rad.ms.corona_view.access.Entities.Role;
import com.rad.ms.corona_view.access.Entities.User;

import java.util.List;

public interface IRoleAccessService {
    List<Role>  getAllRoles();
    Role addRole(Role role);
    Role getRole(String roleId);
    Role updateRole(String roleId, Role role);
    void deleteRole(String roleId);
}
