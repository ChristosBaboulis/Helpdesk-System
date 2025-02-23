package com.example.helpdesk.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;

public class ActionTest {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    LocalDate submDate = LocalDate.parse("03/01/2025", formatter);
    CommunicationAction action;
    CommunicationAction action2;
    CommunicationAction action3;
    TechnicalAction tecAction;
    TechnicalAction tecAction2;

    @BeforeEach
    public void setUp() {
        action = new CommunicationAction("test Action", "test descr", submDate, 25);
        action2 = new CommunicationAction("test Action 2", "test descr 2", 35);
        action3 = new CommunicationAction("test Action 2", 35);
        tecAction = new TechnicalAction("test tec Action", "test tec descr");
        tecAction2 = new TechnicalAction("test tec Action");
    }

    @Test
    public void checkGettersAndSetters() {
        //GETTER, SETTER OF CALL DURATION
        assertEquals(25.0, action.getCallDuration(), 0.001);
        action.setCallDuration(55);

        //GETTER OF ACTION ID
        action.getId();

        //GETTER, SETTER OF ACTION'S TITLE
        assertEquals("test Action", action.getTitle());
        action.setTitle("Test Title");

        //GETTER, SETTER OF ACTION'S DESCRIPTION
        assertEquals("test descr", action.getDescription());
        action.setDescription("Test Description");

        //GETTER, SETTER OF ACTION'S SUBMISSION'S DATE
        assertEquals(submDate, action.getSubmissionDate());
        action.setSubmissionDate(LocalDate.parse("04/01/2025", formatter));

        //GETTER, SETTER OF ACTION'S ASSOCIATED REQUEST
        assertEquals(null, action.getRequest());
        action.setRequest(new Request());
    }
}
