package com.example.helpdesk.representation;

import com.example.helpdesk.domain.*;
import com.example.helpdesk.persistence.*;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class RequestMapperTest {

    @Inject
    RequestMapper requestMapper;
    @Inject
    RequestRepository requestRepository;
    @Inject
    RequestCategoryRepository requestCategoryRepository;
    @Inject
    RequestCategoryMapper requestCategoryMapper;
    @Inject
    CustomerMapper customerMapper;
    @Inject
    CustomerRepository customerRepository;
    @Inject
    CustomerSupportRepository customerSupportRepository;
    @Inject
    CustomerSupportMapper customerSupportMapper;
    @Inject
    TechnicianRepository technicianRepository;
    @Inject
    TechnicianMapper technicianMapper;

    @Test
    @Transactional
    public void testRequestMapper() {
        RequestCategory requestCategory = requestCategoryRepository.findById(2001);
        Customer customer = customerRepository.findById(5001);
        CustomerSupport customerSupport = customerSupportRepository.findById(3001);
        Technician technician = technicianRepository.findById(4001);

        RequestRepresentation requestRepresentation = new RequestRepresentation();
        requestRepresentation.telephoneNumber = "123456789";
        requestRepresentation.problemDescription = "Problem Description";
        requestRepresentation.requestCategory = requestCategoryMapper.toRepresentation(requestCategory);
        requestRepresentation.customer = customerMapper.toRepresentation(customer);
        requestRepresentation.customerSupport = customerSupportMapper.toRepresentation(customerSupport);
        requestRepresentation.technician = technicianMapper.toRepresentation(technician);

        Request request = requestMapper.toModel(requestRepresentation);

        Assertions.assertEquals("123456789", request.getTelephoneNumber());
        Assertions.assertEquals("Problem Description", request.getProblemDescription());
        Assertions.assertEquals(requestCategory, request.getRequestCategory());
        Assertions.assertEquals(customer, request.getCustomer());
        Assertions.assertEquals(customerSupport, request.getCustomerSupport());
        Assertions.assertEquals(technician, request.getTechnician());
        Assertions.assertEquals(Status.ACTIVE, request.getStatus());
        Assertions.assertNotNull(request.getSubmissionDate());
        Assertions.assertNull(request.getCloseDate());

        requestRepository.persist(request);

        RequestRepresentation requestRepresentation2 = requestMapper.toRepresentation(request);
        Assertions.assertEquals("123456789", requestRepresentation2.telephoneNumber);
        Assertions.assertEquals("Problem Description", requestRepresentation2.problemDescription);
        Assertions.assertEquals("Internet Connection Problem", requestRepresentation2.requestCategory.categoryType);
        Assertions.assertEquals("CUST005", requestRepresentation2.customer.customerCode);
        Assertions.assertEquals("EMP001", requestRepresentation2.customerSupport.emplCode);
        Assertions.assertEquals("TECH001", requestRepresentation2.technician.technicianCode);
        Assertions.assertEquals(Status.ACTIVE, requestRepresentation2.status);
        Assertions.assertNotNull(request.getSubmissionDate());
    }

}
