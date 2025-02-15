package com.example.helpdesk.persistence;

import com.example.helpdesk.domain.Request;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class RequestJPATest extends JPATest{

    @Test
    public void listAllRequests() {
        List<Request> result = em.createQuery("select r from Request r").getResultList();
        assertEquals(1, result.size());
    }



}
