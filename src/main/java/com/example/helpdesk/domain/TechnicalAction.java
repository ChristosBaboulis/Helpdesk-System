package com.example.helpdesk.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
@DiscriminatorValue("TECHNICAL")
public class TechnicalAction extends Action {

    public TechnicalAction() { super(); }

    //Full Args constructor - for testing purposes
    public TechnicalAction(String title, String description, LocalDate submissionDate) {
        super(title, description, submissionDate);
    }

    //Constructors to be used
    public TechnicalAction(String title, String description) {
        super(title, description);
    }

    public TechnicalAction(String title) {super(title);}
}
