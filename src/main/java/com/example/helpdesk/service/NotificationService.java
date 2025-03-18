package com.example.helpdesk.service;

import java.util.List;

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

    /**
     * Θέτει τον πάροχο του ηλεκτρονικού ταχυδρομείου.
     * @param provider Ο πάροχος ηλεκτρονικού ταχυδρομείου.
     */
    public void setProvider(EmailProvider provider) {
        this.provider = provider;
    }

    @Transactional
    public void notifyCustomers() {
        if (provider == null) {
            throw new HelpdeskException();
        }

        List<Request> allRequests = requestRepository.closedRequests();

        for (Request request : allRequests) {
            sendEmail(request.getCustomer(),
                    "Request Status Update", "Request no: "+request.getId()+" is closed!");
        }
    }

    private void sendEmail(Customer customer,
                           String subject, String message) {
        EmailAddress eMail  = new EmailAddress(customer.getPersonalInfo().getEmailAddress());
        if (eMail == null || !eMail.isValid()) {
            return;
        }

        EmailMessage emailMessage = new EmailMessage();
        emailMessage.setTo(eMail);
        emailMessage.setSubject(subject);
        emailMessage.setBody(message);
        provider.sendEmail(emailMessage);
    }
}
