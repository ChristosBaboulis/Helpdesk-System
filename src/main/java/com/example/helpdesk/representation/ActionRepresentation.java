package com.example.helpdesk.representation;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class ActionRepresentation {
    public Integer id;
    public String title;
    public String description;
    public double callDuration;
}