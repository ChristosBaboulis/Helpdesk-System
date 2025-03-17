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

    //GET BY ID
    @GET
    @Path("{requestId:[0-9]*}")
    public Response find(@PathParam("requestId") Integer requestId) {

        //SEARCH REQUEST BY ID GIVEN
        Request request = requestRepository.findById(requestId);

        //IF NONE FOUND, RETURN 404
        if (request == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        //IF FOUND RETURN REPRESENTATION IN JSON AND 200 CODE
        return Response.ok().entity(requestMapper.toRepresentation(request)).build();

    }

    //GET BY TELEPHONE NUMBER
    @GET
    @Path("/phone/{telephoneNumber}")
    public Response findByPhoneNumber(@PathParam("telephoneNumber") String telephoneNumber) {

        //TELEPHONE NUMBER FORMAT CHECK - IF BAD RETURN 400
        if (!telephoneNumber.matches("\\d{10}")) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Invalid phone number format. It must be exactly 10 digits.")
                    .build();
        }

        //SEARCH REQUEST BY TELEPHONE NUMBER GIVEN
        Request request = requestRepository.findByTelephoneNumber(telephoneNumber).getFirst();

        //IF NONE FOUND, RETURN 404
        if (request == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        //IF FOUND RETURN REPRESENTATION IN JSON AND 200 CODE
        return Response.ok().entity(requestMapper.toRepresentation(request)).build();
    }

    //GET BY STATUS
    @GET
    @Path("/status/{status}")
    public Response findByStatus(@PathParam("status") String status) {

        //STATUS FORMAT CHECK - IF BAD RETURN 400
        if (!status.matches("^[a-zA-Z]+$")) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Invalid status format. Must contain only letters (A-Z or a-z).")
                    .build();
        }

        try {
            // Convert String to Enum
            Status statusEnum = Status.valueOf(status.toUpperCase());

            //SEARCH REQUEST BY STATUS NAME
            Request request = requestRepository.findByStatus(statusEnum).getFirst();

            //IF NONE FOUND, RETURN 404
            if (request == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            //IF FOUND RETURN REPRESENTATION IN JSON AND 200 CODE
            return Response.ok().entity(requestMapper.toRepresentation(request)).build();

        } catch (IllegalArgumentException e) {

            //IF STATUS NAME IS NOT IN ENUMERATED VALUES, RETURN 400 AND ARRAY WITH AVAILABLE VALUES
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Invalid status value. Allowed values: " + Arrays.toString(Status.values()))
                    .build();
        }
    }

    //CREATE A NEW REQUEST
    @PUT
    @Transactional
    public Response create(RequestRepresentation representation) {
        System.out.println("Create API Method Called!");
        //Check for id to be null in representation
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
        if (representation.telephoneNumber == null || representation.telephoneNumber.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Telephone number is required.")
                    .build();
        }

        Request request = requestMapper.toModel(representation);
        requestRepository.persist(request);
        URI uri = UriBuilder.fromResource(RequestResource.class).path(String.valueOf(request.getId())).build();
        return Response.created(uri).entity(requestMapper.toRepresentation(request)).build();
    }

}