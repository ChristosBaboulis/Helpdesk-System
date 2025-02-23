package com.example.helpdesk.domain;

import com.example.helpdesk.contacts.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;

public class CustomerSupportTest {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    LocalDate birthdate = LocalDate.parse("03/01/1990", formatter);
    CustomerSupport customerSupport;
    Address address;

    @BeforeEach
    public void setUp() {
        customerSupport = new CustomerSupport("ChristosBampoulis", "123pass",
                "Christos", "Bampoulis", "1233211231", "cb@g.gr",
                birthdate, address, "2222 empl code");
    }

    @Test
    public void checkGettersAndSetters() {
        //GETTER, SETTER OF EMPLOYEE'S CODE
        customerSupport.setEmplCode("2223 empl code");
        assertEquals("2223 empl code", customerSupport.getEmplCode());

        //GETTER OF EMPLOYEE'S ID
        customerSupport.getId();

        //TEST OF ASSOCIATED PERSONAL INFO GETTER, SETTER
        assertEquals("Christos", customerSupport.getPersonalInfo().getFirstName());
        customerSupport.setPersonalInfo(new PersonalInfo("Christos", "Bampoulis", "1233211231", "cb@g.gr",
                birthdate, address));

        //GETTER, SETTER OF USERNAME
        assertEquals("ChristosBampoulis", customerSupport.getUsername());
        customerSupport.setUsername("ChristosBampoulis2");

        //GETTER, SETTER OF PASSWORD
        assertEquals("123pass", customerSupport.getPassword());
        customerSupport.setPassword("1234pass");
    }

    //TEST OF EQUALS OVERRIDE
    @Test
    public void checkEquals() {
        assertEquals(true, customerSupport.equals(customerSupport));
        assertEquals(false, customerSupport.equals(new CustomerSupport()));
        assertEquals(false, customerSupport.equals(new Customer()));
        customerSupport.hashCode();
    }
}