package com.example.helpdesk.persistence;

//import com.example.helpdesk.domain.*;
import com.example.helpdesk.domain.CustomerSupport;
import com.example.helpdesk.domain.User;
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
                "01", "Athens", "11111", "123 employee Code");

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        em.persist(newCustomerSupport);

        tx.commit();
        em.close();
    }
}
