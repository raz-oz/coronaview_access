package com.rad.ms.corona_view.access.Security;

public class UserPrincipal {
    private String username;
    private String roleId;

    public UserPrincipal(String username, String roleId) {
        this.username = username;
        this.roleId = roleId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
