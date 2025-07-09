package com.example.helpdesk.domain;

import com.example.helpdesk.util.SystemDate;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
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

    @Column(name = "close_date",length = 10)
    private LocalDate closeDate;

    @Enumerated(EnumType.STRING) //
    @Column(name = "status", nullable = false)
    private Status status;


    @ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private RequestCategory requestCategory;

    @ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_support_id", nullable = false)
    private CustomerSupport customerSupport;

    @ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "techician_id", nullable = true)
    private Technician technician;


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "request_id")
    private Set<Action> actions = new HashSet<>();

    public Request() { }

    //CONSTRUCTOR TO BE USED
    public Request(String telephoneNumber, String problemDescription,
                   RequestCategory requestCategory,
                   Customer customer, CustomerSupport customerSupport){
        //APPROPRIATE FIELDS TO BE POPULATED DURING INITIALIZATION OF A Request
        if( telephoneNumber == null || telephoneNumber.isEmpty() || problemDescription == null || problemDescription.isEmpty()
                || requestCategory == null || customer == null || customerSupport == null){
            throw new DomainException("Insufficient parameters for request creation.");
        }
        //CHECK OF telephoneNumber SIZE
        if(telephoneNumber.length() != 10){
            throw new DomainException("Telephone number must be 10 digits.");
        }
        //POPULATION OF FIELDS
        this.telephoneNumber = telephoneNumber;
        this.problemDescription = problemDescription;
        this.requestCategory = requestCategory;
        this.customer = customer;
        this.customerSupport = customerSupport;
        //change Status OF CREATED Request TO ACTIVE
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

    public LocalDate getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(LocalDate closeDate) {
        this.closeDate = closeDate;
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

    //METHOD USED TO ASSIGN AN Action TO THE Request, IF IT IS NOT ALREADY ASSIGNED
    public void addAction(Action action) {
        if (action == null) return;
        if(actions.contains(action)){
            throw new DomainException("Action already assigned.");
        }
        actions.add(action);
    }

    public void removeAction(Action action) {
        actions.remove(action);
    }

    //METHOD USED TO CHANGE Request's Status TO ACTIVE, AFTER CHECKING IT IS NOT CLOSED
    public void accept(){
        //RESTRICTION OF CLOSED Request
        if(status == Status.CLOSED){
            throw new DomainException("Request's status cannot be changed, it is already closed.");
        }
        status = Status.ACTIVE;
    }

    //METHOD USED TO CHANGE Request's Status TO REJECTED, AFTER CHECKING IT IS NOT CLOSED
    public void reject(){
        //RESTRICTION OF CLOSED Request
        if(status == Status.CLOSED){
            throw new DomainException("Request's status cannot be changed, it is already closed.");
        }
        status = Status.REJECTED;
    }

    //METHOD USED TO ASSIGN Request TO A Technician AND CHANGE THE Status ACCORDINGLY, IF IT IS NOT CLOSED
    public void assign(Technician technician){
        //RESTRICTION OF CLOSED Request
        if(status == Status.CLOSED){
            throw new DomainException("Request cannot be assigned, it is already closed.");
        }
        if (this.requestCategory == null) {
            throw new DomainException("Problem with request, no request category assigned.");
        }
        if (this.requestCategory.getSpecialty() == null) {
            throw new DomainException("Problem with request, request category assigned does not have a specialty.");
        }

        boolean match = false;

        for (Specialty specialty : technician.getSpecialties()){
            if( specialty.equals(this.requestCategory.getSpecialty()) ){
                match = true;
                break;
            }
        }

        if(match){
            this.technician = technician;
            status = Status.RESOLVING;
        }else{
            throw new DomainException("Technician does not have the correct specialty for this request category.");
        }
    }

    //METHOD USED TO CHANGE Request's Status TO CLOSED
    public void close(){
        //RESTRICTION OF A Request CANNOT BE CLOSED WITHOUT ANY Actions OR ASSIGNMENT OF A Technician
        if(!getActions().isEmpty() && getTechnician() != null){
            status = Status.CLOSED;
            closeDate = SystemDate.now();
        }else{
            throw new DomainException("Cannot close request without doing any actions or technician is not assigned.");
        }
    }

    //METHOD THAT RETURNS TRUE OR FALSE IN CASE WE NEED TO NOTIFY Customer ABOUT CLOSURE OF THE Request
    public boolean notifyCustomer(){
        return status == Status.CLOSED;
    }

}
