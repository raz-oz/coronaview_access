package com.rad.ms.corona_view.access.Registration;


import com.rad.ms.corona_view.access.Entities.User;
import com.rad.ms.corona_view.access.Registration.token.ConfirmationToken;
import com.rad.ms.corona_view.access.Registration.token.ConfirmationTokenService;
import com.rad.ms.corona_view.access.Service.UserAccessService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService implements IRegistrationService{
    private static final String ROLE_MONITOR = "3";

    @Autowired
    private UserAccessService userAccessService;
    @Autowired
    private ConfirmationTokenService confirmationTokenService;


    public String register(RegistrationRequest request) {
        String token = userAccessService.addUser(request.getUsername(), request.getPassword(), ROLE_MONITOR);
        return "http://localhost:8403/registration/confirm?token=" + token;
    }

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("User already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        User user = confirmationToken.getAppUser();
        user.setEnabled(true);
        userAccessService.updateUser(user.getUsername(), user);
        confirmationTokenService.setConfirmedAt(token);

        return "confirmed";
    }
}