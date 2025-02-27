package com.example.helpdesk.domain;

import com.example.helpdesk.contacts.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertEquals;

/*
* THIS CLASS IS CREATED MOSTLY TO DEMONSTRATE THE STEPS OF THE PROCESS
* WHEN A CUSTOMER CALLS THE HELPDESK AGENCY TO REPORT A PROBLEM THAT HE HAS
* BEFORE CALLING THE TEST METHOD THAT DEMONSTRATES THE TEST WE NEED TO
* INITIALIZE THE DATA THAT EXISTS TO THE AGENCY
* */

public class CaseFlow {
    Specialty connections;
    Specialty logistics;
    Specialty equipmentSetup;
    Specialty offers;

    RequestCategory internetConnection;
    RequestCategory telephoneConnection;
    RequestCategory pricing;
    RequestCategory contractInformation;
    RequestCategory firstSetup;
    RequestCategory routerMalfunction;
    RequestCategory routerChange;
    RequestCategory other;

    CustomerSupport c1;
    CustomerSupport c2;
    CustomerSupport c3;

    Technician t1;
    Technician t2;
    Technician t3;
    Technician t4;
    List<Technician> technicians = new ArrayList<>();

    //MOCK ADDRESS AND DATE TO BE USED
    Address mockAddress = new Address();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    LocalDate mockDate = LocalDate.parse("03/01/2025", formatter);

    //INITIALIZATION OF DATA THAT EXIST TO THE COMPANY ALWAYS (EMPLOYEES, CATEGORIES, SPECIALTIES)
    @BeforeEach
    public void setup(){
        //SPECIALTIES
        connections = new Specialty("Connectivity");
        logistics = new Specialty("Pricing");
        equipmentSetup = new Specialty("Equipment");
        offers = new Specialty("Offers");

        //REQUEST CATEGORIES
        internetConnection = new RequestCategory("Internet Connection Problem");
        internetConnection.setSpecialty(connections);
        telephoneConnection = new RequestCategory("Telephone Connection Problem");
        telephoneConnection.setSpecialty(connections);
        pricing = new RequestCategory("Pricing Problem");
        pricing.setSpecialty(logistics);
        contractInformation = new RequestCategory("Contract Information Problem");
        contractInformation.setSpecialty(logistics);
        firstSetup = new RequestCategory("First Setup of Equipment Problem");
        firstSetup.setSpecialty(equipmentSetup);
        routerMalfunction = new RequestCategory("Router Malfunction Problem");
        routerMalfunction.setSpecialty(equipmentSetup);
        routerChange = new RequestCategory("Router Change Problem");
        routerChange.setSpecialty(equipmentSetup);
        other = new RequestCategory("Other");

        //CUSTOMER SUPPORT
        c1 = new CustomerSupport("John-User", "Doe-Password", "John",
                "Doe", "1234567890",
                "jd@gmail.com", mockDate, mockAddress, "empl001");
        c2 = new CustomerSupport("John-User2", "Doe-Password2", "John2",
                "Doe2", "1234567899",
                "jd2@gmail.com", mockDate, mockAddress, "empl002");
        c3 = new CustomerSupport("John-User3", "Doe-Password3", "John3",
                "Doe3", "1234567898",
                "jd3@gmail.com", mockDate, mockAddress, "empl003");

        //TECHNICIANS
        t1 = new Technician("Michael-User", "Smith-Password", "Michael",
                "Smith", "1234567800",
                "ms@gmail.com", mockDate, mockAddress, "tec001");
        t1.setSpecialty(connections);
        t2 = new Technician("Michael2-User", "Smith2-Password", "Michael2",
                "Smith2", "1234567880",
                "ms2@gmail.com", mockDate, mockAddress, "tec002");
        t2.setSpecialty(connections);
        t2.setSpecialty(offers);
        t3 = new Technician("Michael3-User", "Smith3-Password", "Michael3",
                "Smith3", "1234567870",
                "ms@gmail.com", mockDate, mockAddress, "tec001");
        t3.setSpecialty(logistics);
        t4 = new Technician("Michael4-User", "Smith4-Password", "Michael4",
                "Smith4", "1234567860",
                "ms4@gmail.com", mockDate, mockAddress, "tec004");
        t4.setSpecialty(equipmentSetup);
        technicians.add(t1);
        technicians.add(t2);
        technicians.add(t3);
        technicians.add(t4);
    }

    @Test
    public void checkFlow() {
        //---------------------- FIRST STEP ----------------------
        //CUSTOMER CALLS AGENCY FOR A PROBLEM THEY ENCOUNTERED
        //CUSTOMER IS CONNECTED WITH CUSTOMER SUPPORT AGENT (EG c1)
        //NEW CUSTOMER INSTANCE IS CREATED
        Customer customer1 = new Customer("customer001", "Tom", "Ross",
                "6900000000", "tr@gmail.com", mockDate, mockAddress);

        //---------------------- SECOND STEP ----------------------
        //CUSTOMER SUPPORT AGENT CREATES REQUEST BASED ON CUSTOMER'S ISSUE ASSESSMENT
        Request r1 = new Request("2109999999",
                "Internet speed is not stable. Disconnections occur often.",
                internetConnection, customer1, c1);
        //IN CASE PROBLEM'S REQUEST CATEGORY WAS UNKNOWN, AGENT WILL ADD "Other" AS THE
        //REQUEST CATEGORY AND THEN THE REQUEST CATEGORY WILL BE CREATED ON THE SYSTEM
        // BASED ON THE DESCRIPTION OF THE PROBLEM,
        //ASSIGNED A SPECIALTY AND THEN BE SET ON THE REQUEST TO CONTINUE

        //AT ANY POINT FROM NOW ON REQUEST CAN BE REJECTED BY THE SYSTEM USING:
        //r1.reject();
        //AND THEN CLOSED AFTER BEING EVALUATED BY THE SYSTEM ADMINISTRATORS,
        //WHERE THEY DECIDE IF A NEW RELEVANT ONE NEEDS TO BE CREATED
        //OR REOPENED IF THEY JUDGE SO

        //---------------------- THIRD STEP ----------------------
        //NOW THE REQUEST NEEDS TO BE ASSIGNED TO A TECHNICIAN
        //BASED ON HIS SPECIALTY AND ACTIVE REQUESTS VOLUME
        int min = 50;
        boolean assigned = false;
        Technician r1_tec = new Technician();

        for(Technician technician : technicians){
            for(Specialty specialty : technician.getSpecialties()){
                if(Objects.equals(specialty.getSpecialtyType(), "Connectivity")){
                    if(technician.getActiveRequests() < min){
                        r1.assign(technician);
                        min = technician.getActiveRequests();
                        r1_tec = technician;
                        assigned = true;
                        break;
                    }
                }
            }
            if(assigned){break;}
        }

        //---------------------- THIRD STEP ----------------------
        //TECHNICIAN STARTS WORKING ON THE REQUEST
        //ADDS ACTIONS TO THE REQUEST DEPENDING ON HIS RESOLUTION STEPS
        Action changeChannel = new TechnicalAction("Change Channel",
                "Changed client's connection channel to test if problem is solved");
        r1.addAction(changeChannel);
        Action callClient1 = new CommunicationAction("Call with Client1",
                "Technician gets in touch with client to see if problem is resolved",
                5);
        r1.addAction(callClient1);

        //---------------------- FOURTH STEP ----------------------
        //CLIENT INFORMED THAT THE PROBLEM IS SOLVED SO REQUEST CAN NOW BE CLOSED
        r1.close();
        for(Technician technician : technicians){
            if(technician == r1_tec){
                assertEquals(0, technician.getRequests().size());
            }
        }

        //EXTRA BUSINESS LOGIC
        //ACTIONS AFTER REQUEST CLOSURE LIKE NOTIFYING CUSTOMER,
        //KEEPING STATISTICAL DATA
        //r1.notifyCustomer();
    }
}
