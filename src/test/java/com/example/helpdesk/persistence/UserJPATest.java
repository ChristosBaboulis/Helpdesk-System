package com.example.helpdesk.persistence;

import com.example.helpdesk.domain.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class UserJPATest extends JPATest {

    @Test
    public void listAllUsers(){
        List<User> result = em.createQuery("select u from User u", User.class).getResultList();
        assertEquals(1, result.size());
    }

}