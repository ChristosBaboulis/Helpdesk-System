package com.example.helpdesk.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class RequestTest {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    LocalDate birthdate = LocalDate.parse("03/01/1990", formatter);
    LocalDate submissionDate = LocalDate.parse("03/01/2025", formatter);
    Request request = new Request();

    @BeforeEach
    public void setup(){
        Specialty specialty = new Specialty("Connectivity Issues Specialization");
        RequestCategory requestCategory = new RequestCategory("Connectivity Issues", specialty);

        Customer customer = new Customer("123 customer code",
                "Christos3", "Bampoulis3",
                "69999991", "cb3@gg3.gr",
                birthdate, "Davaki",
                "03", "Athens", "11113"
        );

        CustomerSupport customerSupport = new CustomerSupport("username123", "123asd!",
                "Christos", "Bampoulis",
                "69999999", "cb@gg.gr",
                birthdate, "Gripari",
                "01", "Athens", "11111",
                "123 employee Code"
        );

        Technician technician = new Technician("username1234", "123asd!!",
                "Christos2", "Bampoulis2",
                "69999990", "cb2@gg2.gr",
                birthdate, "Thiseos",
                "02", "Athens", "11112",
                "123 technician Code"
        );
        technician.setSpecialty(specialty);

        CommunicationAction comAction = new CommunicationAction("Com Action Title", "Test description",
                submissionDate,123);
        TechnicalAction tecAction = new TechnicalAction("Tech Action Title", "Test description",
                submissionDate);

        Set<Action> actions = new HashSet<>();
        actions.add(tecAction);
        actions.add(comAction);

        request = new Request("1234567890","Test problem description",
                submissionDate,"Active", requestCategory,
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
        assertEquals("Active", request.getStatus());
        request.setStatus("Inactive");
        assertEquals("Connectivity Issues", request.getRequestCategory().getCategoryType());
        assertEquals("Connectivity Issues Specialization", request.getRequestCategory().getSpecialty().getSpecialtyType());
        assertEquals("123 customer code", request.getCustomer().getCustomerCode());
        assertEquals("123 employee Code", request.getCustomerSupport().getEmplCode());
        assertEquals("123 technician Code", request.getTechnician().getTechnicianCode());
        assertEquals(true, request.canClose());

        Set<Action> actionsToRemove = new HashSet<>(request.getActions());
        for (Action action : actionsToRemove) {
            request.removeActions(action);
        }
        assertEquals(false, request.canClose());
    }
}
