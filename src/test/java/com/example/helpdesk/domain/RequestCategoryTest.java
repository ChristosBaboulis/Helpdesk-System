package com.example.helpdesk.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class RequestCategoryTest {
    RequestCategory requestCategory;

    @BeforeEach
    public void setUp() {
        requestCategory = new RequestCategory("Connectivity");
    }

    @Test
    public void checkGettersAndSetters() {
        requestCategory.getId();

        assertEquals("Connectivity", requestCategory.getCategoryType());
        requestCategory.setCategoryType("Power Problems");
        requestCategory.setSpecialty(new Specialty("Power Problems"));
    }

    @Test
    public void checkEquality() {
        assertEquals(true, requestCategory.equals(requestCategory));
        assertEquals(false, requestCategory.equals(new Specialty()));
        assertEquals(false, requestCategory.equals(new RequestCategory()));
        requestCategory.hashCode();
    }
}
