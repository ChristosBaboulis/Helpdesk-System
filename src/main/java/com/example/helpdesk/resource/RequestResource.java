package com.example.helpdesk.resource;

import com.example.helpdesk.domain.Request;
import com.example.helpdesk.persistence.RequestRepository;
import com.example.helpdesk.representation.RequestMapper;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

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
    @Path("{telephoneNumber:^[0-9]{10}$}")
    public Response findByPhoneNumber(@PathParam("telephoneNumber") String telephoneNumber) {

        Request request = requestRepository.findByTelephoneNumber(telephoneNumber).getFirst();
        if (request == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok().entity(requestMapper.toRepresentation(request)).build();
    }

    @GET
    @Path("{status:^[a-zA-Z]+$}")
    public Response findByStatus(@PathParam("status") String status) {

        Request request = requestRepository.findByStatus(status).getFirst();
        if (request == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok().entity(requestMapper.toRepresentation(request)).build();
    }
}