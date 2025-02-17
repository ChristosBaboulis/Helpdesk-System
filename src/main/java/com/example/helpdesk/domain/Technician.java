package com.example.helpdesk.domain;

import com.example.helpdesk.contacts.Address;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("TECHNICIAN")
public class Technician extends User {

    @Column(name = "technician_code", length = 20)
    private String technicianCode;

    @OneToMany(mappedBy = "technician",cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private Set<Request> requests = new HashSet<>();

    @Column(name = "active_requests")
    private int activeRequests;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch= FetchType.LAZY)
    @JoinTable(name="technician_specialties",
            joinColumns = {@JoinColumn(name="technician_id")},
            inverseJoinColumns = {@JoinColumn(name="specialty_id")}
    )
    private Set<Specialty> specialties = new HashSet<>();

    public Technician() {
        super();
    }

    public Technician(String username, String password,
                      String firstName, String lastName,
                      String telephoneNumber, String emailAddress,
                      LocalDate birthdate, Address address, String technicianCode) {
        super(username, password, firstName, lastName, telephoneNumber, emailAddress, birthdate, address);
        this.technicianCode = technicianCode;
    }

    public Technician(String username, String password,
                      String firstName, String lastName,
                      String telephoneNumber, String emailAddress,
                      LocalDate birthdate, String street,
                      String number, String city,
                      String zipCode, String technicianCode) {
        super(username, password, firstName, lastName, telephoneNumber, emailAddress, birthdate, street, number, city, zipCode);
        this.technicianCode = technicianCode;
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

    public void setRequest(Request request) {
        if (request == null) return;
        if(requests.contains(request)){
            throw new DomainException("Specialty already assigned.");
        }
        requests.add(request);
        activeRequests++;
    }

    public void removeRequest(Request request) {
        specialties.remove(request);
        activeRequests--;
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
