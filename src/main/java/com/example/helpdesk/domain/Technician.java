package com.example.helpdesk.domain;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("TECHNICIAN")
public class Technician extends User {

    @Column(name = "technician_code", length = 20)
    private String technicianCode;

    @Column(name = "active_requests")
    private int activeRequests;

    //TODO
    //REQUEST OBJECT
    //SPECIALTY OBJECT
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch= FetchType.LAZY)
    @JoinTable(name="technician_specialties",
            joinColumns = {@JoinColumn(name="technician_id")},
            inverseJoinColumns = {@JoinColumn(name="specialty_id")}
    )
    private Set<Specialty> specialties = new HashSet<Specialty>();

    public Technician() {
        super();
    }

    public Technician(String username, String password,
                      String firstName, String lastName,
                      String telephoneNumber, String emailAddress,
                      String birthdate, String street,
                      String number, String city,
                      String zipCode, String technicianCode,
                      int activeRequests, Set<Specialty> specialties) {
        super(username, password, firstName, lastName, telephoneNumber, emailAddress, birthdate, street, number, city, zipCode);
        this.technicianCode = technicianCode;
        this.activeRequests = activeRequests;
        this.specialties = specialties;
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

    public Set<Specialty> getSpecialties() {
        return specialties;
    }

    public void setSpecialty(Specialty specialty) {
        if (specialty == null) return;
        if(specialties.contains(specialty)){
            throw new DomainException("Specialty already assigned.");
        }
        specialties.add(specialty);
    }

    public void removeSpecialty(Specialty specialty) {
        specialties.remove(specialty);
    }
}
