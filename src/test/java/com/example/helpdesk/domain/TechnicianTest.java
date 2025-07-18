package com.example.helpdesk.domain;

import com.example.helpdesk.contacts.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

public class TechnicianTest {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    LocalDate birthdate = LocalDate.parse("03/01/1990", formatter);
    Technician technician;
    Technician technician2;
    Technician technician3;
    Address address;

    @BeforeEach
    public void setUp() {
        technician = new Technician("username1234", "123asd!!",
                "Christos2", "Bampoulis2",
                "69999990", "cb2@gg2.gr",
                birthdate, "Thiseos",
                "02", "Athens", "11112",
                "123 technician Code"
        );

        technician2 = new Technician("username1234", "123asd!!",
                "Christos2", "Bampoulis2",
                "69999990", "cb2@gg2.gr",
                birthdate, address, "345 code"
        );

        technician3 = new Technician();
    }

    @Test
    public void checkGettersAndSetters(){
        //---------------------- GETTERS - SETTERS ----------------------
        //GET - SET technicianCode
        assertEquals("123 technician Code", technician.getTechnicianCode());
        technician.setTechnicianCode("123");

        //GET Technician's Specialties
        assertEquals(new HashSet<>(), technician.getSpecialties());

        //PREPARE ASSOCIATED Request, Category, Specialty
        Request request = new Request();
        RequestCategory category = new RequestCategory();
        Specialty specialty = new Specialty();
        specialty.setSpecialtyType("Comms");
        category.setSpecialty(specialty);
        technician.setSpecialty(specialty);
        request.setRequestCategory(category);

        //---------------------- Specialty ----------------------
        //ASSIGN ALREADY ASSIGNED Specialty TO Technician
        Specialty specialty2 = new Specialty();
        technician.setSpecialty(specialty2);
        assertThrows(DomainException.class, () -> technician.setSpecialty(specialty2));
        technician.setSpecialty(null);

        //TEST REMOVAL OF Specialty
        technician.removeSpecialty(specialty2);
    }

    //TEST EQUALS OVERRIDE
    @Test
    public void checkEquality() {
        assertTrue(technician.equals(technician));
        assertFalse(technician.equals(new Specialty()));
        assertFalse(technician.equals(null));
        assertDoesNotThrow(() -> technician.hashCode());
    }

}
