package com.example.helpdesk.service;

import com.example.helpdesk.domain.*;
import com.example.helpdesk.persistence.CustomerRepository;
import com.example.helpdesk.persistence.CustomerSupportRepository;
import com.example.helpdesk.persistence.RequestCategoryRepository;
import com.example.helpdesk.persistence.RequestRepository;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import io.quarkus.test.TestTransaction;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class StatisticsServiceTest {

    @Inject
    StatisticService statisticService;

    @Inject
    RequestRepository requestRepository;

    @Inject
    CustomerRepository customerRepository;

    @Inject
    CustomerSupportRepository customerSupportRepository;

    @Inject
    RequestCategoryRepository requestCategoryRepository;

    @Test
    @TestTransaction
    void testRequestsPerMonth() {
        // Έλεγχος για τον Μάρτιο (πρέπει να βρει 2 requests)
        assertEquals(3, statisticService.requestsPerMonth(3));

        // Έλεγχος για μήνα χωρίς requests (π.χ. Ιανουάριος)
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
        // Φέρνουμε τα requests από τη βάση
        Request request1 = requestRepository.findById(6001);
        Request request2 = requestRepository.findById(6002);

        if (request1 == null || request2 == null) {
            throw new RuntimeException("Requests not found");
        }

        // Προσθήκη actions στο request1
        request1.addAction(new CommunicationAction("Follow-up Call", "Customer needed additional help", 10.0));

        // Προσθήκη actions στο request2
        request2.addAction(new CommunicationAction("Initial Inquiry", "Customer had a question about their bill", 8.5));
        request2.addAction(new CommunicationAction("Technical Support Call", "Resolved technical issue", 12.0));

        // Αποθήκευση των requests με τα actions
        requestRepository.persist(request1);
        requestRepository.persist(request2);

        // Υπολογισμός του μέσου όρου
        Double avgActions = statisticService.averageCommunicationActionsPerCategory(2001);

        // Έλεγχος αποτελέσματος
        assertEquals(1.5, avgActions, 0.01, "Ο μέσος όρος CommunicationActions δεν είναι σωστός!");

        // Έλεγχος για μια κατηγορία που δεν έχει requests (π.χ. 2003)
        Double avgForEmptyCategory = statisticService.averageCommunicationActionsPerCategory(2003);
        assertEquals(0.0, avgForEmptyCategory, "Ο μέσος όρος πρέπει να είναι 0 αν δεν υπάρχουν requests.");
    }


}
