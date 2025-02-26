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
    private int activeRequests = 0;

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

    public int getActiveRequests() {
        return activeRequests;
    }

    public void setActiveRequests(int activeRequests) {
        this.activeRequests = activeRequests;
    }

    public Set<Specialty> getSpecialties() {
        return specialties;
    }

    public Set<Request> getRequests() {
        return requests;
    }

    //Method to set Request to technician after checking eligibility through request's
    //category, specialty matched to category and technician's specialty
    //In the end we need to update the number of activeRequests assigned to technician
    public void setRequest(Request request) {
        if (request == null) return;

        if (request.getRequestCategory() == null){
            throw new DomainException("Problem with request, no request category assigned.");
        }
        if (request.getRequestCategory().getSpecialty() == null){
            throw new DomainException("Problem with request, request category assigned does not have a specialty.");
        }

        if(requests.contains(request)){
            throw new DomainException("Request already assigned.");
        }

        if( !specialties.contains( request.getRequestCategory().getSpecialty() ) ){
            throw new DomainException("Technician has no specialty for this request.");
        }

        requests.add(request);
        activeRequests++;
    }

    //Method used to remove a request from a technician when it is closed, and reduce the number of
    //technician's active requests by 1
    public void removeRequest(Request request) {
        requests.remove(request);
        activeRequests--;
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
