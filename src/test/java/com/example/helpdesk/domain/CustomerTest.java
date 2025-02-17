package com.example.helpdesk.domain;

import com.example.helpdesk.contacts.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;

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
        assertEquals("123 customer code", customer.getCustomerCode());
        customer.setCustomerCode("new code");

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

    @Test
    public void checkEquality(){
        assertEquals(true, customer.equals(customer));
        assertEquals(false, customer.equals(customer2));
        assertEquals(false, customer.equals(null));
        customer.hashCode();

        PersonalInfo personalInfo = new PersonalInfo();
        PersonalInfo personalInfo2 = new PersonalInfo("Christos", "Bampoulis", "1233211231",
                "cb@g.gr", birthdate, address);
        PersonalInfo personalInfo3 = new PersonalInfo("Christos", "Bampoulis", "3213211232",
                "cb123@g.gr", birthdate, address);
        assertEquals(false, personalInfo.equals(personalInfo2));
        assertEquals(true, personalInfo2.equals(personalInfo3));
    }
}
