package com.example.helpdesk.persistence;

import com.example.helpdesk.domain.RequestCategory;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class RequestCategoryJPATest extends JPATest {
    @Test
    public void listAllRequestCategories() {
        List<RequestCategory> result = em.createQuery("select r from RequestCategory r").getResultList();
        assertEquals(1, result.size());
    }
}