package com.example.helpdesk.persistence;

import com.example.helpdesk.domain.CustomerSupport;
import com.example.helpdesk.domain.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class UserJPATest extends JPATest {

    @Test
    public void listAllUsers(){
        List<User> result = em.createQuery("select u from User u").getResultList();
        assertEquals(1, result.size());
    }

    @Test
    public void listAllCustomerSupport(){
        List<CustomerSupport> result = em.createQuery("select c from CustomerSupport c").getResultList();
        assertEquals(1, result.size());
    }

}