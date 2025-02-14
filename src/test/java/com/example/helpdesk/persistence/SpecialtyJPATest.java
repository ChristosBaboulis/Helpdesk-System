package com.example.helpdesk.persistence;

import com.example.helpdesk.domain.Specialty;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class SpecialtyJPATest extends JPATest{

    @Test
    public void listAllSpecialties() {
        List<Specialty> result = em.createQuery("select s from Specialty s").getResultList();
        assertEquals(1, result.size());
    }
}
