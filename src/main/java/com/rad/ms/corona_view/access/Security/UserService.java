package com.rad.ms.corona_view.access.Security;

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

@Service
public class UserService implements ApplicationListener<AuthenticationSuccessEvent> {

    @Autowired
    UserRepository userRepository;
    private com.rad.ms.corona_view.access.Entities.User CurrUSER;

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
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

}
