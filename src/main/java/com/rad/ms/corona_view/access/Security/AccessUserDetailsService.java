package com.rad.ms.corona_view.access.Security;

import com.rad.ms.corona_view.access.Entities.User;
import com.rad.ms.corona_view.access.Registration.token.ConfirmationToken;
import com.rad.ms.corona_view.access.Registration.token.ConfirmationTokenService;
import com.rad.ms.corona_view.access.Repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.UUID;

@Configuration
@AllArgsConstructor
public class AccessUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    private ConfirmationTokenService confirmationTokenService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User CurrUser = userRepository.getUserByUsername(s);
        if(CurrUser==null)
            throw new UsernameNotFoundException(s);
        return new UserDetailsConfig(CurrUser);
    }

    public String signUpUser(User appUser) {
        User userExists = userRepository
                .getUserByUsername(appUser.getUsername());

        if(userExists!=null) {
            throw new IllegalStateException("Username already taken");
        }

//        String encodedPassword = passwordEncoder
//                .encode(appUser.getPassword());

//        appUser.setPassword(encodedPassword);

        userRepository.save(appUser);

        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                appUser
        );

        confirmationTokenService.saveConfirmationToken(
                confirmationToken);

        return token;
    }
}
