package com.example.helpdesk.representation;

import java.util.List;

import com.example.helpdesk.domain.*;
import com.example.helpdesk.persistence.CustomerRepository;
import com.example.helpdesk.persistence.CustomerSupportRepository;
import com.example.helpdesk.persistence.RequestCategoryRepository;
import com.example.helpdesk.persistence.TechnicianRepository;
import jakarta.inject.Inject;
import org.mapstruct.*;

@Mapper(componentModel = "jakarta",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {CustomerMapper.class, RequestCategoryMapper.class, CustomerSupportMapper.class, TechnicianMapper.class})
public abstract class RequestMapper {

    @Inject
    RequestCategoryRepository requestCategoryRepository;
    @Inject
    CustomerRepository customerRepository;
    @Inject
    CustomerSupportRepository customerSupportRepository;
    @Inject
    TechnicianRepository technicianRepository;

    public abstract RequestRepresentation toRepresentation(Request request);

    //These fields are handled by class and are not populated by a given value of the creator
    @Mapping(target = "actions", ignore = true)
    @Mapping(target = "submissionDate", ignore = true)
    @Mapping(target = "closeDate", ignore = true)
    public abstract Request toModel(RequestRepresentation representation);

    public abstract List<RequestRepresentation> toRepresentationList(List<Request> request);

    @AfterMapping
    protected void connectToRelations(RequestRepresentation requestRepresentation,
                                      @MappingTarget Request request) {
        if (requestRepresentation.requestCategory != null && requestRepresentation.requestCategory.id != null) {
            RequestCategory requestCategory = requestCategoryRepository.findById(requestRepresentation.requestCategory.id);
            if (requestCategory != null) {
                request.setRequestCategory(requestCategory);
            } else {
                System.out.println("Request Category not found for ID: " + requestRepresentation.requestCategory.id);
            }
        }

        if (requestRepresentation.customer != null && requestRepresentation.customer.id != null) {
            Customer customer = customerRepository.findById(requestRepresentation.customer.id);
            if (customer != null) {
                request.setCustomer(customer);
            } else {
                System.out.println("Customer not found for ID: " + requestRepresentation.customer.id);
            }
        }

        if (requestRepresentation.customerSupport != null && requestRepresentation.customerSupport.id != null) {
            CustomerSupport customerSupport = customerSupportRepository.findById(requestRepresentation.customerSupport.id);
            if (customerSupport != null) {
                request.setCustomerSupport(customerSupport);
            } else {
                System.out.println("Customer Support not found for ID: " + requestRepresentation.customerSupport.id);
            }
        }

        if (requestRepresentation.technician != null && requestRepresentation.technician.id != null) {
            Technician technician = technicianRepository.findById(requestRepresentation.technician.id);
            if (technician != null) {
                request.setTechnician(technician);
            } else {
                System.out.println("Technician not found for ID: " + requestRepresentation.technician.id);
            }
        } else {
            request.setTechnician(null); // Remove technician if not provided
        }

        if (requestRepresentation.status != null) {
            request.setStatus(requestRepresentation.status);
        } else if (request.getId() == null) { // Only set ACTIVE for new requests
            request.setStatus(Status.ACTIVE);
        }
    }

}
