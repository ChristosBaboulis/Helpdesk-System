package com.example.helpdesk.representation;

import com.example.helpdesk.domain.*;
import com.example.helpdesk.persistence.SpecialtyRepository;
import jakarta.inject.Inject;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "jakarta",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {SpecialtyMapper.class})
public abstract class TechnicianMapper {

    @Inject
    SpecialtyRepository specialtyRepository;

    public abstract TechnicianRepresentation toRepresentation(Technician requestCategory);

    //These fields are handled by class and are not populated by a given value of the creator
    @Mapping(target = "specialties", ignore = true)
    public abstract Technician toModel(TechnicianRepresentation representation);

    public abstract List<TechnicianRepresentation> toRepresentationList(List<Technician> requestCategory);

    @AfterMapping
    protected void connectToSpecialty(TechnicianRepresentation representation,
                                      @MappingTarget Technician technician) {

        if (!representation.specialties.isEmpty()) {
            for(SpecialtyRepresentation specialtyRepresentation : representation.specialties) {
                Specialty specialty = specialtyRepository.findById(specialtyRepresentation.id);
                if (specialty == null) {
                    throw new RuntimeException();
                }
                technician.setSpecialty(specialty);
            }
        }

        PersonalInfo personalInfo = new PersonalInfo();
        personalInfo.setFirstName(representation.firstName);
        personalInfo.setLastName(representation.lastName);
        personalInfo.setEmailAddress(representation.emailAddress);
        personalInfo.setTelephoneNumber(representation.telephoneNumber);
        personalInfo.setBirthdate(representation.birthdate);
        personalInfo.setAddress(representation.address);

        technician.setPersonalInfo(personalInfo);
    }

    @AfterMapping
    public void extractPersonalInfo(@MappingTarget TechnicianRepresentation representation, Technician technician) {
        if (technician.getPersonalInfo() != null) {
            representation.firstName = technician.getPersonalInfo().getFirstName();
            representation.lastName = technician.getPersonalInfo().getLastName();
            representation.emailAddress = technician.getPersonalInfo().getEmailAddress();
            representation.telephoneNumber = technician.getPersonalInfo().getTelephoneNumber();
            representation.birthdate = technician.getPersonalInfo().getBirthdate();
            representation.address = technician.getPersonalInfo().getAddress();
        } else {
            System.out.println("personalInfo is NULL when mapping CustomerSupport → CustomerSupportRepresentation!");
        }
    }

}
