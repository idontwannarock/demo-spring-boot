package com.example.demospringboot.security;

public class SecurityConstants {

    public static final String LOGIN_URI = "/login";

    // Signing key for HS512 algorithm
    public static final String SECRET = "B?E(H+MbQeThWmYq3t6w9z$C&F)J@NcRfUjXn2r5u7x!A%D*G-KaPdSgVkYp3s6v";

    // JWT token defaults
    public static final String HEADER = "Authorization";
    public static final String PREFIX = "Bearer ";
    public static final int EXPIRATION_TIME = 24 * 60 * 60; // in seconds

    // Claim keys
    public static final String ROLES = "rol";
}
