package com.example.helpdesk.representation;

import com.example.helpdesk.domain.CustomerSupport;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "jakarta",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class CustomerSupportMapper {
    public abstract CustomerSupportRepresentation toRepresentation(CustomerSupport customerSupport);

    public abstract CustomerSupport toModel(CustomerSupportRepresentation representation);

    public abstract List<CustomerSupportRepresentation> toRepresentationList(List<CustomerSupport> customerSupport);
}
