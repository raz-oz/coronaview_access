package com.rad.ms.corona_view.access.JWT;

import com.auth0.jwt.algorithms.Algorithm;

public class JwtConfig {
    private final String secretKey;
    private final String tokenPrefix;
    private final Algorithm algorithm;

    public long getExpirationTime() {
        return 10*60*1000; // 10 Minutes
    }

    private static class SingletonHolder {
        private static JwtConfig instance = new JwtConfig();
    }
    private JwtConfig() {
        secretKey = "DA6E1D6DFE98B97FC7AA8A7B95B52";
        tokenPrefix = "Bearer ";
        algorithm = Algorithm.HMAC256(secretKey.getBytes());
    }
    public static JwtConfig getInstance() {
        return SingletonHolder.instance;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public String getTokenPrefix() {
        return tokenPrefix;
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }
}

