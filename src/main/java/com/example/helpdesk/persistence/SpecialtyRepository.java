package com.example.helpdesk.persistence;

import java.util.List;

import com.example.helpdesk.domain.Specialty;
import jakarta.enterprise.context.RequestScoped;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;

@RequestScoped
public class SpecialtyRepository implements PanacheRepositoryBase<Specialty, Integer>{
    //---------------------------------------- BY ATTRIBUTES ----------------------------------------
    public Specialty findById(Integer id) {
        return find("id", id).firstResult();
    }

    public List<Specialty> findBySpecialtyType(String specialtyType) {
        return find("specialtyType", specialtyType).list();
    }
    //-----------------------------------------------------------------------------------------------
}
