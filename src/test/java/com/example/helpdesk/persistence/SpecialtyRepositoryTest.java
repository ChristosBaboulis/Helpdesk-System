package com.example.helpdesk.persistence;

import com.example.helpdesk.IntegrationBase;
import com.example.helpdesk.domain.Specialty;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class SpecialtyRepositoryTest extends IntegrationBase {
    @Inject
    SpecialtyRepository specialtyRepository;

    @Test
    public void testSearch(){
        Specialty specialty = specialtyRepository.findById(1002);
        Assertions.assertEquals(1002, specialty.getId());
    }

    @Test
    public void testFindBySpecialtyType(){
        Specialty specialty = specialtyRepository.findBySpecialtyType("Offers").getFirst();
        Assertions.assertEquals(1003, specialty.getId());
    }
}
