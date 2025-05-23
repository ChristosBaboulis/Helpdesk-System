package com.example.helpdesk.service;

import com.example.helpdesk.contacts.EmailMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Η κλάση είναι ένα στέλεχος που χρησιμοποιείται αντί
 * μίας πραγματικής υπηρεσίας ηλεκτρονικού ταχυδρομείου.
 * Υπάρχει ένας κατάλογος μηνυμάτων. Σε κάθε κλήση
 * της μεθόδου {@code sendEmail} προστίθεται το μήνυμα
 * στον κατάλογο. Ο κατάλογος επιστρέφεται με τη
 * μέθοδο {@code getAllEmails} για την εκτέλεση ανάλογων
 * ισχυρισμών κατά τον έλεγχο.
 *
 * @author Νίκος Διαμαντίδης
 */
public class EmailProviderStub implements EmailProvider{

    List<EmailMessage> allMessages = new ArrayList<EmailMessage>();


    public List<EmailMessage> getAllEmails() {
        return allMessages;
    }

    public void sendEmail(EmailMessage message) {
        allMessages.add(message);
    }
}