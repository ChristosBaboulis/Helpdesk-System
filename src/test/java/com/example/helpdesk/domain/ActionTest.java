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

    @BeforeEach
    public void setUp() {
        action = new CommunicationAction("test Action", "test descr", submDate, 25);
    }

    @Test
    public void checkGettersAndSetters() {
        assertEquals(25.0, action.getCallDuration(), 0.001);
        action.setCallDuration(55);

        action.getId();

        assertEquals("test Action", action.getTitle());
        action.setTitle("Test Title");

        assertEquals("test descr", action.getDescription());
        action.setDescription("Test Description");

        assertEquals(submDate, action.getSubmissionDate());
        action.setSubmissionDate(LocalDate.parse("04/01/2025", formatter));

        assertEquals(null, action.getRequest());
        action.setRequest(new Request());
    }
}
