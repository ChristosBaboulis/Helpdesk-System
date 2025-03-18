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
        assertEquals(2, statisticService.requestsPerMonth(3));

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
}
