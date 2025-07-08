package com.example.helpdesk.representation;

import com.example.helpdesk.domain.CustomerSupport;
import com.example.helpdesk.domain.PersonalInfo;
import org.mapstruct.AfterMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "jakarta",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class CustomerSupportMapper {

    public abstract CustomerSupportRepresentation toRepresentation(CustomerSupport customerSupport);

    public abstract CustomerSupport toModel(CustomerSupportRepresentation representation);

    public abstract List<CustomerSupportRepresentation> toRepresentationList(List<CustomerSupport> customerSupport);

    @AfterMapping
    public void fillPersonalInfo(CustomerSupportRepresentation representation, @MappingTarget CustomerSupport customerSupport) {
        PersonalInfo personalInfo = new PersonalInfo();
        personalInfo.setFirstName(representation.firstName);
        personalInfo.setLastName(representation.lastName);
        personalInfo.setEmailAddress(representation.emailAddress);
        personalInfo.setTelephoneNumber(representation.telephoneNumber);
        personalInfo.setBirthdate(representation.birthdate);
        personalInfo.setAddress(representation.address);

        customerSupport.setPersonalInfo(personalInfo);
    }

    @AfterMapping
    public void extractPersonalInfo(@MappingTarget CustomerSupportRepresentation representation, CustomerSupport customerSupport) {
        if (customerSupport.getPersonalInfo() != null) {
            representation.firstName = customerSupport.getPersonalInfo().getFirstName();
            representation.lastName = customerSupport.getPersonalInfo().getLastName();
            representation.emailAddress = customerSupport.getPersonalInfo().getEmailAddress();
            representation.telephoneNumber = customerSupport.getPersonalInfo().getTelephoneNumber();
            representation.birthdate = customerSupport.getPersonalInfo().getBirthdate();
            representation.address = customerSupport.getPersonalInfo().getAddress();
        } else {
            System.out.println("personalInfo is NULL when mapping CustomerSupport → CustomerSupportRepresentation!");
        }
    }

}
