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
    @Mapping(target = "actions", ignore = true)
    public abstract RequestRepresentation toRepresentation(Request request);

    public abstract Request toModel(RequestRepresentation representation);

    public abstract List<RequestRepresentation> toRepresentationList(List<Request> request);

    //TODO AfterMapping
}
