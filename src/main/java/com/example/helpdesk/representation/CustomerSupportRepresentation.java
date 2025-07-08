package com.example.helpdesk.representation;

import com.example.helpdesk.contacts.Address;
import io.quarkus.runtime.annotations.RegisterForReflection;

import java.time.LocalDate;

@RegisterForReflection
public class CustomerSupportRepresentation {

    public String emplCode;
    public Integer id;
    public String firstName;
    public String lastName;
    public String username;
    public String password;
    public String telephoneNumber;
    public String emailAddress;
    public LocalDate birthdate;
    public Address address;

}
