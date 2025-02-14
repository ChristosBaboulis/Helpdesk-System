package com.example.helpdesk.domain;

import jakarta.persistence.*;

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

    //TODO
    //REQUEST OBJECT

    @Embedded
    private PersonalInfo personalInfo;

    public Customer() {}

    public Customer(String customerCode, String firstName, String lastName,
                    String telephoneNumber, String emailAddress,
                    String birthdate, String street,
                    String number, String city,
                    String zipCode) {
        this.customerCode = customerCode;
        this.personalInfo = new PersonalInfo(firstName, lastName, telephoneNumber, emailAddress, birthdate,street, number, city, zipCode);
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
        return id == customer.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
