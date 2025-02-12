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

        tx.commit();
    }

    public void prepareData() {

        eraseData();
        //Researcher r1 = new Researcher("Nikos", "Diamantidis", "AUEB", "ndia@aueb.gr");
        //Editor e1 = new Editor("Paris", "Avgeriou",
        //        "University of Groningen", "avgeriou@gmail.com");
        //Journal j1 = new Journal("Journal of Systems and Software", "0164-1212");
        //j1.setEditor(e1);
        //Author a11 = new Author("Pooja", "Rani", "University of Bern", "pooja.rani@unibe.ch");
        /*
        Article article1 = new Article();
        article1.setTitle("A decade of code comment quality assessment: A systematic literature review");
        article1.setSummary("Code comments are important artifacts in software systems and play" +
                " a paramount role in many software engineering (SE) tasks...");
        article1.setKeywords("Code comments\n" +
                "Documentation quality\n" +
                "Systematic literature review");
        article1.setJournal(j1);
        article1.setCorrespondentAuthor(r1);
        article1.addAuthor(a11);
        * */

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        //em.persist(r1);

        tx.commit();

        em.close();

    }

}
