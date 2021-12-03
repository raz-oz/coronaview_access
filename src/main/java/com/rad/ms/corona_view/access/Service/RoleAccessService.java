package com.rad.ms.corona_view.access.Service;

import com.rad.ms.corona_view.access.Entities.Role;
import com.rad.ms.corona_view.access.ErrorHandling.InvalidInputException;
import com.rad.ms.corona_view.access.ErrorHandling.RoleNotFoundException;
import com.rad.ms.corona_view.access.Repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RoleAccessService implements IRoleAccessService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role addRole(Role role) {
        if (role == null || role.getId() == null || role.getName() == null||role.getPermissions() == null) {
            throw new InvalidInputException("Can not update role - These IDs already exist in the system");
        }
        return roleRepository.save(role);
    }

    @Override
    public Role getRole(String roleId) {
        return roleRepository.findById(roleId).orElseThrow(()->new RoleNotFoundException(roleId));
    }

    @Override
    public Role updateRole(String roleId, Role role) {
        return roleRepository.findById(roleId)
                .map(role_to_update -> {
                    role_to_update.setName(role.getName());
                    role_to_update.setPermissions(role.getPermissions());
                    return roleRepository.save(role_to_update);
                }).orElseThrow(() -> new RoleNotFoundException(roleId));
    }

    @Override
    public void deleteRole(String roleId) {
        roleRepository.deleteById(roleId);
    }

    @Override
    public boolean existsById(String roleId) {
        return roleRepository.existsById(roleId);
    }
}
