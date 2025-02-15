package com.example.helpdesk.domain;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("COMMUNICATION")
public class CommunicationAction extends Action{

    @Column(name = "call_duration")
    private double callDuration;

    public CommunicationAction() { super(); }

    public CommunicationAction(String title, String description, String submissionDate, double callDuration) {
        super(title, description, submissionDate);
        this.callDuration = callDuration;
    }

    public double getCallDuration() {
        return callDuration;
    }

    public void setCallDuration(double callDuration) {
        this.callDuration = callDuration;
    }
}
