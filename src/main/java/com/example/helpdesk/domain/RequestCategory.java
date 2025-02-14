package com.example.helpdesk.domain;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "Request_Categories")
public class RequestCategory {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "category_type", length = 50, nullable = false)
    private String categoryType;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "specialty_id", nullable = false)
    private Specialty specialty;

    public RequestCategory() {}

    public RequestCategory(String categoryType, Specialty specialty) {
        this.categoryType = categoryType;
        this.specialty = specialty;
    }

    public int getId() {
        return id;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public Specialty getSpecialty() {
        return specialty;
    }

    public void setSpecialty(Specialty specialty) {
        this.specialty = specialty;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RequestCategory that = (RequestCategory) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
