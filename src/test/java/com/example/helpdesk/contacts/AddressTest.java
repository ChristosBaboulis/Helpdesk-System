package com.example.helpdesk.contacts;

import com.example.helpdesk.domain.Specialty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AddressTest {

    Address address;
    Address address2;

    @BeforeEach
    public void setUp() {
        address = new Address();
        address2 = new Address(address);
    }

    @Test
    public void checkGettersAndSetters() {
        //GETTER - SETTER OF Address' street
        address.setStreet("Test street");
        assertEquals("Test street", address.getStreet());

        //GETTER - SETTER OF Address' number
        address.setNumber("123");
        assertEquals("123", address.getNumber());

        //GETTER - SETTER OF Address' city
        address.setCity("Test city");
        assertEquals("Test city", address.getCity());

        //GETTER - SETTER OF Address' zip code
        address.setZipCode("1234");
        assertEquals("1234", address.getZipCode());
    }

    //TEST EQUALS OVERRIDE
    @Test
    public void checkEquality() {
        assertTrue(address.equals(address));
        assertFalse(address.equals(new Specialty()));
        assertFalse(address.equals(null));

        Address equalAddressBranching = new Address(address);
        assertTrue(address.equals(equalAddressBranching));
        equalAddressBranching.setStreet("Test street2");
        assertFalse(address.equals(equalAddressBranching));
        equalAddressBranching.setStreet(address.getStreet());
        equalAddressBranching.setNumber("321");
        assertFalse(address.equals(equalAddressBranching));
        equalAddressBranching.setNumber(address.getNumber());
        equalAddressBranching.setCity("Test city2");
        assertFalse(address.equals(equalAddressBranching));
        equalAddressBranching.setCity(address.getCity());
        equalAddressBranching.setZipCode("12345");
        assertFalse(address.equals(equalAddressBranching));

        assertDoesNotThrow(() -> address.hashCode());
    }

}
