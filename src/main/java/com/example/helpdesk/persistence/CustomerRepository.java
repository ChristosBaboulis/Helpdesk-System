package com.example.helpdesk.persistence;

import java.util.List;

import com.example.helpdesk.domain.Customer;
import jakarta.enterprise.context.RequestScoped;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;

@RequestScoped
public class CustomerRepository implements PanacheRepositoryBase<Customer, Integer>{
    //---------------------------------------- BY ATTRIBUTES ----------------------------------------
    public Customer findById(Integer id) {
        return find("id", id).firstResult();
    }

    public List<Customer> findByCustomerCode(String customerCode) {
        return find("customerCode", customerCode).list();
    }

    public List<Customer> findByTelephoneNumber(String telephoneNumber) {
        return find("telephoneNumber", telephoneNumber).list();
    }

    public List<Customer> findByEmailAddress(String emailAddress) {
        return find("emailAddress", emailAddress).list();
    }
    //-----------------------------------------------------------------------------------------------
}
