package com.example.helpdesk.resource;

import com.example.helpdesk.domain.Request;
import com.example.helpdesk.domain.Status;
import com.example.helpdesk.persistence.RequestRepository;
import com.example.helpdesk.representation.RequestMapper;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

import java.util.Arrays;

import static com.example.helpdesk.resource.HelpdeskUri.REQUESTS;

@Path(REQUESTS)
@RequestScoped
public class RequestResource {

    @Inject
    RequestRepository requestRepository;

    @Inject
    RequestMapper requestMapper;

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
}