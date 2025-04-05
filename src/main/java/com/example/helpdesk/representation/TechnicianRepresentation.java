package com.example.helpdesk.representation;

import com.example.helpdesk.contacts.Address;
import com.example.helpdesk.domain.Specialty;
import io.quarkus.runtime.annotations.RegisterForReflection;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RegisterForReflection
public class TechnicianRepresentation {
    public Integer id;
    public String technicianCode;
    public String firstName;
    public String lastName;
    public String username;
    public String password;
    public String telephoneNumber;
    public String emailAddress;
    public LocalDate birthdate;
    public Address address;
    public List<SpecialtyRepresentation> specialties = new ArrayList<SpecialtyRepresentation>();
}
