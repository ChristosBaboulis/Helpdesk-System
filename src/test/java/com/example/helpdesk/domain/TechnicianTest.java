package com.example.helpdesk.domain;

import com.example.helpdesk.contacts.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

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
        //---------------------- GETTERS-SETTERS ----------------------
        //GET, SET TECHNICIANS CODE
        assertEquals("123 technician Code", technician.getTechnicianCode());
        technician.setTechnicianCode("123");

        //GET, SET TECHNICIANS NUMBER OF REQUESTS
        assertEquals(0, technician.getActiveRequests());
        technician.setActiveRequests(0);

        //GET TECHNICIAN'S SPECIALTIES
        assertEquals(new HashSet<>(), technician.getSpecialties());

        //PREPARE ASSOCIATED REQUEST, CATEGORY, SPECIALTY
        Request request = new Request();
        RequestCategory category = new RequestCategory();
        Specialty specialty = new Specialty();
        specialty.setSpecialtyType("Comms");
        category.setSpecialty(specialty);
        technician.setSpecialty(specialty);
        request.setRequestCategory(category);


        //---------------------- REQUEST ----------------------
        //ASSIGN REQUEST TO TECHNICIAN
        technician.setRequest(request);

        //GET NUMBER OF REQUESTS ASSIGNED TO TECHNICIAN
        assertEquals(1, technician.getActiveRequests());

        //TEST NULL BRANCH OF setRequest
        technician.setRequest(null);

        //TEST BRANCH OF ALREADY ASSIGNED REQUEST
        assertThrows(DomainException.class, () -> technician.setRequest(request));

        //TEST REMOVAL OF REQUEST FROM TECHNICIAN
        technician.removeRequest(request);
        assertEquals(0, technician.getActiveRequests());

        //ASSIGN REQUEST WITH WRONG SPECIALTY TO TECHNICIAN
        request.getRequestCategory().getSpecialty().setSpecialtyType("New Type");
        assertThrows(DomainException.class, () -> technician.setRequest(request));

        //ASSIGN REQUEST WITH NO SPECIALTY ASSOCIATED TO THE CATEGORY TO TECHNICIAN
        request.getRequestCategory().setSpecialty(null);
        assertThrows(DomainException.class, () -> technician.setRequest(request));

        //ASSIGN REQUEST WITH NO CATEGORY TO TECHNICIAN
        request.setRequestCategory(null);
        assertThrows(DomainException.class, () -> technician.setRequest(request));


        //---------------------- SPECIALTY ----------------------
        //ASSIGN ALREADY ASSIGNED SPECIALTY TO TECHNICIAN
        Specialty specialty2 = new Specialty();
        technician.setSpecialty(specialty2);
        assertThrows(DomainException.class, () -> technician.setSpecialty(specialty2));
        technician.setSpecialty(null);

        //TEST REMOVAL OF SPECIALTY
        technician.removeSpecialty(specialty2);
    }
}