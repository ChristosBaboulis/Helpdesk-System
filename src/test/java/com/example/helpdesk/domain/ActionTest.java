package com.example.helpdesk.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ActionTest {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    LocalDate submissionDate = LocalDate.parse("03/01/2025", formatter);
    CommunicationAction action;
    CommunicationAction action2;
    CommunicationAction action3;
    TechnicalAction tecAction;
    TechnicalAction tecAction2;

    @BeforeEach
    public void setUp() {
        action = new CommunicationAction("test Action", "test descr", submissionDate, 25);
        action2 = new CommunicationAction("test Action 2", "test descr 2", 35);
        action3 = new CommunicationAction("test Action 2", 35);
        tecAction = new TechnicalAction("test tec Action", "test tec descr");
        tecAction2 = new TechnicalAction("test tec Action");
    }

    @Test
    public void checkGettersAndSetters() {
        //GETTER - SETTER OF callDuration
        assertEquals(25.0, action.getCallDuration(), 0.001);
        action.setCallDuration(55);

        //GETTER OF Action ID
        action.getId();

        //GETTER - SETTER OF Action's title
        assertEquals("test Action", action.getTitle());
        action.setTitle("Test Title");

        //GETTER - SETTER OF Action's description
        assertEquals("test descr", action.getDescription());
        action.setDescription("Test Description");

        //GETTER - SETTER OF Action's submissionDate
        assertEquals(submissionDate, action.getSubmissionDate());
        action.setSubmissionDate(LocalDate.parse("04/01/2025", formatter));
    }

}
