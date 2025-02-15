package com.example.helpdesk.persistence;

import com.example.helpdesk.domain.Action;
import com.example.helpdesk.domain.CommunicationAction;
import com.example.helpdesk.domain.TechnicalAction;
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

}
