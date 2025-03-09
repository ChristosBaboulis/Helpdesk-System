package com.example.helpdesk.persistence;

import java.util.List;

import jakarta.enterprise.context.RequestScoped;

import com.example.helpdesk.domain.Technician;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;

@RequestScoped
public class TechnicianRepository implements PanacheRepositoryBase<Technician, Integer>{
    //---------------------------------------- BY ATTRIBUTES ----------------------------------------
    public Technician findById(Integer id) {
        return find("id", id).firstResult();
    }

    public List<Technician> findByName(String name) {
        return find("name", name).list();
    }

    public List<Technician> findByTechnicianCode(String technicianCode) {
        return find("technicianCode", technicianCode).list();
    }

    public List<Technician> findByTelephoneNumber(String telephoneNumber) {
        return find("telephoneNumber", telephoneNumber).list();
    }

    public List<Technician> findByEmailAddress(String emailAddress) {
        return find("emailAddress", emailAddress).list();
    }
    //-----------------------------------------------------------------------------------------------

    //---------------------------------------- BY DEPENDENCIES ----------------------------------------
    //TODO
    //-----------------------------------------------------------------------------------------------
}
