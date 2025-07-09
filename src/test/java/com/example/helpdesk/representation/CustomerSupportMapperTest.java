package com.example.helpdesk.representation;

import com.example.helpdesk.contacts.Address;
import com.example.helpdesk.domain.CustomerSupport;
import com.example.helpdesk.persistence.CustomerSupportRepository;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

@QuarkusTest
public class CustomerSupportMapperTest {

    @Inject
    CustomerSupportMapper customerSupportMapper;
    @Inject
    CustomerSupportRepository customerSupportRepository;

    @Test
    @Transactional
    public void testCustomerSupportMapper() {
        CustomerSupportRepresentation customerSupportRepresentation = new CustomerSupportRepresentation();
        customerSupportRepresentation.emplCode = "123";
        customerSupportRepresentation.firstName = "John";
        customerSupportRepresentation.lastName = "Doe";
        customerSupportRepresentation.username = "johnDoe";
        customerSupportRepresentation.password = "password";
        customerSupportRepresentation.telephoneNumber = "123456789";
        customerSupportRepresentation.emailAddress = "john@doe.com";
        customerSupportRepresentation.birthdate = LocalDate.now();
        customerSupportRepresentation.address = new Address();

        CustomerSupport customerSupport = customerSupportMapper.toModel(customerSupportRepresentation);

        Assertions.assertEquals("123", customerSupport.getEmplCode());
        Assertions.assertEquals("John", customerSupport.getPersonalInfo().getFirstName());
        Assertions.assertEquals("Doe", customerSupport.getPersonalInfo().getLastName());
        Assertions.assertEquals("john@doe.com", customerSupport.getPersonalInfo().getEmailAddress());
        Assertions.assertEquals("123456789", customerSupport.getPersonalInfo().getTelephoneNumber());
        Assertions.assertEquals("johnDoe", customerSupport.getUsername());
        Assertions.assertEquals("password", customerSupport.getPassword());
        Assertions.assertEquals(LocalDate.now(), customerSupport.getPersonalInfo().getBirthdate());
        Assertions.assertEquals(new Address(), customerSupport.getPersonalInfo().getAddress());

        customerSupportRepository.persist(customerSupport);

        CustomerSupportRepresentation representation2 = customerSupportMapper.toRepresentation(customerSupport);
        Assertions.assertNotNull(representation2);
        Assertions.assertEquals("123", representation2.emplCode);
        Assertions.assertEquals("John", representation2.firstName);
        Assertions.assertEquals("Doe", representation2.lastName);
        Assertions.assertEquals("john@doe.com", representation2.emailAddress);
        Assertions.assertEquals("123456789", representation2.telephoneNumber);
        Assertions.assertEquals("johnDoe", representation2.username);
        Assertions.assertEquals("password", representation2.password);
        Assertions.assertEquals(LocalDate.now(), representation2.birthdate);
        Assertions.assertEquals(new Address(), representation2.address);
        Assertions.assertNotNull(representation2.id);
    }

}
