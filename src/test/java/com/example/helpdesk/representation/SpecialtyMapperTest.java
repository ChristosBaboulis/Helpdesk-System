package com.example.helpdesk.representation;

import com.example.helpdesk.domain.Specialty;
import com.example.helpdesk.persistence.SpecialtyRepository;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class SpecialtyMapperTest {

    @Inject
    SpecialtyMapper specialtyMapper;
    @Inject
    SpecialtyRepository specialtyRepository;

    @Test
    @Transactional
    public void test() {
        SpecialtyRepresentation specialtyRepresentation = new SpecialtyRepresentation();
        specialtyRepresentation.specialtyType = "Test Type";

        Specialty specialty = specialtyMapper.toModel(specialtyRepresentation);

        Assertions.assertEquals("Test Type", specialty.getSpecialtyType());

        specialtyRepository.persist(specialty);

        SpecialtyRepresentation representation2 = specialtyMapper.toRepresentation(specialty);
        Assertions.assertNotNull(representation2);
        Assertions.assertEquals("Test Type", representation2.specialtyType);
        Assertions.assertNotNull(representation2.id);
    }

}
