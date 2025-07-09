package com.example.helpdesk.service;

import com.example.helpdesk.contacts.EmailMessage;

import java.util.ArrayList;
import java.util.List;

public class EmailProviderStub implements EmailProvider{

    List<EmailMessage> allMessages = new ArrayList<EmailMessage>();

    public List<EmailMessage> getAllEmails() {
        return allMessages;
    }

    public void sendEmail(EmailMessage message) {
        allMessages.add(message);
    }

}
