package com.example.helpdesk.representation;

import com.example.helpdesk.domain.RequestCategory;
import com.example.helpdesk.domain.Specialty;
import com.example.helpdesk.domain.Technician;
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
    }
}
