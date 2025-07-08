package com.example.helpdesk.representation;

import io.quarkus.runtime.annotations.RegisterForReflection;

import java.time.LocalDate;

@RegisterForReflection
public class ActionRepresentation {

    public Integer id;
    public String title;
    public String description;
    public LocalDate submissionDate;
    public double callDuration;

}
