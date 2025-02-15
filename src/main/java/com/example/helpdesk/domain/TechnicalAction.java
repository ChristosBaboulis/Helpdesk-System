package com.example.helpdesk.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("TECHNICAL")
public class TechnicalAction extends Action {

    public TechnicalAction() { super(); }

    public TechnicalAction(String title, String description, String submissionDate) {
        super(title, description, submissionDate);
    }
}
