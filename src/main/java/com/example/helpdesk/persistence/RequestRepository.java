package com.example.helpdesk.persistence;

import java.time.LocalDate;
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

    public List<Request> findByRequestCategoryId(Integer categoryId) {
        return find("requestCategory.id", categoryId).list();
    }

    public List<Request> findBySubmissionDate(LocalDate submissionDate) {
        return find("submissionDate", submissionDate).list();
    }

    public List<Request> findBySubmissionMonth(int month) {
        return find("MONTH(submissionDate) = ?1", month).list();
    }

    public List<Request> findByClosingDate(LocalDate closingDate) {
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
                Parameters.with("status", Status.ACTIVE)).list();
    }

    public List<Request> closedRequests() {
        return (List<Request>) find("select r from Request r where r.status = :status",
                Parameters.with("status", Status.CLOSED)).list();
    }

    public List<Request> assignedRequests() {
        return (List<Request>) find("select r from Request r where r.status = :status",
                Parameters.with("status", Status.RESOLVING)).list();
    }
    //-----------------------------------------------------------------------------------------------
}
