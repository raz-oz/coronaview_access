package com.rad.ms.corona_view.access.Registration;

import com.rad.ms.corona_view.access.Entities.Role;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequest {
    private String username;
    private String password;
    private String email;
    private String cellphoneNumber;
}