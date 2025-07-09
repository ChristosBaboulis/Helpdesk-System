package com.example.helpdesk.service;

import java.util.List;
import java.util.Objects;

import com.example.helpdesk.HelpdeskException;
import com.example.helpdesk.contacts.EmailAddress;
import com.example.helpdesk.contacts.EmailMessage;
import com.example.helpdesk.domain.Customer;
import com.example.helpdesk.domain.Request;
import com.example.helpdesk.persistence.RequestRepository;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@RequestScoped
public class NotificationService {

    @Inject
    RequestRepository requestRepository;

    private EmailProvider provider;

    public void setProvider(EmailProvider provider) {
        this.provider = provider;
    }

    @Transactional
    public void notifyCustomer(Integer requestId) {
        if (provider == null) {
            throw new HelpdeskException();
        }

        List<Request> allRequests = requestRepository.closedRequests();

        for (Request request : allRequests) {
            if (Objects.equals(request.getId(), requestId)) {
                sendEmail(request.getCustomer(),
                        "Request no: "+request.getId()+" is closed!");
            }
        }
    }

    private void sendEmail(Customer customer,
                           String message) {
        EmailAddress eMail  = new EmailAddress(customer.getPersonalInfo().getEmailAddress());
        if (!eMail.isValid()) {
            return;
        }

        EmailMessage emailMessage = new EmailMessage();
        emailMessage.setTo(eMail);
        emailMessage.setSubject("Request Status Update");
        emailMessage.setBody(message);
        provider.sendEmail(emailMessage);
    }

}
