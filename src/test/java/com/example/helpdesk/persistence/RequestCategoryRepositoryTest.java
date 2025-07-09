package com.example.helpdesk.persistence;

import com.example.helpdesk.IntegrationBase;
import com.example.helpdesk.domain.RequestCategory;
import com.example.helpdesk.domain.Specialty;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class RequestCategoryRepositoryTest extends IntegrationBase {

    @Inject
    RequestCategoryRepository requestCategoryRepository;
    @Inject
    SpecialtyRepository specialtyRepository;

    @Test
    public void testSearch(){
        RequestCategory requestCategory = requestCategoryRepository.findById(2003);
        Assertions.assertEquals(2003, requestCategory.getId());
    }

    @Test
    public void testFindBySpecialty(){
        Specialty specialty = specialtyRepository.findById(1002);

        RequestCategory requestCategory = requestCategoryRepository.findBySpecialty(specialty).getFirst();
        Assertions.assertEquals(2005, requestCategory.getId());
        Assertions.assertEquals(3, requestCategoryRepository.findBySpecialty(specialty).size());
    }

}
