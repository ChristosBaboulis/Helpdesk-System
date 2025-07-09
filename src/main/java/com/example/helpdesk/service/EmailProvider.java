package com.example.helpdesk.service;

import com.example.helpdesk.contacts.EmailMessage;

public interface EmailProvider {

    void sendEmail(EmailMessage message);

}
