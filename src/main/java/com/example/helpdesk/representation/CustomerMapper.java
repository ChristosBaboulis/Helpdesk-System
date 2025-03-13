package com.example.helpdesk.representation;

import com.example.helpdesk.domain.Customer;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "jakarta",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class CustomerMapper {
    public abstract RequestRepresentation toRepresentation(Customer customer);

    public abstract List<RequestRepresentation> toRepresentationList(List<Customer> customer);
}
