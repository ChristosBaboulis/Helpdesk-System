package com.example.helpdesk.resource;

import com.example.helpdesk.domain.Request;
import com.example.helpdesk.domain.Specialty;
import com.example.helpdesk.domain.Technician;
import com.example.helpdesk.persistence.RequestRepository;
import com.example.helpdesk.persistence.SpecialtyRepository;
import com.example.helpdesk.persistence.TechnicianRepository;
import com.example.helpdesk.representation.TechnicianMapper;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;


import java.util.ArrayList;
import java.util.List;

import static com.example.helpdesk.resource.HelpdeskUri.TECHNICIANS;

@Path(TECHNICIANS)
@RequestScoped
public class TechnicianResource {
    @Inject
    TechnicianRepository technicianRepository;
    @Inject
    TechnicianMapper technicianMapper;
    @Inject
    SpecialtyRepository specialtyRepository;
    @Inject
    RequestRepository requestRepository;

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

    //Επιστρέφει έναν τεχνικό βάσει του κωδικού που έχει
    @GET
    @Path("{technicianCode}")
    public Response findByCode(@PathParam("technicianCode") String technicianCode) {

        // Validate that the technicianCode contains only alphanumeric characters
        if (!technicianCode.matches("^[a-zA-Z0-9]+$")) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Invalid technician code format. Only alphanumeric characters are allowed.")
                    .build();
        }

        Technician technician = technicianRepository.findByTechnicianCode(technicianCode).getFirst();
        if (technician == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok().entity(technicianMapper.toRepresentation(technician)).build();

    }

    // Αφαιρεί Ειδικότητα
    @PUT
    @Path("{technicianId:[0-9]*}/removeSpecialty/{specialtyId:[0-9]*}")
    @Transactional
    public Response removeSpecialty(@PathParam("technicianId") Integer technicianId, @PathParam("specialtyId") Integer specialtyId) {

        //Check if technician exists in db
        Technician technician = technicianRepository.findById(technicianId);
        if (technician == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        //Check if specialty exists in db
        Specialty specialty = specialtyRepository.findById(specialtyId);
        if (specialty == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        //Remove specialty from technician both in domain and DB Levels
        technician.removeSpecialty(specialty);
        technicianRepository.getEntityManager().merge(technician);

        return Response.ok().entity(technicianMapper.toRepresentation(technician)).build();
    }

    // Προσθέτει Ειδικότητα
    @PUT
    @Path("{technicianId:[0-9]*}/addSpecialty/{specialtyId:[0-9]*}")
    @Transactional
    public Response addSpecialty(@PathParam("technicianId") Integer technicianId, @PathParam("specialtyId") Integer specialtyId,Technician updatedTechnician) {

        //Check if technician exists in db
        Technician technician = technicianRepository.findById(technicianId);
        if (technician == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        //Check if specialty exists in db
        Specialty specialty = specialtyRepository.findById(specialtyId);
        if (specialty == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        //Add specialty to technician both in domain and DB Levels
        technician.setSpecialty(specialty);
        technicianRepository.getEntityManager().merge(technician);

        return Response.ok().entity(technicianMapper.toRepresentation(technician)).build();
    }
}
