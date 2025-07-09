package com.example.helpdesk.representation;

import com.example.helpdesk.contacts.Address;
import com.example.helpdesk.domain.Technician;
import com.example.helpdesk.persistence.TechnicianRepository;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

@QuarkusTest
public class TechnicianMapperTest {

    @Inject
    TechnicianMapper technicianMapper;
    @Inject
    TechnicianRepository technicianRepository;

    @Test
    @Transactional
    public void testMapper(){
        TechnicianRepresentation technicianRepresentation = new TechnicianRepresentation();
        technicianRepresentation.technicianCode = "123";
        technicianRepresentation.firstName = "John";
        technicianRepresentation.lastName = "Doe";
        technicianRepresentation.username = "johnDoe";
        technicianRepresentation.password = "password";
        technicianRepresentation.telephoneNumber = "123456789";
        technicianRepresentation.emailAddress = "johndoe@example.com";
        technicianRepresentation.birthdate = LocalDate.of(1990, 1, 1);
        technicianRepresentation.address = new Address();

        Technician technician = technicianMapper.toModel(technicianRepresentation);

        Assertions.assertEquals("123", technician.getTechnicianCode());
        Assertions.assertEquals("John", technician.getPersonalInfo().getFirstName());
        Assertions.assertEquals("Doe", technician.getPersonalInfo().getLastName());
        Assertions.assertEquals("johndoe@example.com", technician.getPersonalInfo().getEmailAddress());
        Assertions.assertEquals("123456789", technician.getPersonalInfo().getTelephoneNumber());
        Assertions.assertEquals("johnDoe", technician.getUsername());
        Assertions.assertEquals("password", technician.getPassword());
        Assertions.assertEquals(LocalDate.of(1990, 1, 1), technician.getPersonalInfo().getBirthdate());
        Assertions.assertEquals(new Address(), technician.getPersonalInfo().getAddress());

        technicianRepository.persist(technician);

        TechnicianRepresentation representation2 = technicianMapper.toRepresentation(technician);
        Assertions.assertNotNull(representation2);
        Assertions.assertEquals("123", representation2.technicianCode);
        Assertions.assertEquals("John", representation2.firstName);
        Assertions.assertEquals("Doe", representation2.lastName);
        Assertions.assertEquals("johndoe@example.com", representation2.emailAddress);
        Assertions.assertEquals("123456789", representation2.telephoneNumber);
        Assertions.assertEquals("johnDoe", representation2.username);
        Assertions.assertEquals("password", representation2.password);
        Assertions.assertEquals(LocalDate.of(1990, 1, 1), representation2.birthdate);
        Assertions.assertEquals(new Address(), representation2.address);
        Assertions.assertNotNull(representation2.id);
    }

}
