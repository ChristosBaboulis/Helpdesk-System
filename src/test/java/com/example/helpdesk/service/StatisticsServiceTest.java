package com.example.helpdesk.service;

import com.example.helpdesk.domain.*;
import com.example.helpdesk.persistence.RequestRepository;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import io.quarkus.test.TestTransaction;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class StatisticsServiceTest {

    @Inject
    StatisticService statisticService;

    @Inject
    RequestRepository requestRepository;

    @Test
    @TestTransaction
    void testRequestsPerMonth() {
        //CHECK FOR MARCH - SHOULD FIND 2 Requests
        assertEquals(3, statisticService.requestsPerMonth(3));

        //CHECK FOR MONTH W/O Requests (E.G. JANUARY)
        assertEquals(0, statisticService.requestsPerMonth(1));
    }

    @Test
    void testInvalidMonthInput() {
        try {
            statisticService.requestsPerMonth(0);
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid Month: 0", e.getMessage());
        }

        try {
            statisticService.requestsPerMonth(13);
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid Month: 13", e.getMessage());
        }
    }

    @Test
    @TestTransaction
    void testAverageCommunicationActionsPerCategory() {
        //FETCH Requests FROM DB
        Request request1 = requestRepository.findById(6001);
        Request request2 = requestRepository.findById(6002);

        if (request1 == null || request2 == null) {
            throw new RuntimeException("Requests not found");
        }

        //ADD Actions TO Request 1
        request1.addAction(new CommunicationAction("Follow-up Call", "Customer needed additional help", 10.0));

        //ADD Actions TO Request 2
        request2.addAction(new CommunicationAction("Initial Inquiry", "Customer had a question about their bill", 8.5));
        request2.addAction(new CommunicationAction("Technical Support Call", "Resolved technical issue", 12.0));

        //SAVE Requests WITH Actions
        requestRepository.persist(request1);
        requestRepository.persist(request2);

        //CALCULATE AVERAGE
        Double avgActions = statisticService.averageCommunicationActionsPerCategory(2001);

        //CHECK RESULT
        assertEquals(1.5, avgActions, 0.01, "Ο μέσος όρος CommunicationActions δεν είναι σωστός!");

        //CHECK FOR A Category W/O Requests (E.G. 2003)
        Double avgForEmptyCategory = statisticService.averageCommunicationActionsPerCategory(2003);
        assertEquals(0.0, avgForEmptyCategory, "Ο μέσος όρος πρέπει να είναι 0 αν δεν υπάρχουν requests.");
    }

}
