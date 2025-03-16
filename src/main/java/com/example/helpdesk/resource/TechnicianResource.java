package com.example.helpdesk.resource;

import com.example.helpdesk.domain.Technician;
import com.example.helpdesk.persistence.TechnicianRepository;
import com.example.helpdesk.representation.TechnicianMapper;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;


import static com.example.helpdesk.resource.HelpdeskUri.TECHNICIANS;

@Path(TECHNICIANS)
@RequestScoped
public class TechnicianResource {
    @Inject
    TechnicianRepository technicianRepository;

    @Inject
    TechnicianMapper technicianMapper;

    //Επιστρέφει ένα id τεχνικού
    @GET
    @Path("{technicianId:[0-9]*}")
    public Response find(@PathParam("technicianId") Integer technicianId) {

        Technician technician = technicianRepository.findById(technicianId);
        if (technician == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok().entity(technicianMapper.toRepresentation(technician)).build();
    }


    //Επιστρέφει έναν τεχνικό βάσει του κωδικού που εχει
    @GET
    @Path("{technicianCode:^[a-zA-Z0-9]+$}")
    public Response findByCode(@PathParam("technicianCode") Integer technicianCode) {
        Technician technician = technicianRepository.findById(technicianCode);
        if (technician == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok().entity(technicianMapper.toRepresentation(technician)).build();

    }


    // Αφαιρεί ένα αίτημα ή ενημερώνει/προσθέτει ειδικότητα
    @PUT
    @Path("{technicianId:[0-9]*}")
    public Response updateTechnician(@PathParam("technicianId") Integer technicianId, Technician updatedTechnician) {
        Technician technician = technicianRepository.findById(technicianId);
        if (technician == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
       // technician.setSpecialty(updatedTechnician.getSpecialty());
       // technician.setRequests(updatedTechnician.getRequests());



        return Response.ok().entity(technicianMapper.toRepresentation(technician)).build();}





}
