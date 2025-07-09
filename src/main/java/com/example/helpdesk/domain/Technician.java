package com.example.helpdesk.domain;

import com.example.helpdesk.contacts.Address;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("TECHNICIAN")
public class Technician extends User {

    @Column(name = "technician_code", length = 20, unique = true)
    private String technicianCode;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch= FetchType.LAZY)
    @JoinTable(name="technician_specialties",
            joinColumns = {@JoinColumn(name="technician_id")},
            inverseJoinColumns = {@JoinColumn(name="specialty_id")}
    )
    private Set<Specialty> specialties = new HashSet<>();

    public Technician() {
        super();
    }

    //CONSTRUCTOR WITH Address AS ARGUMENT
    public Technician(String username, String password,
                      String firstName, String lastName,
                      String telephoneNumber, String emailAddress,
                      LocalDate birthdate, Address address, String technicianCode) {
        super(username, password, firstName, lastName, telephoneNumber, emailAddress, birthdate, address);
        this.technicianCode = technicianCode;
    }

    //CONSTRUCTOR WITH Address' FIELDS AS PARAMETERS
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

    public Set<Specialty> getSpecialties() {
        return specialties;
    }

    //METHOD USED TO ASSIGN A Specialty TO A Technician - NEEDS TO CHECK FIRST IF Specialty IS ALREADY ASSIGNED
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
