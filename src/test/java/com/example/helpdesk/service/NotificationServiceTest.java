package com.example.helpdesk.service;

import com.example.helpdesk.IntegrationBase;
import com.example.helpdesk.contacts.EmailMessage;
import com.example.helpdesk.domain.Customer;
import com.example.helpdesk.persistence.CustomerRepository;
import com.example.helpdesk.util.SystemDateStub;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.vertx.http.runtime.devmode.ResourceNotFoundData;
import jakarta.inject.Inject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class NotificationServiceTest extends IntegrationBase {

    @Inject
    NotificationService notificationService;
    @Inject
    CustomerRepository customerRepository;

    private EmailProviderStub provider;

    @BeforeEach
    protected void beforeDatabasePreparation() {
        provider = new EmailProviderStub();
    }

    @AfterEach
    protected void afterTearDown() {
        SystemDateStub.reset();
    }

    @Test
    @TestTransaction
    public void testSendEmail() {
        notificationService.setProvider(provider);
        notificationService.notifyCustomers();

        Customer customer = customerRepository.findById(5001);
        Assertions.assertEquals(1, provider.allMessages.size());
        EmailMessage message = provider.getAllEmails().getFirst();
        Assertions.assertEquals(customer.getPersonalInfo().getEmailAddress(), message.getTo().toString());
    }
}
