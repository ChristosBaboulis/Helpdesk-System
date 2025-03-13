package com.example.helpdesk.representation;

import java.util.List;

import com.example.helpdesk.domain.Request;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "jakarta",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {CustomerMapper.class})
public abstract class RequestMapper {
    public abstract RequestRepresentation toRepresentation(Request request);

    public abstract List<RequestRepresentation> toRepresentationList(List<Request> request);
}
