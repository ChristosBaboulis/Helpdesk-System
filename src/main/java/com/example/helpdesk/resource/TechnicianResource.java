package com.example.helpdesk.resource;

import com.example.helpdesk.domain.Technician;
import com.example.helpdesk.persistence.TechnicianRepository;
import com.example.helpdesk.representation.TechnicianMapper;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
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

    @GET
    @Path("{technicianId:[0-9]*}")
public Response find(@PathParam("technicianId") Integer technicianId) {

    Technician technician = TechnicianRepository.findById(technicianId);
    if (technician == null) {
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    return Response.ok().entity(TechnicianMapper.toRepresentation(technician)).build();
}


}
