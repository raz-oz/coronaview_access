package com.rad.ms.corona_view.access.Service;

import com.rad.ms.corona_view.access.Repositories.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionAccessService {

    @Autowired
    private PermissionRepository permissionRepository;

}
