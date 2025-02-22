package com.example.helpdesk.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class RequestTest {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    LocalDate birthdate = LocalDate.parse("03/01/1990", formatter);
    LocalDate submissionDate = LocalDate.parse("03/01/2025", formatter);
    Request request = new Request();
    Specialty specialty;
    RequestCategory requestCategory;
    Customer customer;
    CustomerSupport customerSupport;
    Technician technician;
    CommunicationAction comAction;
    TechnicalAction tecAction;

    @BeforeEach
    public void setup(){
        specialty = new Specialty("Connectivity Issues Specialization");
        requestCategory = new RequestCategory("Connectivity Issues", specialty);

        customer = new Customer("123 customer code",
                "Christos3", "Bampoulis3",
                "69999991", "cb3@gg3.gr",
                birthdate, "Davaki",
                "03", "Athens", "11113"
        );

        customerSupport = new CustomerSupport("username123", "123asd!",
                "Christos", "Bampoulis",
                "69999999", "cb@gg.gr",
                birthdate, "Gripari",
                "01", "Athens", "11111",
                "123 employee Code"
        );

        technician = new Technician("username1234", "123asd!!",
                "Christos2", "Bampoulis2",
                "69999990", "cb2@gg2.gr",
                birthdate, "Thiseos",
                "02", "Athens", "11112",
                "123 technician Code"
        );
        technician.setSpecialty(specialty);

        comAction = new CommunicationAction("Com Action Title", "Test description",
                submissionDate,123);
        tecAction = new TechnicalAction("Tech Action Title", "Test description",
                submissionDate);

        Set<Action> actions = new HashSet<>();
        actions.add(tecAction);
        actions.add(comAction);

        request = new Request("1234567890","Test problem description",
                submissionDate,Status.ACTIVE, requestCategory,
                customer, customerSupport,
                technician, actions);
    }

    @Test
    public void checkGettersAndSetters() {
        request.getId();

        assertEquals("1234567890", request.getTelephoneNumber());
        request.setTelephoneNumber("1234567899");

        assertEquals("Test problem description", request.getProblemDescription());
        request.setProblemDescription("Test description 2");

        assertEquals(submissionDate, request.getSubmissionDate());
        request.setSubmissionDate(LocalDate.now());

        assertEquals(Status.ACTIVE, request.getStatus());
        request.setStatus(Status.ACTIVE);

        assertEquals("Connectivity Issues", request.getRequestCategory().getCategoryType());
        request.setRequestCategory(requestCategory);

        assertEquals("Connectivity Issues Specialization", request.getRequestCategory().getSpecialty().getSpecialtyType());

        assertEquals("123 customer code", request.getCustomer().getCustomerCode());
        request.setCustomer(customer);

        assertEquals("123 employee Code", request.getCustomerSupport().getEmplCode());
        request.setCustomerSupport(customerSupport);

        assertEquals("123 technician Code", request.getTechnician().getTechnicianCode());
        request.setTechnician(technician);

        DomainException dm = new DomainException();
        assertThrows(DomainException.class, () -> request.addAction(comAction));
        request.addAction(null);
        request.addAction(new TechnicalAction("Test action", "this is a new description", submissionDate));
    }

    @Test
    public void checkClose() {
        request.close();

        Set<Action> actionsToRemove = new HashSet<>(request.getActions());
        for (Action action : actionsToRemove) {
            request.removeAction(action);
        }
        assertThrows(DomainException.class, () -> request.close());
    }

    @Test
    public void checkEquals() {
        assertEquals(true, request.equals(request));
    }
}
