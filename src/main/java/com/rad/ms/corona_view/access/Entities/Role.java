package com.rad.ms.corona_view.access.Entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Document
public class Role {
    @Id
    private final String id;
    private final String name;
    private final List<Permission> permissions;

    public Role(String _id, String _name, List<Permission> permissions) {
        this.id = _id;
        this.name = _name;
        this.permissions = permissions;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

}
