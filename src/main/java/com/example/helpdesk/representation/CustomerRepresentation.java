package com.example.helpdesk.representation;

import com.example.helpdesk.contacts.Address;
import io.quarkus.runtime.annotations.RegisterForReflection;

import java.time.LocalDate;

@RegisterForReflection
public class CustomerRepresentation {

    public Integer id;
    public String customerCode;
    public String firstName;
    public String lastName;
    public String telephoneNumber;
    public String emailAddress;
    public LocalDate birthdate;
    public Address address;

    //NO-ARGS CONSTRUCTOR FOR JSON DESERIALIZATION
    public CustomerRepresentation() {}

}
