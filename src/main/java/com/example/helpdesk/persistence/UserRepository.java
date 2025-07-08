package com.example.helpdesk.persistence;

import com.example.helpdesk.domain.User;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class UserRepository implements PanacheRepositoryBase<User, Integer> {

    public Optional<User> findByUsername(String username) {
        return find("username", username).firstResultOptional();
    }

}
