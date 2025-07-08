package com.example.helpdesk.auth;

import com.example.helpdesk.domain.User;
import com.example.helpdesk.persistence.UserRepository;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.Duration;
import java.util.Optional;

@ApplicationScoped
public class AuthService {

    @Inject
    UserRepository userRepository;

    public Optional<String> authenticate(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);

        if (userOpt.isPresent() && userOpt.get().getPassword().equals(password)) {
            User user = userOpt.get();

            String token = Jwt.issuer("helpdesk-system")
                    .upn(user.getUsername())
                    .groups(user.getRole()) // role claim from @Formula
                    .expiresIn(Duration.ofHours(1))
                    .sign();

            return Optional.of(token);
        }

        return Optional.empty();
    }

}
