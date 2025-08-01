package com.example.helpdesk.resource;

import com.example.helpdesk.domain.CommunicationAction;
import com.example.helpdesk.domain.TechnicalAction;
import com.example.helpdesk.persistence.ActionRepository;
import com.example.helpdesk.representation.*;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDate;
import java.util.List;

import static com.example.helpdesk.resource.HelpdeskUri.ACTIONS;

@Path(ACTIONS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class ActionResource {

    @Inject
    ActionRepository actionRepository;

    @Inject
    CommunicationActionMapper communicationActionMapper;

    @Inject
    TechnicalActionMapper technicalActionMapper;

    //GET /actions/communication/{id} - RETRIEVE A CommunicationAction BY ID
    @GET
    @Path("/communication/{id}")
    public Response getCommunicationActionById(@PathParam("id") Integer id) {
        CommunicationAction action = (CommunicationAction) actionRepository.findById(id);
        if (action == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(communicationActionMapper.toRepresentation((CommunicationAction) action)).build();
    }

    //GET /actions/technical/{id} - RETRIEVE A TechnicalAction BY ID
    @GET
    @Path("/technical/{id}")
    public Response getTechnicalActionById(@PathParam("id") Integer id) {
        TechnicalAction action = (TechnicalAction) actionRepository.findById(id);
        if (action == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(technicalActionMapper.toRepresentation((TechnicalAction) action)).build();
    }

    //GET /actions/communication/by-date/{date} - RETRIEVE CommunicationActions BY submissionDate
    @GET
    @Path("/communication/by-date/{date}")
    public Response getCommunicationActionsByDate(@PathParam("date") String date) {
        LocalDate submissionDate = LocalDate.parse(date);
        List<CommunicationAction> actions = actionRepository.list(
                "FROM Action a WHERE a.submissionDate = ?1 AND TREAT(a AS CommunicationAction) IS NOT NULL", submissionDate
        ).stream().map(action -> (CommunicationAction) action).toList(); // CAST TO CommunicationAction
        return Response.ok(communicationActionMapper.toRepresentationList(actions)).build();
    }

    //GET /actions/technical/by-date/{date} - RETRIEVE TechnicalActions BY submissionDate
    @GET
    @Path("/technical/by-date/{date}")
    public Response getTechnicalActionsByDate(@PathParam("date") String date) {
        LocalDate submissionDate = LocalDate.parse(date);
        List<TechnicalAction> actions = actionRepository.list(
                "FROM Action a WHERE a.submissionDate = ?1 AND TREAT(a AS TechnicalAction) IS NOT NULL", submissionDate
        ).stream().map(action -> (TechnicalAction) action).toList(); //CAST TO TechnicalAction

        return Response.ok(technicalActionMapper.toRepresentationList(actions)).build();
    }

    //POST /actions/communication - CREATE A CommunicationAction
    @POST
    @Path("/communication")
    @Transactional
    @RolesAllowed({"ADMIN", "CUSTOMERSUPPORT"})
    public Response createCommunicationAction(CommunicationActionRepresentation representation) {
        CommunicationAction action = communicationActionMapper.toModel(representation);
        actionRepository.persist(action);
        return Response.status(Response.Status.CREATED)
                .entity(communicationActionMapper.toRepresentation(action))
                .build();
    }

    //POST /actions/technical - CREATE A TechnicalAction
    @POST
    @Path("/technical")
    @Transactional
    @RolesAllowed({"ADMIN", "TECHNICIAN"})
    public Response createTechnicalAction(TechnicalActionRepresentation representation) {
        TechnicalAction action = technicalActionMapper.toModel(representation);
        actionRepository.persist(action);
        return Response.status(Response.Status.CREATED)
                .entity(technicalActionMapper.toRepresentation(action))
                .build();
    }

}
