////package com.example.helpdesk.resource;
//
//import com.example.helpdesk.domain.Action;
//import com.example.helpdesk.persistence.ActionRepository;
//import com.example.helpdesk.representation.ActionMapper;
//import jakarta.enterprise.context.RequestScoped;
//import jakarta.inject.Inject;
//import jakarta.ws.rs.*;
//import jakarta.ws.rs.core.Response;
//
//import static com.example.helpdesk.resource.HelpdeskUri.ACTIONS;
//
//@Path(ACTIONS)
//@RequestScoped
//public class ActionResource {
//
//    @Inject
//    ActionRepository actionRepository;
//
//    @Inject
//    ActionMapper actionMapper;
//
//    // POST /actions - Δημιουργεί μια νέα ενέργεια
//    @POST
//    public Response createAction(Action action) {
//        actionRepository.persist(action);
//        return Response.status(Response.Status.CREATED).entity(actionMapper.toRepresentation(action)).build();
//    }
//
//    // DELETE /actions/{id} - Διαγράφει μια ενέργεια
//    @DELETE
//    @Path("{actionId:[0-9]*}")
//    public Response deleteAction(@PathParam("actionId") Integer actionId) {
//        Action action = actionRepository.findById(actionId);
//        if (action == null) {
//            return Response.status(Response.Status.NOT_FOUND).build();
//        }
//        actionRepository.delete(action);
//        return Response.noContent().build();
//    }
//}