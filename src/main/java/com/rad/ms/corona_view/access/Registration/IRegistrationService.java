package com.rad.ms.corona_view.access.Registration;

public interface IRegistrationService {
    String register(RegistrationRequest request);
    String confirmToken(String token);
}
