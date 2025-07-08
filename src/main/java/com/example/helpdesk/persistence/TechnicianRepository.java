package com.example.helpdesk.persistence;

import java.util.List;

import jakarta.enterprise.context.RequestScoped;

import com.example.helpdesk.domain.Technician;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@RequestScoped
public class TechnicianRepository implements PanacheRepositoryBase<Technician, Integer>{

    public Technician findById(Integer id) {
        return find("id", id).firstResult();
    }

    public List<Technician> findByLastName(String name) {
        return find("personalInfo.lastName", name).list();
    }

    public List<Technician> findByTechnicianCode(String technicianCode) {
        return find("technicianCode", technicianCode).list();
    }

    public List<Technician> findByTelephoneNumber(String telephoneNumber) {
        return find("personalInfo.telephoneNumber", telephoneNumber).list();
    }

    public List<Technician> findByEmailAddress(String emailAddress) {
        return find("personalInfo.emailAddress", emailAddress).list();
    }

    public List<Technician> findBySpecialty(Integer specialtyId) {
        return find("SELECT t FROM Technician t JOIN t.specialties s WHERE s.id = ?1", specialtyId).list();
    }

    public List<Technician> findBySpecialtyType(String specialtyType) {
        return find("SELECT t FROM Technician t JOIN t.specialties s WHERE s.specialtyType = ?1", specialtyType).list();
    }

}
