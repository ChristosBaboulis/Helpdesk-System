package com.example.helpdesk.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
@DiscriminatorValue("TECHNICAL")
public class TechnicalAction extends Action {

    public TechnicalAction() { super(); }

    //FULL ARGS CONSTRUCTOR - FOR TESTING PURPOSES
    public TechnicalAction(String title, String description, LocalDate submissionDate) {
        super(title, description, submissionDate);
    }

    //CONSTRUCTORS TO BE USED
    public TechnicalAction(String title, String description) {
        super(title, description);
    }

    public TechnicalAction(String title) {super(title);}

}
