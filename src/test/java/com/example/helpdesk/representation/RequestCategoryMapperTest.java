package com.example.helpdesk.representation;

import com.example.helpdesk.domain.RequestCategory;
import com.example.helpdesk.persistence.RequestCategoryRepository;
import com.example.helpdesk.persistence.SpecialtyRepository;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class RequestCategoryMapperTest {

    @Inject
    RequestCategoryMapper requestCategoryMapper;
    @Inject
    RequestCategoryRepository requestCategoryRepository;
    @Inject
    SpecialtyRepository specialtyRepository;
    @Inject
    SpecialtyMapper specialtyMapper;

    @Test
    @Transactional
    public void testRequestCategoryMapper() {
        RequestCategoryRepresentation representation = new RequestCategoryRepresentation();

        SpecialtyRepresentation specialtyRepresentation = specialtyMapper.toRepresentation(specialtyRepository.findById(1000));

        representation.categoryType = "Test";
        representation.specialty = specialtyRepresentation;

        RequestCategory requestCategory = requestCategoryMapper.toModel(representation);

        Assertions.assertEquals("Test", requestCategory.getCategoryType());
        Assertions.assertEquals(specialtyRepository.findById(1000), requestCategory.getSpecialty());

        requestCategoryRepository.persist(requestCategory);

        RequestCategoryRepresentation representation2 = requestCategoryMapper.toRepresentation(requestCategory);
        Assertions.assertNotNull(representation2);
        Assertions.assertEquals("Test", representation2.categoryType);
        Assertions.assertNotNull(representation2.specialty);
        Assertions.assertNotNull(representation2.id);
    }

}
