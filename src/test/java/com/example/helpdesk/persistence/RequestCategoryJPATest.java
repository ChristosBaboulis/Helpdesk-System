package com.example.helpdesk.persistence;

import com.example.helpdesk.domain.RequestCategory;
import com.example.helpdesk.domain.Specialty;
import com.example.helpdesk.domain.Technician;
import jakarta.persistence.Query;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class RequestCategoryJPATest extends JPATest {
    @Test
    public void listAllRequestCategories() {
        List<RequestCategory> result = em.createQuery("select r from RequestCategory r").getResultList();
        assertEquals(1, result.size());
    }

    @Test
    public void fetchCategoryBySpecialty(){
        Query query = em.createQuery("select c from RequestCategory c "+
                "join c.specialty s WHERE s.specialtyType = :specialtyType");
        query.setParameter("specialtyType", "Connectivity Issues Specialization");
        List<RequestCategory> result = query.getResultList();
        assertEquals(1, result.size());

        Specialty specialty = result.get(0).getSpecialty();
        assertEquals("Connectivity Issues Specialization", specialty.getSpecialtyType());
    }
}