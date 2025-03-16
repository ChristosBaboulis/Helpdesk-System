package com.example.helpdesk.resource;

import com.example.helpdesk.domain.*;
import com.example.helpdesk.persistence.*;
import com.example.helpdesk.representation.*;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.Arrays;

import static com.example.helpdesk.resource.HelpdeskUri.REQUESTS;

@Path(REQUESTS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class RequestResource {

    @Inject
    RequestRepository requestRepository;

    @Inject
    RequestMapper requestMapper;

    @Inject
    RequestCategoryRepository requestCategoryRepository;

    @Inject
    CustomerRepository customerRepository;

    @Inject
    CustomerSupportRepository customerSupportRepository;

    @Inject
    TechnicianRepository technicianRepository;

    @GET
    @Path("{requestId:[0-9]*}")
    public Response find(@PathParam("requestId") Integer requestId) {

        Request request = requestRepository.findById(requestId);
        if (request == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok().entity(requestMapper.toRepresentation(request)).build();
    }

    @GET
    @Path("/phone/{telephoneNumber}")
    public Response findByPhoneNumber(@PathParam("telephoneNumber") String telephoneNumber) {
        //Telephone number format check
        if (!telephoneNumber.matches("\\d{10}")) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Invalid phone number format. It must be exactly 10 digits.")
                    .build();
        }

        Request request = requestRepository.findByTelephoneNumber(telephoneNumber).getFirst();
        if (request == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok().entity(requestMapper.toRepresentation(request)).build();
    }

    @GET
    @Path("/status/{status}")
    public Response findByStatus(@PathParam("status") String status) {
        //Status format check
        if (!status.matches("^[a-zA-Z]+$")) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Invalid status format. Must contain only letters (A-Z or a-z).")
                    .build();
        }

        try {
            // Convert String to Enum
            Status statusEnum = Status.valueOf(status.toUpperCase());

            Request request = requestRepository.findByStatus(statusEnum).getFirst();
            if (request == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            return Response.ok().entity(requestMapper.toRepresentation(request)).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Invalid status value. Allowed values: " + Arrays.toString(Status.values()))
                    .build();
        }
    }

    //CREATE A NEW REQUEST W/O PROVIDING ID
    @POST
    @Transactional
    public Response create(RequestRepresentation representation) {
        if(representation.id != null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Request ID should not be provided when creating a new request.")
                    .build();
        }

        // Validate required fields before accessing them
        if (representation.customer == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Customer is required.")
                    .build();
        }
        if (representation.requestCategory == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Request Category is required.")
                    .build();
        }
        if (representation.customerSupport == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Customer Support is required.")
                    .build();
        }

        // Fetch existing Customer, RequestCategory, CustomerSupport from DB
        RequestCategory category = requestCategoryRepository.findById(representation.requestCategory.id);
        if (category == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Request Category not found in the database.")
                    .build();
        }

        Customer customer = customerRepository.findById(representation.customer.id);
        if (customer == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Customer not found in the database.")
                    .build();
        }
        CustomerSupport customerSupport = customerSupportRepository.findById(representation.customerSupport.id);
        if (customerSupport == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Customer Support not found in the database.")
                    .build();
        }

        // Validate required fields
        if (representation.telephoneNumber == null || representation.telephoneNumber.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Telephone number is required.")
                    .build();
        }

        if (representation.requestCategory == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Category is required.")
                    .build();
        }

        if (representation.customer == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Customer is required.")
                    .build();
        }

        if (representation.problemDescription == null) {
            representation.problemDescription = "";
        }

        Request request = requestMapper.toModel(representation);

        // Set the fetched entities to avoid transient entity errors
        request.setRequestCategory(category);
        request.setCustomer(customer);
        request.setCustomerSupport(customerSupport);

        requestRepository.persist(request);
        URI uri = UriBuilder.fromResource(RequestResource.class).path(String.valueOf(request.getId())).build();
        return Response.created(uri).entity(requestMapper.toRepresentation(request)).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response update(@PathParam("id") Integer id,
                           RequestRepresentation representation) {

        System.out.println("Received update request for ID: " + id);
        System.out.println("Request Body ID: " + representation.id);
        System.out.println("Problem Description: " + representation.problemDescription);
        System.out.println("Telephone Number: " + representation.telephoneNumber);
        System.out.println("Customer ID: " + (representation.customer != null ? representation.customer.id : "NULL"));
        System.out.println("Category ID: " + (representation.requestCategory != null ? representation.requestCategory.id : "NULL"));
        System.out.println("Status: " + representation.status);

        if (! id.equals(representation.id)) {
            throw new RuntimeException();
        }

        Request request = requestMapper.toModel(representation);
        requestRepository.getEntityManager().merge(request);

        return Response.noContent().build();
    }
}