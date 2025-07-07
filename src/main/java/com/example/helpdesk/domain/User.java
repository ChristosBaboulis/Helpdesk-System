package com.example.helpdesk.domain;

import com.example.helpdesk.contacts.Address;
import jakarta.persistence.*;
import org.hibernate.annotations.Formula;

import java.time.LocalDate;
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

    @Formula("user_type")
    private String role;

    public User() {}

    //Constructor with address as argument
    public User(String username, String password,
                String firstName, String lastName,
                String telephoneNumber, String emailAddress,
                LocalDate birthdate, Address address) {
        this.personalInfo = new PersonalInfo(firstName, lastName, telephoneNumber, emailAddress, birthdate, address);
        this.username = username;
        this.password = password;
    }

    //Constructor with addresses fields as parameters
    public User(String username, String password,
                String firstName, String lastName,
                String telephoneNumber, String emailAddress,
                LocalDate birthdate, String street,
                String number, String city,
                String zipCode) {
        this.personalInfo = new PersonalInfo(firstName, lastName, telephoneNumber, emailAddress, birthdate, street, number, city, zipCode);
        this.username = username;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public String getRole() {return role;}

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
        return Objects.equals(username, user.username) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }
}
