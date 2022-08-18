package com.example.managementbackend.common;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.managementbackend.registration.LoginData;
import java.util.Date;

public class JwtUtils {

    private static final String SECRET = "SECRET_KEY";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String SIGN_UP_URL = "/user-login";


    private static Algorithm getAlgorithm() {
        Algorithm algorithm = Algorithm.HMAC256(SECRET.getBytes());
        return algorithm;
    }

    public static DecodedJWT verifyToken(String authorizationHeader) {
        final String token = authorizationHeader.substring((TOKEN_PREFIX + " ").length());
        JWTVerifier jwtVerifier = JWT.require(getAlgorithm()).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        return decodedJWT;

    }

    public static String generateToken(LoginData loginData) {
        return JWT.create().withSubject(loginData.getEmail())
                .withExpiresAt(getExpireTime(60))
                .sign(getAlgorithm());

    }

    public static String getRefreshToken(LoginData loginData) {
        return JWT.create().withSubject(loginData.getEmail())
                .withExpiresAt(getExpireTime(120))
                .sign(getAlgorithm());
    }

    private static Date getExpireTime(int mins) {
        return new Date(System.currentTimeMillis() + (1000 * 60 * mins));
    }
}
