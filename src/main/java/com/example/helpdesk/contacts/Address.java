package com.example.helpdesk.contacts;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Address {

    @Column(name = "street", length = 50)
    private String street;

    @Column(name = "street_number", length = 10)
    private String number;

    @Column(name = "city", length = 50)
    private String city;

    @Column(name = "zip_code", length = 50)
    private String zipCode;

    public Address() {}

    //Copy Constructor
    public Address(Address address) {
        this.street = address.getStreet();
        this.number = address.getNumber();
        this.city = address.getCity();
        this.zipCode = address.getZipCode();
    }

    public Address(String street, String number, String city, String zipCode) {
        this.street = street;
        this.number = number;
        this.city = city;
        this.zipCode = zipCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zip) {
        this.zipCode = zip;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(street, address.street) && Objects.equals(number, address.number) && Objects.equals(city, address.city) && Objects.equals(zipCode, address.zipCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, number, city, zipCode);
    }
}
