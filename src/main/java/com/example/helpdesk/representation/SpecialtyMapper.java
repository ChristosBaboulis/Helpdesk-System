package com.example.helpdesk.representation;

import com.example.helpdesk.domain.Specialty;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "jakarta",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class SpecialtyMapper {
    public abstract RequestRepresentation toRepresentation(Specialty specialty);

    public abstract List<RequestRepresentation> toRepresentationList(List<Specialty> specialty);
}
