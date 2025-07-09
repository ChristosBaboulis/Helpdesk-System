package com.example.helpdesk.persistence;

import com.example.helpdesk.IntegrationBase;
import com.example.helpdesk.domain.User;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

@QuarkusTest
public class UserRepositoryTest extends IntegrationBase {

    @Inject
    UserRepository userRepository;

    @Test
    public void testFindByUsername_validUser() {
        Optional<User> result = userRepository.findByUsername("csupport1");
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("csupport1", result.get().getUsername());
    }

    @Test
    public void testFindByUsername_invalidUser() {
        Optional<User> result = userRepository.findByUsername("nonexistent");
        Assertions.assertTrue(result.isEmpty());
    }

}
