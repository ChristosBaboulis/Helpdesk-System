package com.example.helpdesk.resource;

import com.example.helpdesk.domain.Specialty;
import com.example.helpdesk.domain.Technician;
import com.example.helpdesk.persistence.RequestRepository;
import com.example.helpdesk.persistence.SpecialtyRepository;
import com.example.helpdesk.persistence.TechnicianRepository;
import com.example.helpdesk.representation.TechnicianMapper;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import static com.example.helpdesk.resource.HelpdeskUri.TECHNICIANS;

@Path(TECHNICIANS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
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

    //GET /technicians/{technicianId} - RETRIEVE A Technician BY ID
    @GET
    @Path("{technicianId:[0-9]*}")
    public Response find(@PathParam("technicianId") Integer technicianId) {

        Technician technician = technicianRepository.findById(technicianId);
        if (technician == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok().entity(technicianMapper.toRepresentation(technician)).build();
    }

    //GET /technicians/{technicianCode} - RETRIEVE A Technician BY technicianCode
    @GET
    @Path("{technicianCode}")
    public Response findByCode(@PathParam("technicianCode") String technicianCode) {

        //VALIDATE THAT THE technicianCode CONTAINS ONLY ALPHANUMERIC CHARACTERS
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

    //PUT /technicians/{technicianId}/removeSpecialty/{specialtyId} - REMOVE Specialty FROM A Technician
    @PUT
    @Path("{technicianId:[0-9]*}/removeSpecialty/{specialtyId:[0-9]*}")
    @Transactional
    public Response removeSpecialty(@PathParam("technicianId") Integer technicianId, @PathParam("specialtyId") Integer specialtyId) {

        //CHECK IF Technician EXISTS IN DB
        Technician technician = technicianRepository.findById(technicianId);
        if (technician == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        //CHECK IF Specialty EXISTS IN DB
        Specialty specialty = specialtyRepository.findById(specialtyId);
        if (specialty == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        //REMOVE SPECIALTY FROM Technician BOTH IN DOMAIN AND DB LEVELS
        technician.removeSpecialty(specialty);
        technicianRepository.getEntityManager().merge(technician);

        return Response.ok().entity(technicianMapper.toRepresentation(technician)).build();
    }

    //PUT /technicians/{technicianId}/addSpecialty/{specialtyId} - ADD Specialty TO A Technician
    @PUT
    @Path("{technicianId:[0-9]*}/addSpecialty/{specialtyId:[0-9]*}")
    @Transactional
    public Response addSpecialty(@PathParam("technicianId") Integer technicianId, @PathParam("specialtyId") Integer specialtyId) {

        //CHECK IF Technician EXISTS IN DB
        Technician technician = technicianRepository.findById(technicianId);
        if (technician == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        //CHECK IF Specialty EXISTS IN DB
        Specialty specialty = specialtyRepository.findById(specialtyId);
        if (specialty == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        //ADD Specialty TO Technician BOTH IN DOMAIN AND DB LEVELS
        technician.setSpecialty(specialty);
        technicianRepository.getEntityManager().merge(technician);

        return Response.ok().entity(technicianMapper.toRepresentation(technician)).build();
    }

}
