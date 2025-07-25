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

    //THESE FIELDS ARE HANDLED BY CLASS AND ARE NOT POPULATED BY A GIVEN VALUE OF THE CREATOR
    @Mapping(target = "submissionDate", ignore = true)
    public abstract Action toModel(ActionRepresentation representation);

    public abstract List<ActionRepresentation> toRepresentationList(List<Action> action);

}
