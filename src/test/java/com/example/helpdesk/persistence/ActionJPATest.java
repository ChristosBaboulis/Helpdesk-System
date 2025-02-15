package com.example.helpdesk.persistence;

import com.example.helpdesk.domain.Action;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ActionJPATest extends JPATest{
    @Test
    public void listAllActions(){
        List<Action> result = em.createQuery("select a from Action a").getResultList();
        assertEquals(1, result.size());
    }
}
