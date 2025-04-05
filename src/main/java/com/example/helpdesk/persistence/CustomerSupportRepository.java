package com.example.helpdesk.persistence;

import java.util.List;

import com.example.helpdesk.domain.CustomerSupport;
import jakarta.enterprise.context.RequestScoped;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;

@RequestScoped
public class CustomerSupportRepository implements PanacheRepositoryBase<CustomerSupport, Integer>{
    //---------------------------------------- BY ATTRIBUTES ----------------------------------------
    public CustomerSupport findById(Integer id) {
        return find("id", id).firstResult();
    }

    public List<CustomerSupport> findByEmplCode(String emplCode) {
        return find("emplCode", emplCode).list();
    }

    public List<CustomerSupport> findByTelephoneNumber(String telephoneNumber) {
        return find("personalInfo.telephoneNumber", telephoneNumber).list();
    }

    public List<CustomerSupport> findByEmailAddress(String emailAddress) {
        return find("personalInfo.emailAddress", emailAddress).list();
    }
    //-----------------------------------------------------------------------------------------------
}
