package com.example.helpdesk.persistence;

//import com.example.helpdesk.domain.*;
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

//        em.createNativeQuery("delete from reviews").executeUpdate();
//        em.createNativeQuery("delete from review_invitations").executeUpdate();
//        em.createNativeQuery("delete from article_authors").executeUpdate();
//        em.createNativeQuery("delete from articles").executeUpdate();
//        em.createNativeQuery("delete from journals").executeUpdate();
//        em.createNativeQuery("delete from users").executeUpdate();
//        em.createNativeQuery("delete from authors").executeUpdate();

        tx.commit();
    }

    public void prepareData() {

        eraseData();

    }

}
