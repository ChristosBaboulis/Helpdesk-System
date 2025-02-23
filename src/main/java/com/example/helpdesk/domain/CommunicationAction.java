package com.example.helpdesk.domain;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
@DiscriminatorValue("COMMUNICATION")
public class CommunicationAction extends Action{

    @Column(name = "call_duration")
    private double callDuration;

    public CommunicationAction() { super(); }

    //Full Args constructor - for testing purposes
    public CommunicationAction(String title, String description, LocalDate submissionDate, double callDuration) {
        super(title, description, submissionDate);
        this.callDuration = callDuration;
    }

    //Constructor to be used
    public CommunicationAction(String title, String description, double callDuration) {
        super(title, description);
        this.callDuration = callDuration;
    }

    public double getCallDuration() {
        return callDuration;
    }

    public void setCallDuration(double callDuration) {
        this.callDuration = callDuration;
    }
}
