package com.example.helpdesk.persistence;

import java.util.List;

import com.example.helpdesk.domain.Customer;
import jakarta.enterprise.context.RequestScoped;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@RequestScoped
public class CustomerRepository implements PanacheRepositoryBase<Customer, Integer>{

    public Customer findById(Integer id) {
        return find("id", id).firstResult();
    }

    public List<Customer> findByCustomerCode(String customerCode) {
        return find("customerCode", customerCode).list();
    }

    public List<Customer> findByTelephoneNumber(String telephoneNumber) {
        return find("personalInfo.telephoneNumber", telephoneNumber).list();
    }

    public List<Customer> findByEmailAddress(String emailAddress) {
        return find("personalInfo.emailAddress", emailAddress).list();
    }

}
