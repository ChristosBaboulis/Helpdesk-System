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
        if (customer.getPersonalInfo() == null) {
            customer.setPersonalInfo(new PersonalInfo());
        }
        customer.getPersonalInfo().setFirstName(representation.firstName);
        customer.getPersonalInfo().setLastName(representation.lastName);
        customer.getPersonalInfo().setEmailAddress(representation.emailAddress);
        customer.getPersonalInfo().setTelephoneNumber(representation.telephoneNumber);
        customer.getPersonalInfo().setBirthdate(representation.birthdate);
        customer.getPersonalInfo().setAddress(representation.address);

        customer.setPersonalInfo(customer.getPersonalInfo());
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
            throw new IllegalStateException("PersonalInfo is missing in Customer entity!");
        }
    }
}
