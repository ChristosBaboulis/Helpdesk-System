package com.example.helpdesk.representation;

import com.example.helpdesk.domain.TechnicalAction;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "jakarta", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class TechnicalActionMapper {

    public abstract TechnicalActionRepresentation toRepresentation(TechnicalAction action);

    @Mapping(target = "submissionDate", ignore = true) // Submission date is auto-generated
    public abstract TechnicalAction toModel(TechnicalActionRepresentation representation);

    public abstract List<TechnicalActionRepresentation> toRepresentationList(List<TechnicalAction> action);

}
