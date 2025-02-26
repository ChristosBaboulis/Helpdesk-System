package com.example.helpdesk.contacts;

import com.example.helpdesk.domain.Specialty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;

public class AddressTest {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    LocalDate submDate = LocalDate.parse("03/01/2025", formatter);
    Address address;
    Address address2;

    @BeforeEach
    public void setUp() {
        address = new Address();
        address2 = new Address(address);
    }

    @Test
    public void checkGettersAndSetters() {
        //GETTER, SETTER OF ADDRESS STREET
        address.setStreet("Test street");
        assertEquals("Test street", address.getStreet());

        //GETTER, SETTER OF ADDRESS NUMBER
        address.setNumber("123");
        assertEquals("123", address.getNumber());

        //GETTER, SETTER OF ADDRESS CITY
        address.setCity("Test city");
        assertEquals("Test city", address.getCity());

        //GETTER, SETTER OF ADDRESS ZIP CODE
        address.setZipCode("1234");
        assertEquals("1234", address.getZipCode());
    }

    //TEST EQUALS OVERRIDE
    @Test
    public void checkEquality() {
        assertEquals(true, address.equals(address));
        assertEquals(false, address.equals(new Specialty()));
        assertEquals(false, address.equals(null));

        Address equalAddressBranching = new Address(address);
        assertEquals(true, address.equals(equalAddressBranching));
        equalAddressBranching.setStreet("Test street2");
        assertEquals(false, address.equals(equalAddressBranching));
        equalAddressBranching.setStreet(address.getStreet());
        equalAddressBranching.setNumber("321");
        assertEquals(false, address.equals(equalAddressBranching));
        equalAddressBranching.setNumber(address.getNumber());
        equalAddressBranching.setCity("Test city2");
        assertEquals(false, address.equals(equalAddressBranching));
        equalAddressBranching.setCity(address.getCity());
        equalAddressBranching.setZipCode("12345");
        assertEquals(false, address.equals(equalAddressBranching));

        address.hashCode();
    }
}
