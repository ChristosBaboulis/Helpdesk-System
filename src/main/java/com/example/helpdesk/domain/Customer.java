package com.example.helpdesk.domain;

import com.example.helpdesk.contacts.Address;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "Customers")
public class Customer {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "customer_code", length = 20, nullable = false)
    private String customerCode;

    @Embedded
    private PersonalInfo personalInfo;

    public Customer() {}

    //Constructor with address as argument
    public Customer(String customerCode, String firstName, String lastName,
                    String telephoneNumber, String emailAddress,
                    LocalDate birthdate, Address address) {
        this.customerCode = customerCode;
        this.personalInfo = new PersonalInfo(firstName, lastName, telephoneNumber, emailAddress, birthdate, address);
    }

    //Constructor with addresses fields as parameters
    public Customer(String customerCode, String firstName, String lastName,
                    String telephoneNumber, String emailAddress,
                    LocalDate birthdate, String street,
                    String number, String city,
                    String zipCode) {
        this.customerCode = customerCode;
        this.personalInfo = new PersonalInfo(firstName, lastName, telephoneNumber, emailAddress, birthdate, street, number, city, zipCode);
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public PersonalInfo getPersonalInfo() {
        return personalInfo;
    }

    public void setPersonalInfo(PersonalInfo personalInfo) {
        this.personalInfo = personalInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(customerCode, customer.customerCode);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(customerCode);
    }

    public int getId() {
        return id;
    }

}
