package com.example.helpdesk.persistence;

import com.example.helpdesk.domain.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
        em.createNativeQuery("delete from requests").executeUpdate();
        em.createNativeQuery("delete from users").executeUpdate();
        em.createNativeQuery("delete from request_categories").executeUpdate();
        em.createNativeQuery("delete from specialties").executeUpdate();
        em.createNativeQuery("delete from customers").executeUpdate();
        em.createNativeQuery("delete from actions").executeUpdate();

        tx.commit();
    }

    public void prepareData() {

        eraseData();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate birthdate = LocalDate.parse("03/01/1990", formatter);
        LocalDate submissionDate = LocalDate.parse("03/01/2025", formatter);

        Specialty specialty = new Specialty("Connectivity Issues Specialization");

        CustomerSupport newCustomerSupport = new CustomerSupport("username123", "123asd!",
                "Christos", "Bampoulis",
                "69999999", "cb@gg.gr",
                birthdate, "Gripari",
                "01", "Athens", "11111",
                "123 employee Code"
                );

        Technician newTechnician = new Technician("username1234", "123asd!!",
                "Christos2", "Bampoulis2",
                "69999990", "cb2@gg2.gr",
                birthdate, "Thiseos",
                "02", "Athens", "11112",
                "123 technician Code"
                );
        newTechnician.setSpecialty(specialty);

        RequestCategory requestCategory = new RequestCategory("Connectivity Issues", specialty);

        Customer customer = new Customer("123 customer code",
                "Christos3", "Bampoulis3",
                "69999991", "cb3@gg3.gr",
                birthdate, "Davaki",
                "03", "Athens", "11113"
                );

        CommunicationAction comAction = new CommunicationAction("Test Title", "Test description",
                submissionDate,123);
        TechnicalAction tecAction = new TechnicalAction("Test Title", "Test description",
                submissionDate);

        Set<Action> actions = new HashSet<>();
        actions.add(tecAction);
        actions.add(comAction);

        Request request = new Request("1234567890","Test problem description",
                submissionDate,Status.ACTIVE, requestCategory,
                customer, newCustomerSupport,
                newTechnician, actions);

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        em.persist(newCustomerSupport);
        em.persist(newTechnician);
        em.persist(specialty);
        em.persist(requestCategory);
        em.persist(customer);
        em.persist(comAction);
        em.persist(tecAction);
        em.persist(request);

        tx.commit();
        em.close();
    }
}
