package com.example.helpdesk.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class SpecialtyTest {
    Specialty specialty;

    @BeforeEach
    public void setUp() {
        specialty = new Specialty("Connectivity");
    }

    @Test
    public void checkGettersAndSetters() {
        //GETTER OF ID
        specialty.getId();

        //GETTER, SETTER OF SPECIALTY'S TYPE
        assertEquals("Connectivity", specialty.getSpecialtyType());
        specialty.setSpecialtyType("Power Problems");
    }

    //TEST EQUALS OVERRIDE
    @Test
    public void checkEquality() {
        assertEquals(true, specialty.equals(specialty));
        assertEquals(false, specialty.equals(new RequestCategory()));
        assertEquals(false, specialty.equals(new Specialty()));
        specialty.hashCode();
    }
}