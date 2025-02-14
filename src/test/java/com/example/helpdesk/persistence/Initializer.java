package com.example.helpdesk.persistence;

import com.example.helpdesk.domain.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.HashSet;
import java.util.Set;

public class Initializer {

    private EntityManager em;

    public Initializer() {
        em = JPAUtil.getCurrentEntityManager();
    }

    private void eraseData() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        em.createNativeQuery("delete from technician_specialties").executeUpdate();
        em.createNativeQuery("delete from users").executeUpdate();
        em.createNativeQuery("delete from request_categories").executeUpdate();
        em.createNativeQuery("delete from specialties").executeUpdate();

        tx.commit();
    }

    public void prepareData() {

        eraseData();

        Specialty specialty = new Specialty("Connectivity Issues Specialization");

        CustomerSupport newCustomerSupport = new CustomerSupport("username123", "123asd!",
                "Christos", "Bampoulis",
                "69999999", "cb@gg.gr",
                "01/01/1990", "Gripari",
                "01", "Athens", "11111",
                "123 employee Code"
                );

        Set<Specialty> specialties = new HashSet<>();
        specialties.add(specialty);

        Technician newTechnician = new Technician("username1234", "123asd!!",
                "Christos2", "Bampoulis2",
                "69999990", "cb2@gg2.gr",
                "02/01/1990", "Thiseos",
                "02", "Athens", "11112",
                "123 technician Code", 5,
                specialties
                );



        RequestCategory requestCategory = new RequestCategory("Connectivity Issues", specialty);

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        em.persist(newCustomerSupport);
        em.persist(newTechnician);
        em.persist(specialty);
        em.persist(requestCategory);

        tx.commit();
        em.close();
    }
}
