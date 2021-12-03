//package com.rad.ms.corona_view.access.Security;
//
//import com.rad.ms.corona_view.access.Entities.User;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jws;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.stereotype.Service;
//
//import java.security.Key;
//import java.time.Instant;
//import java.time.temporal.ChronoUnit;
//import java.util.Date;
//
//@Service
//public class JWTTokenService implements  TokenService{
//    private static final String JWT_SECRET = "EDF44B726C9596F6BCFEC99C68227";
//
//    @Override
//    public String generateToken(User user) {
//        Instant expirationTime = Instant.now().plus(1, ChronoUnit.HOURS);
//        Date expirationDate = Date.from(expirationTime);
//
//        Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes());
//
//        String compactTokenString = Jwts.builder()
//                .claim("username", user.getUsername())
//                .claim("role", user.getRoleId())
//                .setExpiration(expirationDate)
//                .signWith(key, SignatureAlgorithm.HS256)
//                .compact();
//
//        return "Bearer " + compactTokenString;
//    }
//
//    @Override
//    public UserPrincipal parseToken(String token) {
//        byte[] secretBytes = JWT_SECRET.getBytes();
//
//        Jws<Claims> jwsClaims = Jwts.parserBuilder()
//                .setSigningKey(secretBytes)
//                .build()
//                .parseClaimsJws(token);
//
//        String username = jwsClaims.getBody().get("username", String.class);
//        String roleId = jwsClaims.getBody().get("role", String.class);
//
//        return new UserPrincipal(username, roleId);
//    }
//}
