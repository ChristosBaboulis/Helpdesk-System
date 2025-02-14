package com.example.helpdesk.persistence;

import com.example.helpdesk.domain.Customer;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class CustomerJPATest extends JPATest{
    @Test
    public void listAllCustomers(){
        List<Customer> result = em.createQuery("select c from Customer c").getResultList();
        assertEquals(1, result.size());
    }
}
