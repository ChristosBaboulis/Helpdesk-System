package com.example.helpdesk.domain;

import com.example.helpdesk.util.SystemDate;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "Requests")
public class Request {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Integer id;

    @Column(name = "telephone_number", length = 10, nullable = false)
    private String telephoneNumber;

    @Column(name = "problem_description", length = 1000)
    private String problemDescription;

    @Column(name = "submission_date",length = 10, nullable = false)
    private LocalDate submissionDate = SystemDate.now();

    @Enumerated(EnumType.STRING) //
    @Column(name = "status", nullable = false)
    private Status status;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private RequestCategory requestCategory;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_support_id", nullable = false)
    private CustomerSupport customerSupport;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "techician_id", nullable = false)
    private Technician technician;


    @OneToMany(mappedBy = "request", cascade = {CascadeType.PERSIST, CascadeType.MERGE},fetch = FetchType.LAZY)
    private Set<Action> actions = new HashSet<>();

    public Request() { }

    //Constructor to be used
    public Request(String telephoneNumber, String problemDescription, RequestCategory requestCategory,
                   Customer customer, CustomerSupport customerSupport){
        if( telephoneNumber == null || telephoneNumber.isEmpty() || problemDescription == null || problemDescription.isEmpty()
                || requestCategory == null || customer == null || customerSupport == null){
            throw new DomainException("Insufficient parameters for request creation.");
        }
        if(telephoneNumber.length() != 10){
            throw new DomainException("Telephone number must be 10 digits.");
        }
        this.telephoneNumber = telephoneNumber;
        this.problemDescription = problemDescription;
        this.requestCategory = requestCategory;
        this.customer = customer;
        this.customerSupport = customerSupport;
        accept();
    }

    public Integer getId() {
        return id;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getProblemDescription() {
        return problemDescription;
    }

    public void setProblemDescription(String problemDescription) {
        this.problemDescription = problemDescription;
    }

    public LocalDate getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(LocalDate submissionDate) {
        this.submissionDate = submissionDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public RequestCategory getRequestCategory() {
        return requestCategory;
    }

    public void setRequestCategory(RequestCategory requestCategory) {
        this.requestCategory = requestCategory;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public CustomerSupport getCustomerSupport() {
        return customerSupport;
    }

    public void setCustomerSupport(CustomerSupport customerSupport) {
        this.customerSupport = customerSupport;
    }

    public Technician getTechnician() {
        return technician;
    }

    public void setTechnician(Technician technician) {
        this.technician = technician;
    }

    public Set<Action> getActions() {
        return actions;
    }

    public void addAction(Action action) {
        if (actions == null) return;
        if(actions.contains(action)){
            throw new DomainException("Action already assigned.");
        }
        actions.add(action);
    }

    public void removeAction(Action action) {
        actions.remove(action);
    }

    public void accept(){
        if(status == Status.CLOSED){
            throw new DomainException("Request's status cannot be changed, it is already closed.");
        }
        status = Status.ACTIVE;
    }

    public void reject(){
        if(status == Status.CLOSED){
            throw new DomainException("Request's status cannot be changed, it is already closed.");
        }
        status = Status.REJECTED;
    }

    public void assign(Technician technician){
        if(status == Status.CLOSED){
            throw new DomainException("Request cannot be assigned, it is already closed.");
        }

        boolean match = false;

        if (this.requestCategory == null) {
            throw new DomainException("Problem with request, no request category assigned.");
        }
        if (this.requestCategory.getSpecialty() == null) {
            throw new DomainException("Problem with request, request category assigned does not have a specialty.");
        }

        for (Specialty specialty : technician.getSpecialties()){
            if( specialty.equals( this.requestCategory.getSpecialty() ) ){
                match = true;
                break;
            }
        }

        if(match){
            this.technician = technician;
            status = Status.ASSIGNED_TO_BE_SOLVED;
        }else{
            throw new DomainException("Technician does not have the correct specialty for this request category.");
        }
    }

    public void resolve(){
        if(status == Status.CLOSED){
            throw new DomainException("Request's status cannot be changed, it is already closed.");
        }
        status = Status.RESOLVING;
    }

    public void close(){
        if(!getActions().isEmpty()){
            status = Status.CLOSED;
        }else{
            throw new DomainException("Cannot close request without doing any actions.");
        }
    }

    public boolean notifyCustomer(){
        return status == Status.CLOSED;
    }
}
