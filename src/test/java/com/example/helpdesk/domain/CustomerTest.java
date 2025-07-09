package com.example.helpdesk.domain;

import com.example.helpdesk.contacts.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    LocalDate birthdate = LocalDate.parse("03/01/1990", formatter);
    Customer customer;
    Customer customer2;
    Customer customer3;
    Address address;

    @BeforeEach
    public void setup(){
        customer = new Customer("123 customer code",
                "Christos3", "Bampoulis3",
                "69999991", "cb3@gg3.gr",
                birthdate, "Davaki",
                "03", "Athens", "11113"
        );

        address = new Address("test", "12", "test", "12345");
        customer2 = new Customer("1234 customer code",
                "Christos4", "Bampoulis4",
                "69999992", "cb3@gg4.gr",
                birthdate, address
        );

        customer3 = new Customer();
    }

    @Test
    public void checkGettersAndSetters(){
        //GETTER - SETTER OF customerCode
        assertEquals("123 customer code", customer.getCustomerCode());
        customer.setCustomerCode("new code");

        //GETTER - SETTER OF ASSOCIATED personalInfo
        assertEquals("Christos3", customer.getPersonalInfo().getFirstName());
        customer.getPersonalInfo().setFirstName("new name");

        assertEquals("Bampoulis3", customer.getPersonalInfo().getLastName());
        customer.getPersonalInfo().setLastName("last name");

        assertEquals("cb3@gg3.gr", customer.getPersonalInfo().getEmailAddress());
        customer.getPersonalInfo().setEmailAddress("test@gmail.com");

        assertEquals(address, customer2.getPersonalInfo().getAddress());
        customer.getPersonalInfo().setAddress(address);

        assertEquals(birthdate, customer.getPersonalInfo().getBirthdate());
        customer.getPersonalInfo().setBirthdate(birthdate);

        assertEquals("69999991", customer.getPersonalInfo().getTelephoneNumber());
        customer.getPersonalInfo().setTelephoneNumber("1234567890");

        customer.setPersonalInfo(new PersonalInfo());
    }

    //TEST IF EQUALS OVERRIDE
    @Test
    public void checkEquality(){
        assertTrue(customer.equals(customer));
        assertFalse(customer.equals(customer2));
        assertFalse(customer.equals(null));
        assertFalse(customer.equals(address));
        assertDoesNotThrow(() -> customer.hashCode());

        PersonalInfo personalInfo = new PersonalInfo();
        PersonalInfo personalInfo2 = new PersonalInfo("Christos", "Bampoulis", "1233211231",
                "cb@g.gr", birthdate, address);
        PersonalInfo personalInfo3 = new PersonalInfo("Christos", "Bampoulis", "3213211232",
                "cb123@g.gr", birthdate, address);
        PersonalInfo personalInfo4 = new PersonalInfo("Christos1", "Bampoulis", "3213211232",
                "cb123@g.gr", birthdate, address);
        assertFalse(personalInfo.equals(personalInfo2));
        assertTrue(personalInfo2.equals(personalInfo3));
        assertFalse(personalInfo3.equals(personalInfo4));
        personalInfo4.setFirstName("Christos");
        personalInfo4.setLastName("Bampoulis2");
        assertFalse(personalInfo3.equals(personalInfo4));
        personalInfo4.setLastName("Bampoulis");
        LocalDate birthdate2 = LocalDate.parse("03/01/1991", formatter);
        personalInfo3.setBirthdate(birthdate2);
        assertFalse(personalInfo3.equals(personalInfo4));
        assertFalse(personalInfo.equals(null));
        assertFalse(personalInfo.equals(address));
        assertDoesNotThrow(personalInfo::hashCode);
    }

}
