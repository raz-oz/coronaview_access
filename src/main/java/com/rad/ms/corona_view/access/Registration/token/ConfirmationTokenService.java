package com.rad.ms.corona_view.access.Registration.token;

import com.rad.ms.corona_view.access.Entities.User;
import com.rad.ms.corona_view.access.ErrorHandling.UserNotFoundException;
import com.rad.ms.corona_view.access.Repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
//@AllArgsConstructor
public class ConfirmationTokenService {

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    public void saveConfirmationToken(ConfirmationToken token) {
        confirmationTokenRepository.save(token);
    }

    public Optional<ConfirmationToken> getToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }

    public void setConfirmedAt(String token) {

        Optional<ConfirmationToken> Token_to_update_opt = confirmationTokenRepository.findByToken(token);
        if (Token_to_update_opt.isPresent()){
            ConfirmationToken Token_to_update = Token_to_update_opt.get();
            Token_to_update.setConfirmedAt(LocalDateTime.now());
            confirmationTokenRepository.save(Token_to_update);
        }
        else
            throw  new IllegalStateException("token expired");
    }
}