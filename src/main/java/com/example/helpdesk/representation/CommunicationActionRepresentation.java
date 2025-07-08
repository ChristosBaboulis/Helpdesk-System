package com.example.helpdesk.representation;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class CommunicationActionRepresentation extends ActionRepresentation {

    public double callDuration;

}
