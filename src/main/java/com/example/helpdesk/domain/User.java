package com.example.helpdesk.domain;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "Users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Integer id;

    @Embedded
    protected PersonalInfo personalInfo;

    @Column(name = "user_name", length = 50, nullable = false)
    protected String username;

    @Column(name = "password", length = 50, nullable = false)
    protected String password;

    public User() {}

    public User(String username, String password,
                String firstName, String lastName,
                String telephoneNumber, String emailAddress,
                String birthdate, String street,
                String number, String city,
                String zipCode) {
        this.personalInfo = new PersonalInfo(firstName, lastName, telephoneNumber, emailAddress, birthdate,street, number, city, zipCode);
        this.username = username;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public PersonalInfo getPersonalInfo() {
        return personalInfo;
    }

    public void setPersonalInfo(PersonalInfo personalInfo) {
        this.personalInfo = personalInfo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
