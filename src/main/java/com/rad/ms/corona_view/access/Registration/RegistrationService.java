package com.rad.ms.corona_view.access.Registration;


import com.rad.ms.corona_view.access.Entities.User;
import com.rad.ms.corona_view.access.Repositories.RoleRepository;
import com.rad.ms.corona_view.access.Security.AccessUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService implements IRegistrationService{
    @Autowired
    private RoleRepository roleRepository ;

    private AccessUserDetailsService AccessUserDetailsService;
//    private final ConfirmationTokenService confirmationTokenService;
//    private final EmailSender emailSender;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public String register(RegistrationRequest request) {
        String token = AccessUserDetailsService.signUpUser(
                new User(
                        request.getUsername(),
                        request.getPassword(),
                        roleRepository.findRoleById("3")
                )
        );

        String link = "http://localhost:8403/registration/confirm?token=" + token;
        return token;
    }

//    @Transactional
//    public String confirmToken(String token) {
//        ConfirmationToken confirmationToken = confirmationTokenService
//                .getToken(token)
//                .orElseThrow(() ->
//                        new IllegalStateException("token not found"));
//
//        if (confirmationToken.getConfirmedAt() != null) {
//            throw new IllegalStateException("email already confirmed");
//        }
//
//        LocalDateTime expiredAt = confirmationToken.getExpiresAt();
//
//        if (expiredAt.isBefore(LocalDateTime.now())) {
//            throw new IllegalStateException("token expired");
//        }
//
//        confirmationTokenService.setConfirmedAt(token);
//        appUserService.enableAppUser(
//                confirmationToken.getAppUser().getEmail());
//        return "confirmed";
//    }


}