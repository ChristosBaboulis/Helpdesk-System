package com.example.helpdesk.persistence;

import com.example.helpdesk.domain.*;
import jakarta.persistence.Query;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ActionJPATest extends JPATest{
    @Test
    public void listAllActions(){
        List<Action> result = em.createQuery("select a from Action a").getResultList();
        assertEquals(2, result.size());
    }

    @Test
    public void listAllCommunicationActions(){
        List<CommunicationAction> result = em.createQuery("select c from CommunicationAction c").getResultList();
        assertEquals(1, result.size());
    }

    @Test
    public void listAllTechnicalActions(){
        List<TechnicalAction> result = em.createQuery("select t from TechnicalAction t").getResultList();
        assertEquals(1, result.size());
    }

    @Test
    public void fetchActionByRequest(){
        Query query = em.createQuery("select a from Action a "+
                "join a.request r WHERE r.telephoneNumber = :telephoneNumber");
        query.setParameter("telephoneNumber", "1234567890");
        List<Action> result = query.getResultList();
        assertEquals(2, result.size());

        Request request = result.get(0).getRequest();
        assertEquals("1234567890", request.getTelephoneNumber());
    }

    @Test
    public void fetchCommunicationActionByRequest(){
        Query query = em.createQuery("select ca from CommunicationAction ca "+
                "join ca.request r WHERE r.telephoneNumber = :telephoneNumber");
        query.setParameter("telephoneNumber", "1234567890");
        List<Action> result = query.getResultList();
        assertEquals(1, result.size());
        assertEquals("Test Title", result.get(0).getTitle());

        Request request = result.get(0).getRequest();
        assertEquals("1234567890", request.getTelephoneNumber());
    }

    @Test
    public void fetchTechnicalActionByRequest(){
        Query query = em.createQuery("select ta from TechnicalAction ta "+
                "join ta.request r WHERE r.telephoneNumber = :telephoneNumber");
        query.setParameter("telephoneNumber", "1234567890");
        List<Action> result = query.getResultList();
        assertEquals(1, result.size());
        assertEquals("Test Title 2", result.get(0).getTitle());

        Request request = result.get(0).getRequest();
        assertEquals("1234567890", request.getTelephoneNumber());
    }
}
