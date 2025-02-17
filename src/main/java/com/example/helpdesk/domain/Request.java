package com.example.helpdesk.domain;

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
    private LocalDate submissionDate;

    @Column(name = "status", length = 10, nullable = false)
    private String status;  // enumarated tha valoume meta


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

    public Request(String telephoneNumber, String problemDescription,
                   LocalDate submissionDate, String status,
                   RequestCategory requestCategory, Customer customer,
                   CustomerSupport customerSupport, Technician technician,
                   Set<Action> actions) {
        this.telephoneNumber = telephoneNumber;
        this.problemDescription = problemDescription;
        this.submissionDate = submissionDate;
        this.status = status;
        this.requestCategory = requestCategory;
        this.customer = customer;
        this.customerSupport = customerSupport;
        this.technician = technician;
        this.actions = actions;
    }

    public Boolean canClose(){
        return !getActions().isEmpty();
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    public void setActions(Action action) {
        if (actions == null) return;
        if(actions.contains(action)){
            throw new DomainException("Action already assigned.");
        }
        actions.add(action);
    }

    public void removeActions(Action action) {
        actions.remove(action);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return Objects.equals(id, request.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
