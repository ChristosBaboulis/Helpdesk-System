package com.example.helpdesk.auth;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AuthResponseTest {

    @Test
    public void testConstructorAndGetters() {
        String token = "sample-jwt-token";
        AuthResponse response = new AuthResponse(token);

        assertEquals(token, response.getToken(), "Token should match the one passed to constructor");
    }

    @Test
    public void testSetters() {
        AuthResponse response = new AuthResponse();
        String token = "new-token";
        response.setToken(token);

        assertEquals(token, response.getToken(), "Token should be updated correctly via setter");
    }
}
