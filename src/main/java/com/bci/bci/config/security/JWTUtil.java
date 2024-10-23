package com.bci.bci.config.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

public class JWTUtil {

    private static final Long ACCESS_TOKEN_TTL= 10000L;
    private static final String ACCESS_TOKEN = "xhmeMUN9y64uStBGFjvQgnJDLV5cTsaXKYEpwAkH";

    public static String generateToken(String name, String email){
        var expirationTime = ACCESS_TOKEN_TTL * 1000;
        var claims = new HashMap<String, String>();
        claims.put("name", name);
        claims.put("email", email);

        return Jwts.builder()
                .claims(claims)
                .subject(email)
                .expiration(new Date(System.currentTimeMillis()+expirationTime))
                .signWith(Keys.hmacShaKeyFor(ACCESS_TOKEN.getBytes(StandardCharsets.UTF_8)))
                .compact();
    }

    public static UsernamePasswordAuthenticationToken validateToken(String token){
        try {
            var claims = Jwts.parser()
                    .setSigningKey(ACCESS_TOKEN.getBytes(StandardCharsets.UTF_8))
                    .build()
                    .parseEncryptedClaims(token)
                    .getPayload();

            return new UsernamePasswordAuthenticationToken(claims.getSubject(), null, Collections.emptyList());
        }catch (JwtException ex){
            throw null;
        }
    }
}
