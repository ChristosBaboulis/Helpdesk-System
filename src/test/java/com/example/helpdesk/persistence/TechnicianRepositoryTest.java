package com.example.helpdesk.persistence;


import com.example.helpdesk.IntegrationBase;
import com.example.helpdesk.domain.Technician;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class TechnicianRepositoryTest extends IntegrationBase {

    @Inject
    TechnicianRepository technicianRepository;
    @Inject
    SpecialtyRepository specialtyRepository;

    @Test
    public void search(){
        Technician technician = technicianRepository.findById(4003);
        Assertions.assertEquals(4003, technician.getId());
    }

    @Test
    public void testFindByLastName(){
        Technician technician = technicianRepository.findByLastName("Wilson").getFirst();
        Assertions.assertEquals(4002, technician.getId());
    }

    @Test
    public void testFindByTelephoneNumber(){
        Technician technician = technicianRepository.findByTelephoneNumber("4567893210").getFirst();
        Assertions.assertEquals(4003, technician.getId());
    }

    @Test
    public void testFindByTechnicianCode(){
        Technician technician = technicianRepository.findByTechnicianCode("TECH001").getFirst();
        Assertions.assertEquals(4001, technician.getId());
    }

    @Test
    public void testFindByEmailAddress(){
        Technician technician = technicianRepository.findByEmailAddress("sophia.anderson@example.com").getFirst();
        Assertions.assertEquals(4004, technician.getId());
    }

    @Test
    public void testFindBySpecialtyType(){
        Technician technician = technicianRepository.findBySpecialtyType(specialtyRepository.findById(1002).getSpecialtyType()).getFirst();
        Assertions.assertEquals(4004, technician.getId());
    }

    @Test
    public void testGetRole() {
        Technician technician = technicianRepository.findById(4004);
        Assertions.assertEquals("TECHNICIAN", technician.getRole());
    }

}
