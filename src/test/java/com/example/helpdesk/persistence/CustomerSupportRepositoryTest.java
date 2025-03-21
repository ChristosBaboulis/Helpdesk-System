package com.example.helpdesk.persistence;


import com.example.helpdesk.IntegrationBase;
import com.example.helpdesk.domain.CustomerSupport;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class CustomerSupportRepositoryTest extends IntegrationBase {
    @Inject
    CustomerSupportRepository customerSupportRepository;

    @Test
    public void testSearch(){

        CustomerSupport customerSupport = customerSupportRepository.findById(3001);
        Assertions.assertEquals(3001, customerSupport.getId());
    }

    @Test
    public void testFindByEmplCode(){

        CustomerSupport customerSupport = customerSupportRepository.findByEmplCode("EMP001").getFirst();
        Assertions.assertEquals("EMP001", customerSupport.getEmplCode());
    }
}
