package com.rad.ms.corona_view.access.Service;

import com.rad.ms.corona_view.access.Entities.Permission;
import com.rad.ms.corona_view.access.ErrorHandling.PermissionNotFoundException;
import com.rad.ms.corona_view.access.ErrorHandling.UserNotFoundException;
import com.rad.ms.corona_view.access.Repositories.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionAccessService implements IPermissionAccessService {

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public List<Permission> getPermissions() {
        return permissionRepository.findAll();
    }

    @Override
    public Permission getPermission(String permissionId) {
        return permissionRepository.findById(permissionId).orElseThrow(()->new PermissionNotFoundException(permissionId));
    }
}
