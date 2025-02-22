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
        assertEquals("123 technician Code", technician.getTechnicianCode());
        technician.setTechnicianCode("123");

        assertEquals(0, technician.getActiveRequests());
        technician.setActiveRequests(0);

        assertEquals(new HashSet<>(), technician.getSpecialties());

        Request request = new Request();
        RequestCategory category = new RequestCategory();
        Specialty specialty = new Specialty();
        specialty.setSpecialtyType("Comms");
        category.setSpecialty(specialty);
        technician.setSpecialty(specialty);
        request.setRequestCategory(category);
        technician.setRequest(request);

        assertEquals(1, technician.getActiveRequests());

        technician.setRequest(null);

        assertThrows(DomainException.class, () -> technician.setRequest(request));

        technician.removeRequest(request);
        assertEquals(0, technician.getActiveRequests());

        Specialty specialty2 = new Specialty();
        technician.setSpecialty(specialty2);
        assertThrows(DomainException.class, () -> technician.setSpecialty(specialty));
        technician.setSpecialty(null);

        technician.removeSpecialty(specialty2);
    }
}
