package com.rad.ms.corona_view.access.Security;

import com.rad.ms.corona_view.access.Entities.Permission;
import com.rad.ms.corona_view.access.Entities.Role;
import com.rad.ms.corona_view.access.Entities.User;
import com.rad.ms.corona_view.access.Repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class UserDetailsConfig implements UserDetails {

    /**
     * This class is responsible for managing the user who is currently trying to connect to the system
     * **/
    private final User user;
    private final Role role;

    //Saves the instance of the user who has just requested 2 login
    public UserDetailsConfig(User user) {
        super();
        this.user = user;
        this.role = user.getRole();
    }

    //Creates a new authority for the user in the current login to the system - according to his role
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return getGrantedAuthorities();
    }

//    public Collection<? extends GrantedAuthority> getRoles() {
//        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(this.role.getName());
//        return Collections.singleton(authority);
//    }

    /**-----------------------------------getter------------------------------------**/

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.getAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.getCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return user.getEnabled();
    }

    //Creates a new authorities for user permissions in the system - According to those we have defined
    private List<GrantedAuthority> getGrantedAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Permission permission : this.role.getPermissions()) {
            authorities.add(new SimpleGrantedAuthority(permission.getName()));
        }
        return authorities;
    }
}
