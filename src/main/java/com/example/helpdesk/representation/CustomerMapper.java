package com.example.helpdesk.representation;

import com.example.helpdesk.domain.Customer;
import com.example.helpdesk.domain.PersonalInfo;
import org.mapstruct.AfterMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "jakarta",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class CustomerMapper {
    public abstract CustomerRepresentation toRepresentation(Customer customer);

    public abstract Customer toModel(CustomerRepresentation representation);

    public abstract List<CustomerRepresentation> toRepresentationList(List<Customer> customer);

    @AfterMapping
    public void fillPersonalInfo(CustomerRepresentation representation, @MappingTarget Customer customer) {
        PersonalInfo personalInfo = new PersonalInfo();
        personalInfo.setFirstName(representation.firstName);
        personalInfo.setLastName(representation.lastName);
        personalInfo.setEmailAddress(representation.emailAddress);
        personalInfo.setTelephoneNumber(representation.telephoneNumber);
        personalInfo.setBirthdate(representation.birthdate);
        personalInfo.setAddress(representation.address);

        customer.setPersonalInfo(personalInfo);
    }

    @AfterMapping
    public void extractPersonalInfo(@MappingTarget CustomerRepresentation representation, Customer customer) {
        if (customer.getPersonalInfo() != null) {
            representation.firstName = customer.getPersonalInfo().getFirstName();
            representation.lastName = customer.getPersonalInfo().getLastName();
            representation.emailAddress = customer.getPersonalInfo().getEmailAddress();
            representation.telephoneNumber = customer.getPersonalInfo().getTelephoneNumber();
            representation.birthdate = customer.getPersonalInfo().getBirthdate();
            representation.address = customer.getPersonalInfo().getAddress();
        } else {
            System.out.println("personalInfo is NULL when mapping Customer → CustomerRepresentation!");
        }
    }
}
