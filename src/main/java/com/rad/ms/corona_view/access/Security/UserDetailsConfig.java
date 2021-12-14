//package com.rad.ms.corona_view.access.Security;
//
//import com.rad.ms.corona_view.access.Entities.Permission;
//import com.rad.ms.corona_view.access.Entities.Role;
//import com.rad.ms.corona_view.access.Entities.User;
//import com.rad.ms.corona_view.access.Repositories.RoleRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.*;
//
//public class UserDetailsConfig implements UserDetails {
//
//    /**
//     * This class is responsible for managing the user who is currently trying to connect to the system
//     * **/
//    private final User user;
//
//    //Saves the instance of the user who has just requested 2 login
//    public UserDetailsConfig(User user) {
//        super();
//        this.user = user;
//    }
//
//    //Creates a new authority for the user in the current login to the system - according to his role
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        for (Permission permission : this.user.getRole().getPermissions()) {
//            authorities.add(new SimpleGrantedAuthority(permission.getName()));
//        }
//        return authorities;
//    }
//
//
//    /**-----------------------------------getter------------------------------------**/
//
//    @Override
//    public String getPassword() {
//        return user.getPassword();
//    }
//
//    @Override
//    public String getUsername() {
//        return user.getUsername();
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return user.getAccountNonExpired();
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return user.getAccountNonLocked();
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return user.getCredentialsNonExpired();
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return user.getEnabled();
//    }
//
//}
