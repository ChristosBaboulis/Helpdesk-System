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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    @Inject
    ActionRepository actionRepository;

    //GET /requests/{requestId} - RETRIEVE Request BY ID
    @GET
    @Path("{requestId:[0-9]*}")
    public Response find(@PathParam("requestId") Integer requestId) {

        //SEARCH Request BY ID GIVEN
        Request request = requestRepository.findById(requestId);

        //IF NONE FOUND, RETURN 404
        if (request == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        //IF FOUND, RETURN REPRESENTATION IN JSON AND 200 CODE
        return Response.ok().entity(requestMapper.toRepresentation(request)).build();

    }

    //GET /requests/phone/{telephoneNumber} - RETRIEVE Request BY telephoneNumber
    @GET
    @Path("/phone/{telephoneNumber}")
    public Response findByPhoneNumber(@PathParam("telephoneNumber") String telephoneNumber) {

        //telephoneNumber FORMAT CHECK - IF BAD RETURN 400
        if (!telephoneNumber.matches("\\d{10}")) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Invalid phone number format. It must be exactly 10 digits.")
                    .build();
        }

        //SEARCH Request BY telephoneNumber GIVEN
        Request request = requestRepository.findByTelephoneNumber(telephoneNumber).getFirst();

        //IF NONE FOUND, RETURN 404
        if (request == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        //IF FOUND, RETURN REPRESENTATION IN JSON AND 200 CODE
        return Response.ok().entity(requestMapper.toRepresentation(request)).build();
    }

    //GET /requests/status/{status} - RETRIEVE Request BY status
    @GET
    @Path("/status/{status}")
    public Response findByStatus(@PathParam("status") String status) {

        //status FORMAT CHECK - IF BAD RETURN 400
        if (!status.matches("^[a-zA-Z]+$")) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Invalid status format. Must contain only letters (A-Z or a-z).")
                    .build();
        }

        try {
            //CONVERT STRING TO ENUM
            Status statusEnum = Status.valueOf(status.toUpperCase());

            //SEARCH Request BY status NAME
            Request request = requestRepository.findByStatus(statusEnum).getFirst();

            //IF NONE FOUND, RETURN 404
            if (request == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            //IF FOUND, RETURN REPRESENTATION IN JSON AND 200 CODE
            return Response.ok().entity(requestMapper.toRepresentation(request)).build();

        } catch (IllegalArgumentException e) {

            //IF status NAME IS NOT IN ENUMERATED VALUES, RETURN 400 AND ARRAY WITH AVAILABLE VALUES
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Invalid status value. Allowed values: " + Arrays.toString(Status.values()))
                    .build();
        }
    }

    //PUT /requests/{requestId}/updateStatus/{status} - UPDATE Request status
    @PUT
    @Transactional
    @Path("/{requestId}/updateStatus/{status}")
    public Response updateStatus(@PathParam("requestId") Integer requestId, @PathParam("status") String status) {

        Status statusEnum = Status.valueOf(status.toUpperCase());
        Request updatedRequest = requestRepository.findById(requestId);

        if (updatedRequest == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Request not found").build();
        }

        switch (statusEnum) {
            case Status.ACTIVE -> updatedRequest.accept();
            case Status.REJECTED -> updatedRequest.reject();
            case Status.CLOSED -> updatedRequest.close();
            default -> {
                return Response.status(Response.Status.BAD_REQUEST).entity("Invalid status transition").build();
            }
        }

        requestRepository.getEntityManager().merge(updatedRequest);

        return Response.noContent().build();
    }

    //PUT /requests/assignTechnician/{requestId} - ASSIGN THE BEST AVAILABLE Technician TO Request AND UPDATE status ACCORDINGLY
    @PUT
    @Path("/assignTechnician/{requestId}")
    @Transactional
    public Response findAvailableTechnician(@PathParam("requestId") Integer requestId) {

        Request request = requestRepository.search(requestId);
        if(request == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        List<Technician> technicianList = technicianRepository.findBySpecialty(request.getRequestCategory().getSpecialty().getId());
        List<Request> assignedRequests = requestRepository.assignedRequests();
        List<Integer> counters = new ArrayList<>();

        for(Technician technician : technicianList) {
            Integer activeRequests = 0;
            for(Request assignedRequest : assignedRequests) {
                if(assignedRequest.getTechnician().getId().equals(technician.getId())) {
                    activeRequests++;
                }
            }
            counters.add(activeRequests);
        }

        int min = 50;
        Technician selectedTechnician = null;
        int index = 0;
        for (int i = 0; i < technicianList.size(); i++) {
            if (counters.get(i) <= min) {
                min = counters.get(i);
                index = i;
            }
        }
        selectedTechnician = technicianList.get(index);

        request.assign(selectedTechnician);
        requestRepository.getEntityManager().merge(request);

        return Response.ok().build();
    }

    //PUT /requests/{requestId}/addAction/{actionId} - ADD Action TO Request
    @PUT
    @Path("{requestId}/addAction/{actionId}")
    @Transactional
    public Response addAction(@PathParam("requestId") Integer requestId, @PathParam("actionId") Integer actionId) {
        //FIND Request TO BE ASSIGNED AN Action
        Request request = requestRepository.findById(requestId);
        if(request == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        //FIND Action
        Action action = actionRepository.findById(actionId);
        if(action == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        //PREVENT DUPLICATE ASSIGNMENT (ENSURE Action IS NOT ALREADY LINKED TO ANOTHER Request)
        if (request.getActions().contains(action)) {
            return Response.status(Response.Status.CONFLICT).entity("Action already assigned to this request.").build();
        }

        request.addAction(action);
        requestRepository.persist(request); //ENSURE IT'S PERSISTED
        requestRepository.getEntityManager().flush(); //FORCE COMMIT
        requestRepository.getEntityManager().clear(); //CLEAR SESSION CACHE

        return Response.ok().build();
    }

    //POST /requests - CREATE A NEW Request
    @POST
    @Transactional
    public Response createRequest(RequestRepresentation requestRepresentation) {
        //VALIDATE INPUT
        if (requestRepresentation.telephoneNumber == null || requestRepresentation.problemDescription == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Missing required fields.").build();
        }

        //VALIDATE telephoneNumber FORMAT (MUST BE EXACTLY 10 DIGITS)
        if (!requestRepresentation.telephoneNumber.matches("\\d{10}")) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid phone number format. It must be exactly 10 digits.").build();
        }

        //FETCH RELATED ENTITIES
        RequestCategory category = requestCategoryRepository.findById(requestRepresentation.requestCategory.id);
        Customer customer = customerRepository.findById(requestRepresentation.customer.id);
        CustomerSupport support = customerSupportRepository.findById(requestRepresentation.customerSupport.id);

        if (category == null || customer == null || support == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Category, Customer, or Support not found.").build();
        }

        //CONVERT REPRESENTATION TO ENTITY
        Request newRequest = requestMapper.toModel(requestRepresentation);

        //SET FETCHED RELATIONSHIPS
        newRequest.setRequestCategory(category);
        newRequest.setCustomer(customer);
        newRequest.setCustomerSupport(support);

        requestRepository.persist(newRequest);
        URI location = UriBuilder.fromResource(RequestResource.class).path("/{id}").build(newRequest.getId());

        return Response.created(location).entity(requestMapper.toRepresentation(newRequest)).build();
    }

}
