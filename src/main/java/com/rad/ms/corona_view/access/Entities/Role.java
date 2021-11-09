package com.rad.ms.corona_view.access.Entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Document
public class Role {
    @Id
    private final String _id;
    private final String _name;
    private final List<Permission> permissions;

    public Role(String _id, String _name, List<Permission> permissions) {
        this._id = _id;
        this._name = _name;
        this.permissions = permissions;
    }

    public String getId() {
        return _id;
    }

    public String getName() {
        return _name;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

}
