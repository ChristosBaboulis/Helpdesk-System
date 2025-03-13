package com.example.helpdesk.representation;

import com.example.helpdesk.domain.Status;
import io.quarkus.runtime.annotations.RegisterForReflection;

import java.time.LocalDate;

@RegisterForReflection
public class RequestRepresentation {
    public Integer id;
    public String telephoneNumber;
    public String problemDescription;
    public LocalDate submissionDate;
    public LocalDate closeDate;
    public Status status;
//    public RequestCategory category;
//    public Customer customer;
//    public Technician technician;
//    public List<Actions> actions;
}
