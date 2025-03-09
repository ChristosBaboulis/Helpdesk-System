package com.example.helpdesk.persistence;

import java.util.List;

import com.example.helpdesk.domain.RequestCategory;
import com.example.helpdesk.domain.Specialty;
import jakarta.enterprise.context.RequestScoped;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;

@RequestScoped
public class RequestCategoryRepository implements PanacheRepositoryBase<RequestCategory, Integer>{
    //---------------------------------------- BY ATTRIBUTES ----------------------------------------
    public RequestCategory findById(Integer id) {
        return find("id", id).firstResult();
    }

    public List<RequestCategory> findByCategoryType(String categoryType) {
        return find("customerCode", categoryType).list();
    }
    //-----------------------------------------------------------------------------------------------

    //---------------------------------------- BY DEPENDENCIES ----------------------------------------
    public List<RequestCategory> findBySpecialty(Specialty specialty) {
        return find("specialty", specialty).list();
    }
    //-----------------------------------------------------------------------------------------------
}