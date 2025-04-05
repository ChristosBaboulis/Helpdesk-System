package com.example.helpdesk.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RequestCategoryTest {
    RequestCategory requestCategory;

    @BeforeEach
    public void setUp() {
        requestCategory = new RequestCategory("Connectivity");
    }

    @Test
    public void checkGettersAndSetters() {
        //GETTER OF ID
        requestCategory.getId();

        //GETTER, SETTER OF CATEGORY TYPE
        assertEquals("Connectivity", requestCategory.getCategoryType());
        requestCategory.setCategoryType("Power Problems");

        //GETTER, SETTER OF ASSOCIATED SPECIALTY
        assertEquals(null ,requestCategory.getSpecialty());
        requestCategory.setSpecialty(new Specialty("Power Problems"));
    }

    //TEST EQUALS OVERRIDE
    @Test
    public void checkEquality() {
        assertEquals(true, requestCategory.equals(requestCategory));
        assertEquals(false, requestCategory.equals(new Specialty()));
        assertEquals(false, requestCategory.equals(new RequestCategory()));
        assertEquals(false, requestCategory.equals(null));
        requestCategory.hashCode();
    }
}