package com.example.helpdesk.persistence;

import com.example.helpdesk.IntegrationBase;
import com.example.helpdesk.domain.Customer;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;



@QuarkusTest
public class CustomerRepositoryTest extends IntegrationBase {

    @Inject
    CustomerRepository customerRepository;

    @Test
    public void testSearch(){

        Customer customer = customerRepository.findById(5001);
        Assertions.assertEquals(5001, customer.getId());
    }

    @Test
    public void testFindByCustomerCode(){

        Customer customer1 = customerRepository.findByCustomerCode("CUST005").getFirst();
        Assertions.assertEquals("CUST005", customer1.getCustomerCode());
    }

    @Test
    public void testFindByTelephoneNumber(){

        Customer customer2 = customerRepository.findByTelephoneNumber("7123456789").getFirst();
        Assertions.assertEquals("7123456789", customer2.getPersonalInfo().getTelephoneNumber());
    }

    @Test
    public void testFindByEmail(){

        Customer customer3 = customerRepository.findByEmailAddress("liam.miller@example.com").getFirst();
        Assertions.assertEquals("liam.miller@example.com", customer3.getPersonalInfo().getEmailAddress());
    }

}
