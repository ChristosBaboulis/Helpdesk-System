package com.example.helpdesk.domain;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("TECHNICIAN")
public class Technician extends User {

    @Column(name = "technician_code", length = 20)
    private String technicianCode;

    @Column(name = "active_requests")
    private int activeRequests;  //TO BE CONNECTED TO REQUEST OBJECT

    public Technician() {
        super();
    }

    public Technician(String username, String password,
                      String firstName, String lastName,
                      String telephoneNumber, String emailAddress,
                      String birthdate, String street,
                      String number, String city,
                      String zipCode, String technicianCode,
                      int activeRequests) {
        super(username, password, firstName, lastName, telephoneNumber, emailAddress, birthdate, street, number, city, zipCode);
        this.technicianCode = technicianCode;
        this.activeRequests = activeRequests;
    }

    public String getTechnicianCode() {
        return technicianCode;
    }

    public void setTechnicianCode(String technicianCode) {
        this.technicianCode = technicianCode;
    }

    public int getActiveRequests() {
        return activeRequests;
    }

    public void setActiveRequests(int activeRequests) {
        this.activeRequests = activeRequests;
    }
}
