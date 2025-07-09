package com.example.helpdesk.representation;

import com.example.helpdesk.contacts.Address;
import com.example.helpdesk.domain.Customer;
import com.example.helpdesk.persistence.CustomerRepository;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

@QuarkusTest
public class CustomerMapperTest {

    @Inject
    CustomerRepository customerRepository;
    @Inject
    CustomerMapper customerMapper;

    @Test
    @Transactional
    public void test() {
        CustomerRepresentation customerRepresentation = new CustomerRepresentation();
        customerRepresentation.customerCode = "123";
        customerRepresentation.firstName = "John";
        customerRepresentation.lastName = "Doe";
        customerRepresentation.telephoneNumber = "123456789";
        customerRepresentation.emailAddress = "john@doe.com";
        customerRepresentation.birthdate = LocalDate.now();
        customerRepresentation.address = new Address();

        Customer customer = customerMapper.toModel(customerRepresentation);

        Assertions.assertEquals("123", customer.getCustomerCode());
        Assertions.assertEquals("John", customer.getPersonalInfo().getFirstName());
        Assertions.assertEquals("Doe", customer.getPersonalInfo().getLastName());
        Assertions.assertEquals("john@doe.com", customer.getPersonalInfo().getEmailAddress());
        Assertions.assertEquals("123456789", customer.getPersonalInfo().getTelephoneNumber());
        Assertions.assertEquals(LocalDate.now(), customer.getPersonalInfo().getBirthdate());
        Assertions.assertEquals(new Address(), customer.getPersonalInfo().getAddress());

        customerRepository.persist(customer);

        CustomerRepresentation representation2 = customerMapper.toRepresentation(customer);
        Assertions.assertNotNull(representation2);
        Assertions.assertEquals("123", representation2.customerCode);
        Assertions.assertEquals("John", representation2.firstName);
        Assertions.assertEquals("Doe", representation2.lastName);
        Assertions.assertEquals("john@doe.com", representation2.emailAddress);
        Assertions.assertEquals("123456789", representation2.telephoneNumber);
        Assertions.assertEquals(LocalDate.now(), representation2.birthdate);
        Assertions.assertEquals(new Address(), representation2.address);
        Assertions.assertNotNull(representation2.id);
    }

}
