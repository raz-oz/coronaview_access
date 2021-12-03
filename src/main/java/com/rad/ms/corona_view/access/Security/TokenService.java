package com.rad.ms.corona_view.access.Security;

import com.rad.ms.corona_view.access.Entities.User;

public interface TokenService {
    String generateToken(User user);
    UserPrincipal parseToken(String token);
}
