package com.example.helpdesk.persistence;

import com.example.helpdesk.domain.Action;
import com.example.helpdesk.domain.Request;
import jakarta.persistence.Query;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class RequestJPATest extends JPATest{

    @Test
    public void listAllRequests() {
        List<Request> result = em.createQuery("select r from Request r").getResultList();
        assertEquals(1, result.size());

        Request request = result.get(0);
        assertNotNull(request.getRequestCategory());
        assertNotNull(request.getCustomer());
        assertNotNull(request.getCustomerSupport());

        Set<Action> actions = request.getActions();
        assertEquals(2, actions.size());
    }

    @Test
    public void listRequestsByAssignedTechnician(){
        Query query = em.createQuery("select r from Request r where r.technician.personalInfo.emailAddress =: email");
        query.setParameter("email", "cb2@gg2.gr");
        List<Request> result = query.getResultList();
        assertEquals(1, result.size());
    }

    @Test
    public void fetchRequestWithActionsAndAssignedTechnician(){
        Query query = em.createQuery("select r from Request r " +
                "join fetch r.actions " +
                "join fetch r.technician t " +
                "where t.personalInfo.emailAddress=:email");
        query.setParameter("email", "cb2@gg2.gr");
        List<Request> result = query.getResultList();
        assertEquals(1, result.size());

        em.close();
    }
}