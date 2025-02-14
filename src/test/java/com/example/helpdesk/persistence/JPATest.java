package com.example.helpdesk.persistence;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.util.logging.Logger;

public class JPATest {
    EntityManager em;
    private static final Logger logger = Logger.getLogger(JPATest.class.getName());

    @BeforeEach
    public void setup() {

        Initializer initializer = new Initializer();
        initializer.prepareData();

        em = JPAUtil.getCurrentEntityManager();
    }

    @AfterEach
    public void tearDown() {
        em.close();
    }
}