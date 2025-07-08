package com.example.helpdesk.representation;

import com.example.helpdesk.domain.RequestCategory;
import com.example.helpdesk.domain.Specialty;
import com.example.helpdesk.persistence.SpecialtyRepository;
import jakarta.inject.Inject;
import org.mapstruct.AfterMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "jakarta",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {SpecialtyMapper.class})
public abstract class RequestCategoryMapper {

    @Inject
    SpecialtyRepository specialtyRepository;

    public abstract RequestCategoryRepresentation toRepresentation(RequestCategory requestCategory);

    public abstract RequestCategory toModel(RequestCategoryRepresentation representation);

    public abstract List<RequestCategoryRepresentation> toRepresentationList(List<RequestCategory> requestCategory);

    @AfterMapping
    protected void connectToSpecialty(RequestCategoryRepresentation representation,
                                     @MappingTarget RequestCategory requestCategory) {
        if(representation.specialty != null) {
            Specialty specialty = specialtyRepository.findById(representation.specialty.id);
            requestCategory.setSpecialty(specialty);
        }
    }

}
