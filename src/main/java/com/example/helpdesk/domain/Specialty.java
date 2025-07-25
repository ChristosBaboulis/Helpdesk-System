package com.example.helpdesk.domain;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "Specialties")
public class Specialty {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "specialty_type", length = 50, nullable = false)
    private String specialtyType;

    public Specialty() {}

    public Specialty(String specialtyType) {
        this.specialtyType = specialtyType;
    }

    public Integer getId() {
        return id;
    }

    public String getSpecialtyType() {
        return specialtyType;
    }

    public void setSpecialtyType(String specialtyType) {
        this.specialtyType = specialtyType;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Specialty specialty = (Specialty) o;
        return Objects.equals(specialtyType, specialty.specialtyType);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(specialtyType);
    }

}
