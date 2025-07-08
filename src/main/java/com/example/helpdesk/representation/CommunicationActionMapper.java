package com.example.helpdesk.representation;

import com.example.helpdesk.domain.CommunicationAction;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "jakarta",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class CommunicationActionMapper {

    public abstract CommunicationActionRepresentation toRepresentation(CommunicationAction action);

    @Mapping(target = "submissionDate", ignore = true) // Submission date is auto-generated
    public abstract CommunicationAction toModel(CommunicationActionRepresentation representation);

    public abstract List<CommunicationActionRepresentation> toRepresentationList(List<CommunicationAction> action);

}
