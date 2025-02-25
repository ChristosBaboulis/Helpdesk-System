package com.example.helpdesk.domain;

import com.example.helpdesk.contacts.Address;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@DiscriminatorValue("CUSTOMERSUPPORT")
public class CustomerSupport extends User{

    @Column(name = "employee_code", length = 20)
    private String emplCode;

    public CustomerSupport() {
        super();
    }

    //Constructor with address as argument
    public CustomerSupport(String username, String password,
                           String firstName, String lastName,
                           String telephoneNumber, String emailAddress,
                           LocalDate birthdate, Address address, String emplCode) {
        super(username, password, firstName, lastName, telephoneNumber, emailAddress, birthdate, address);
        this.emplCode = emplCode;
    }

    //Constructor with addresses fields as parameters
    public CustomerSupport(String username, String password,
                           String firstName, String lastName,
                           String telephoneNumber, String emailAddress,
                           LocalDate birthdate, String street,
                           String number, String city,
                           String zipCode, String emplCode) {
        super(username, password, firstName, lastName, telephoneNumber, emailAddress, birthdate, street, number, city, zipCode);
        this.emplCode = emplCode;
    }

    public String getEmplCode() {
        return emplCode;
    }

    public void setEmplCode(String emplCode) {
        this.emplCode = emplCode;
    }
}
