package com.rad.ms.corona_view.access.Service;

import com.rad.ms.corona_view.access.Entities.Permission;
import java.util.List;

public interface IPermissionAccessService {
    List<Permission> getPermissions();
    Permission getPermission(String permissionId);
}
