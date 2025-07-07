package com.example.helpdesk.domain;

import com.example.helpdesk.contacts.Address;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends User {
    public Admin() {
        super();
    }

    public Admin(String username, String password,
                 String firstName, String lastName,
                 String telephoneNumber, String emailAddress,
                 LocalDate birthdate, Address address) {
        super(username, password, firstName, lastName, telephoneNumber, emailAddress, birthdate, address);
    }
}
