package com.example.helpdesk.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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

        //GETTER - SETTER OF categoryType
        assertEquals("Connectivity", requestCategory.getCategoryType());
        requestCategory.setCategoryType("Power Problems");

        //GETTER - SETTER OF ASSOCIATED specialty
        assertEquals(null ,requestCategory.getSpecialty());
        requestCategory.setSpecialty(new Specialty("Power Problems"));
    }

    //TEST EQUALS OVERRIDE
    @Test
    public void checkEquality() {
        assertTrue(requestCategory.equals(requestCategory));
        assertFalse(requestCategory.equals(new Specialty()));
        assertFalse(requestCategory.equals(new RequestCategory()));
        assertFalse(requestCategory.equals(null));
        assertDoesNotThrow(() -> requestCategory.hashCode());
    }

}
