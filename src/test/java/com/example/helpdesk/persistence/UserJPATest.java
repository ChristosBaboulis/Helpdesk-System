package com.example.helpdesk.persistence;

import com.example.helpdesk.domain.*;
import jakarta.persistence.Query;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class UserJPATest extends JPATest {

    @Test
    public void listAllUsers(){
        List<User> result = em.createQuery("select u from User u").getResultList();
        assertEquals(2, result.size());
    }

    @Test
    public void listAllCustomerSupport(){
        List<CustomerSupport> result = em.createQuery("select c from CustomerSupport c").getResultList();
        assertEquals(1, result.size());
    }

    @Test
    public void listAllTechnician(){
        List<Technician> result = em.createQuery("select t from Technician t").getResultList();
        assertEquals(1, result.size());

        Technician technician = result.get(0);
        Set<Request> requests = technician.getRequests();
        assertEquals(1, requests.size());
    }

    @Test
    public void fetchTechnicianBySpecialty(){
        Query query = em.createQuery("select t from Technician t "+
                "join t.specialties s WHERE s.specialtyType = :specialtyType");
        query.setParameter("specialtyType", "Connectivity Issues Specialization");
        List<Technician> result = query.getResultList();
        assertEquals(1, result.size());

        List<Specialty> specialties = new ArrayList<>(result.get(0).getSpecialties());
        Specialty specialty = specialties.get(0);
        assertEquals("Connectivity Issues Specialization", specialty.getSpecialtyType());
    }
}