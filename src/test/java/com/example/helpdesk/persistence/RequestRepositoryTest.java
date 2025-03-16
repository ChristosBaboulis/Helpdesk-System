package com.example.helpdesk.persistence;

import com.example.helpdesk.Fixture;
import com.example.helpdesk.IntegrationBase;
import com.example.helpdesk.domain.*;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@QuarkusTest
public class RequestRepositoryTest extends IntegrationBase {
    @Inject
    RequestRepository requestRepository;
    @Inject
    TechnicianRepository technicianRepository;
    @Inject
    CustomerRepository customerRepository;
    @Inject
    CustomerSupportRepository customerSupportRepository;
    @Inject
    RequestCategoryRepository requestCategoryRepository;

    @Test
    public void testFindByTelephoneNumber(){
        List<Request> requests = requestRepository.findByTelephoneNumber("1234567890");
        Assertions.assertEquals(Fixture.Requests.PHONE_NUMBER, requests.get(0).getTelephoneNumber());

        Request r2 = requestRepository.findByTelephoneNumber("1234567890").getFirst();
        Assertions.assertEquals(Fixture.Requests.PHONE_NUMBER, r2.getTelephoneNumber());
    }

    @Test
    public void testSearch(){
        Request request = requestRepository.search(6000);
        Assertions.assertEquals(6000, request.getId());
    }

    @Test
    public void testFindBySubmissionDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate submissionDate = LocalDate.parse("2025-03-16", formatter);

        Request request = requestRepository.findBySubmissionDate(submissionDate).getFirst();
        Assertions.assertEquals(6000, request.getId());
        Assertions.assertEquals(2, requestRepository.findBySubmissionDate(submissionDate).size());
    }

    @Test
    public void testFindByClosingDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate submissionDate = LocalDate.parse("2025-03-17", formatter);

        Request request = requestRepository.findByClosingDate(submissionDate).getFirst();
        Assertions.assertEquals(6001, request.getId());
    }

    @Test
    public void testFindByTechnician(){
        Technician t = technicianRepository.findById(4001);
        Assertions.assertEquals(4001, t.getId());

        Request request = requestRepository.findByTechnician(t).getFirst();
        Assertions.assertEquals(6000, request.getId());
    }

    @Test
    public void testFindByCustomer(){
        Customer c = customerRepository.findById(5001);
        Assertions.assertEquals(5001, c.getId());

        Request request = requestRepository.findByCustomer(c).getFirst();
        Assertions.assertEquals(6000, request.getId());
    }

    @Test
    public void testFindByCustomerSupport(){
        CustomerSupport cs = customerSupportRepository.findById(3001);
        Assertions.assertEquals(3001, cs.getId());

        Request request = requestRepository.findByCustomerSupport(cs).getFirst();
        Assertions.assertEquals(6000, request.getId());
    }

    @Test
    public void testFindByRequestCategory(){
        RequestCategory rc = requestCategoryRepository.findById(2001);
        Assertions.assertEquals(2001, rc.getId());

        Request request = requestRepository.findByRequestCategory(rc).getFirst();
        Assertions.assertEquals(6000, request.getId());
    }

    @Test
    public void testActiveRequests(){
        Request request = requestRepository.activeRequests().getFirst();
        Assertions.assertEquals(6000, request.getId());
    }

    @Test
    public void testClosedRequests(){
        Request request = requestRepository.closedRequests().getFirst();
        Assertions.assertEquals(6001, request.getId());
    }
}
