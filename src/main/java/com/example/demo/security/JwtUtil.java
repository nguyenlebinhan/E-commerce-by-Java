package com.example.demo.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Value("${jwt.secret.key}")
    private String SECRET_KEY;

    public String generateToken(String email) throws IllegalArgumentException, JWTCreationException{
        return JWT.create()
                .withSubject("User Details")
                .withClaim("email",email)
                .withIssuedAt(new Date())
                .withIssuer("Event Scheduler")
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }
    public String validateTokenAndRetrieveSubject (String token) throws JWTVerificationException{
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET_KEY))
                .withSubject("User Details")
                .withIssuer("Event Scheduler").build();
        DecodedJWT jwt =verifier.verify(token);

        return jwt.getClaim("email").asString();
    }
}
