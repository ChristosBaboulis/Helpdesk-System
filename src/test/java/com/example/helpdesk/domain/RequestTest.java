package com.example.helpdesk.domain;

import com.example.helpdesk.util.SystemDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RequestTest {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    LocalDate birthdate = LocalDate.parse("03/01/1990", formatter);
    LocalDate submissionDate = LocalDate.parse("03/01/2025", formatter);
    Request request = new Request();
    Request request2 = new Request();
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
                requestCategory,
                customer, customerSupport);
        request.addAction(comAction);
        request.addAction(tecAction);
        request.setTechnician(technician);

        request2 = new Request("1234567890",
                "Test problem description 2",
                requestCategory,
                customer, customerSupport);
    }

    @Test
    public void checkGettersAndSetters() {
        //GETTER, SETTER OF ID
        request.getId();

        //GETTER, SETTER OF REQUEST'S TELEPHONE NUMBER
        assertEquals("1234567890", request.getTelephoneNumber());
        request.setTelephoneNumber("1234567899");

        //GETTER, SETTER OF REQUEST'S PROBLEM DESCRIPTION
        assertEquals("Test problem description", request.getProblemDescription());
        request.setProblemDescription("Test description 2");

        //GETTER, SETTER OF REQUEST'S SUBMISSION DATE
        assertEquals(SystemDate.now(), request.getSubmissionDate());
        request.setSubmissionDate(LocalDate.now());

        //GETTER, SETTER OF REQUEST'S CLOSE DATE
        assertEquals(null, request.getCloseDate());
        request.setCloseDate(LocalDate.now());

        //GETTER, SETTER OF REQUEST'S STATUS
        assertEquals(Status.ACTIVE, request.getStatus());
        request.setStatus(Status.ACTIVE);

        //GETTER, SETTER OF REQUEST'S ASSOCIATED CATEGORY
        assertEquals("Connectivity Issues", request.getRequestCategory().getCategoryType());
        request.setRequestCategory(requestCategory);

        //GETTER OF REQUEST'S ASSOCIATED CATEGORY'S SPECIALTY
        assertEquals("Connectivity Issues Specialization", request.getRequestCategory().getSpecialty().getSpecialtyType());

        //GETTER, SETTER OF REQUEST'S ASSOCIATED CUSTOMER
        assertEquals("123 customer code", request.getCustomer().getCustomerCode());
        request.setCustomer(customer);

        //GETTER, SETTER OF REQUEST'S ASSOCIATED CUSTOMER SUPPORT EMPLOYEE
        assertEquals("123 employee Code", request.getCustomerSupport().getEmplCode());
        request.setCustomerSupport(customerSupport);

        //GETTER, SETTER OF REQUEST'S ASSOCIATED TECHNICIAN
        assertEquals("123 technician Code", request.getTechnician().getTechnicianCode());
        request.setTechnician(technician);

        //COVERAGE OF DOMAIN EXCEPTION CLASS
        DomainException dm = new DomainException();

        //TEST ADDITION OF AN ACTION TO REQUEST
        assertThrows(DomainException.class, () -> request.addAction(comAction));
        request.addAction(null);
        request.addAction(new TechnicalAction("Test action", "this is a new description", submissionDate));
    }

    //TEST CLOSE OF REQUEST
    @Test
    public void checkClose() {
        request.close();
        assertEquals(SystemDate.now(), request.getCloseDate());

        Set<Action> actionsToRemove = new HashSet<>(request.getActions());
        for (Action action : actionsToRemove) {
            request.removeAction(action);
        }
        assertThrows(DomainException.class, () -> request.close());

        request.addAction(new TechnicalAction("Test action", "this is a new description", submissionDate));
        request.setTechnician(null);
        assertThrows(DomainException.class, () -> request.close());
    }

    //TEST REJECTION OF REQUEST
    @Test
    public void checkRejection() {
        request.reject();
        assertEquals(Status.REJECTED, request.getStatus());
        request.close();
        assertThrows(DomainException.class, () -> request.reject());
    }

    //TEST ASSOCIATION OF TECHNICIAN TO REQUEST
    @Test
    public void checkTechnicianAssignment() {
        //Correct technician with more than 1 specialty
        technician.setSpecialty(new Specialty("Extra useless specialty"));
        request2.assign(technician);
        assertEquals(Status.RESOLVING, request2.getStatus());

        //Wrong Technician according to specialty
        Technician wrongTechnician = new Technician("username1234", "123asd!!",
                "Christos2", "Bampoulis2",
                "69999990", "cb2@gg2.gr",
                birthdate, "Thiseos",
                "02", "Athens", "11112",
                "123 technician Code");
        Specialty wrongSpecialty = new Specialty("Something wrong");
        wrongTechnician.setSpecialty(wrongSpecialty);
        wrongTechnician.setSpecialty(new Specialty("Extra useless specialty"));
        assertThrows(DomainException.class, () -> request2.assign(wrongTechnician));

        //Problems with requst category, specialty
        request2.getRequestCategory().setSpecialty(null);
        assertThrows(DomainException.class, () -> request2.assign(technician));

        request2.getRequestCategory().setSpecialty(specialty);
        request2.setRequestCategory(null);
        assertThrows(DomainException.class, () -> request2.assign(technician));

        request2.addAction(comAction);
        request2.close();
        assertThrows(DomainException.class, () -> request2.assign(technician));
    }

    //TEST ACCEPTANCE OF REQUEST
    @Test
    public void checkAccept() {
        request.accept();
        assertEquals(Status.ACTIVE, request.getStatus());
        request.close();
        assertThrows(DomainException.class, () -> request.accept());
    }

    //TEST OF BAD REQUEST CREATION
    @Test
    public void checkRestrictions() {
        assertThrows(DomainException.class, () -> new Request(null, "problemDescription", requestCategory,
                customer, customerSupport));
        assertThrows(DomainException.class, () -> new Request("12345", "problemDescription", requestCategory,
                customer, customerSupport));
        assertThrows(DomainException.class, () -> new Request("1234599999", null, requestCategory,
                customer, customerSupport));
        assertThrows(DomainException.class, () -> new Request("1234599999", "problemDescription", null,
                customer, customerSupport));
        assertThrows(DomainException.class, () -> new Request("1234599999", "problemDescription", requestCategory,
                null, customerSupport));
        assertThrows(DomainException.class, () -> new Request("1234599999", "problemDescription", requestCategory,
                customer, null));
        assertThrows(DomainException.class, () -> new Request("", "problemDescription", requestCategory,
                customer, customerSupport));
        assertThrows(DomainException.class, () -> new Request("1234599999", "", requestCategory,
                customer, customerSupport));

    }




    //TEST NOTIFICATION OF CUSTOMER WHEN REQUEST IS SOLVED
    @Test
    public void checkNotifyCustomer() {
        assertEquals(false, request.notifyCustomer());
        request.close();
        assertEquals(true, request.notifyCustomer());
    }

    //TEST EQUALS OVVERIDE
    @Test
    public void checkEquals() {
        assertEquals(true, request.equals(request));
    }

}

