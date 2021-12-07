package com.rad.ms.corona_view.access.Security;

import com.rad.ms.corona_view.access.Entities.Permission;
import com.rad.ms.corona_view.access.Entities.User;
import com.rad.ms.corona_view.access.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserService implements ApplicationListener<AuthenticationSuccessEvent> {

    /**
    * This class is been used as cookies, allows us 2 access from any one of our the classes
     * to the curr login user info.
    * */
    @Autowired
    UserRepository userRepository;
    private com.rad.ms.corona_view.access.Entities.User CurrUSER;

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        //event listener - on Login
        String userName = ((UserDetails) event.getAuthentication().
                getPrincipal()).getUsername();

        this.CurrUSER = userRepository.getUserByUsername(userName);
    }

    public User getCurrUSER() {
        return CurrUSER;
    }
    public String getCurrUserRole(){
        return CurrUSER.getRole().getName();
    }
    public List<Permission> getCurrUserPermissions(){
        return CurrUSER.getRole().getPermissions();
    }


}
