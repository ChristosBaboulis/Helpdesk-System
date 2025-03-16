package com.example.helpdesk.resource;

import com.example.helpdesk.domain.Customer;
import com.example.helpdesk.persistence.CustomerRepository;
import com.example.helpdesk.representation.CustomerMapper;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
        import jakarta.ws.rs.core.Response;

import static com.example.helpdesk.resource.HelpdeskUri.CUSTOMERS;

@Path(CUSTOMERS)
@RequestScoped
public class CustomerResource {

    @Inject
    CustomerRepository customerRepository;

    @Inject
    CustomerMapper customerMapper;

    // POST /customers - Δημιουργεί έναν νέο πελάτη
    @POST
    public Response createCustomer(Customer customer) {
        customerRepository.persist(customer);
        return Response.status(Response.Status.CREATED).entity(customerMapper.toRepresentation(customer)).build();
    }

    // PUT /customers/{id} - Ενημερώνει τα προσωπικά στοιχεία ενός πελάτη
    @PUT
    @Path("{customerId:[0-9]*}")
    public Response updateCustomer(@PathParam("customerId") Integer customerId, Customer updatedCustomer) {
        Customer customer = customerRepository.findById(customerId);
        if (customer == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        //customer.setName(updatedCustomer.getName());
        //customer.setEmail(updatedCustomer.getEmail());
        //customer.setTelephoneNumber(updatedCustomer.getTelephoneNumber());
        // Ενημέρωση άλλων πεδίων...
        return Response.ok().entity(customerMapper.toRepresentation(customer)).build();
    }
}