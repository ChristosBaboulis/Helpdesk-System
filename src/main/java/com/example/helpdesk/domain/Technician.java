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

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch= FetchType.LAZY)
    @JoinTable(name="technician_specialties",
            joinColumns = {@JoinColumn(name="technician_id")},
            inverseJoinColumns = {@JoinColumn(name="specialty_id")}
    )
    private Set<Specialty> specialties = new HashSet<>();

    public Technician() {
        super();
    }

    //Constructor with address as argument
    public Technician(String username, String password,
                      String firstName, String lastName,
                      String telephoneNumber, String emailAddress,
                      LocalDate birthdate, Address address, String technicianCode) {
        super(username, password, firstName, lastName, telephoneNumber, emailAddress, birthdate, address);
        this.technicianCode = technicianCode;
    }

    //Constructor with addresses fields as parameters
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

    //Method used to assign a specialty to a technician. Needs to check first if specialty is
    //already assigned
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
