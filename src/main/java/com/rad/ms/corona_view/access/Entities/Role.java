package com.rad.ms.corona_view.access.Entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class Role {
    @Id
    private  String id;
    private  String name;
    private  List<Permission> permissions;

    public Role(String id, String name, List<Permission> permissions) {
        this.id = id;
        this.name = name;
        this.permissions = permissions;
    }

    public Role() {
    }

    @Override
    public String toString() {
        return "Role{" +
                "id='" + id + '\'' +
                "name='" + name + '\'' +
                "permissions='" + permissions + '\'' +
                '}';
    }




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

}
