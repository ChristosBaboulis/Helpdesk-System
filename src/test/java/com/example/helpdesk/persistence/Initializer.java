package com.example.helpdesk.persistence;

import com.example.helpdesk.domain.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class Initializer {

    private EntityManager em;

    public Initializer() {
        em = JPAUtil.getCurrentEntityManager();
    }

    private void eraseData() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        em.createNativeQuery("delete from users").executeUpdate();

        tx.commit();
    }

    public void prepareData() {

        eraseData();

        CustomerSupport newCustomerSupport = new CustomerSupport("username123", "123asd!",
                "Christos", "Bampoulis",
                "69999999", "cb@gg.gr",
                "01/01/1990", "Gripari",
                "01", "Athens", "11111",
                "123 employee Code");

        Technician newTechnician = new Technician("username1234", "123asd!!",
                "Christos2", "Bampoulis2",
                "69999990", "cb2@gg2.gr",
                "02/01/1990", "Thiseos",
                "02", "Athens", "11112",
                "123 technician Code", 5);

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        em.persist(newCustomerSupport);
        em.persist(newTechnician);

        tx.commit();
        em.close();
    }
}
