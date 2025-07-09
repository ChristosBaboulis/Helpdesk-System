package com.example.helpdesk.auth;

import com.example.helpdesk.IntegrationBase;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class AuthServiceTest extends IntegrationBase {

    @Inject
    AuthService authService;

    @Test
    public void testValidCredentialsReturnsToken() {
        Optional<String> token = authService.authenticate("csupport1", "securePass123");

        assertTrue(token.isPresent(), "Token should be present");
        assertFalse(token.get().isBlank(), "Token should not be blank");
    }

    @Test
    public void testInvalidPasswordReturnsEmpty() {
        Optional<String> token = authService.authenticate("csupport1", "wrongPassword");

        assertTrue(token.isEmpty(), "Should return empty when password is wrong");
    }

    @Test
    public void testUnknownUsernameReturnsEmpty() {
        Optional<String> token = authService.authenticate("unknownUser", "any");

        assertTrue(token.isEmpty(), "Should return empty for unknown username");
    }

}
