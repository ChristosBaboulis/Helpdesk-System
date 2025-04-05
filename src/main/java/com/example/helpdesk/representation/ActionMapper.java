package com.example.helpdesk.representation;

import com.example.helpdesk.domain.Action;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "jakarta",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class ActionMapper {
    public abstract ActionRepresentation toRepresentation(Action action);

    //These fields are handled by class and are not populated by a given value of the creator
    @Mapping(target = "submissionDate", ignore = true)
    public abstract Action toModel(ActionRepresentation representation);

    public abstract List<ActionRepresentation> toRepresentationList(List<Action> action);
}