package com.bci.bci.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Component
public class JWTUtil {
    private static final Long ACCESS_TOKEN_TTL = 10000L;
    private static final String ACCESS_TOKEN = "xCTM8eEKft2ZBP6p5z7dXqN4WDmLbsUHnQ3augGx9cJYkAvjrhVXUzpJQDwHkWAN8dsVR6jygf5CMBLr7YvbZ9nGxu2FhqcSa3P4mJSZBgyU7ARXEk6MjpnPrh4twQ3vam2YuDWsdCcLxqzb5VGeK9T";

    public String createToken(String email) {

        Date tokenCreateTime = new Date();
        Date tokenValidity = new Date(tokenCreateTime.getTime() + TimeUnit.MINUTES.toMillis(ACCESS_TOKEN_TTL));

        return Jwts.builder()
                .setSubject(email)
                .setExpiration(tokenValidity)
                .signWith(SignatureAlgorithm.HS512, ACCESS_TOKEN)
                .compact();
    }

    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(ACCESS_TOKEN)
                .parseClaimsJws(token)
                .getBody();
    }
}
