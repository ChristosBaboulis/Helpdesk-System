package com.example.helpdesk.representation;

import java.util.List;

import com.example.helpdesk.domain.Request;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "jakarta",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {CustomerMapper.class, RequestCategoryMapper.class, CustomerSupportMapper.class, TechnicianMapper.class})
public abstract class RequestMapper {
    public abstract RequestRepresentation toRepresentation(Request request);

    //These fields are handled by class and are not populated by a given value of the creator
    @Mapping(target = "actions", ignore = true)
    @Mapping(target = "submissionDate", ignore = true)
    @Mapping(target = "closeDate", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "technician", ignore = true)
    public abstract Request toModel(RequestRepresentation representation);

    public abstract List<RequestRepresentation> toRepresentationList(List<Request> request);

    //TODO AfterMapping
}
