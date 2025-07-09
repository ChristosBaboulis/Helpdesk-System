package com.example.helpdesk.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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

        //GETTER - SETTER OF Specialty's type
        assertEquals("Connectivity", specialty.getSpecialtyType());
        specialty.setSpecialtyType("Power Problems");
    }

    //TEST EQUALS OVERRIDE
    @Test
    public void checkEquality() {
        assertTrue(specialty.equals(specialty));
        assertFalse(specialty.equals(new RequestCategory()));
        assertFalse(specialty.equals(new Specialty()));
        assertFalse(specialty.equals(null));
        assertDoesNotThrow(() -> specialty.hashCode());
    }

}
