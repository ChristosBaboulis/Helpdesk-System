package com.example.helpdesk.persistence;

import java.util.List;

import com.example.helpdesk.domain.*;
import jakarta.enterprise.context.RequestScoped;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;

@RequestScoped
public class RequestRepository implements PanacheRepositoryBase<Request, Integer>{
    //---------------------------------------- BY ATTRIBUTES ----------------------------------------
    public Request search(Integer id) {
        return find("id", id).firstResult();
    }

    public List<Request> findByTelephoneNumber(String telephoneNumber) {
        return find("telephoneNumber", telephoneNumber).list();
    }

    public List<Request> findByStatus(Status status) {
        return find("status", status).list();
    }

    public List<Request> findBySubmissionDate(String submissionDate) {
        return find("submissionDate", submissionDate).list();
    }

    public List<Request> findByClosingDate(String closingDate) {
        return find("closeDate", closingDate).list();
    }
    //-----------------------------------------------------------------------------------------------

    //---------------------------------------- BY DEPENDENCIES ----------------------------------------
    public List<Request> findByTechnician(Technician technician) {
        return find("technician", technician).list();
    }

    public List<Request> findByCustomer(Customer customer) {
        return find("customer", customer).list();
    }

    public List<Request> findByCustomerSupport(CustomerSupport customerSupport) {
        return find("customerSupport", customerSupport).list();
    }

    public List<Request> findByRequestCategory(RequestCategory requestCategory) {
        return find("requestCategory", requestCategory).list();
    }
    //-----------------------------------------------------------------------------------------------

    //------------------------------------ ACTIVE & CLOSED STATUS ------------------------------------
    public List<Request> activeRequests() {
        return (List<Request>) find("select r from Request r where r.status = :status",
                Parameters.with("status", "ACTIVE")).list();
    }

    public List<Request> closedRequests() {
        return (List<Request>) find("select r from Request r where r.status = :status",
                Parameters.with("status", "CLOSED")).list();
    }
    //-----------------------------------------------------------------------------------------------
}
