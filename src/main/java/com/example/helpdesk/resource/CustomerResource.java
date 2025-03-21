package com.example.helpdesk.resource;

import com.example.helpdesk.domain.Customer;
import com.example.helpdesk.persistence.CustomerRepository;
import com.example.helpdesk.representation.CustomerMapper;
import com.example.helpdesk.representation.CustomerRepresentation;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

import java.net.URI;
import java.util.List;

import static com.example.helpdesk.resource.HelpdeskUri.CUSTOMERS;

@Path(CUSTOMERS)
@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CustomerResource {

    @Inject
    CustomerRepository customerRepository;

    @Inject
    CustomerMapper customerMapper;

    // GET CUSTOMER BY ID
    @GET
    @Path("/{id}")
    public Response getCustomerById(@PathParam("id") Integer id) {
        Customer customer = customerRepository.findById(id);
        if (customer == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Customer not found.").build();
        }
        return Response.ok(customerMapper.toRepresentation(customer)).build();
    }

    // GET CUSTOMER BY CUSTOMER CODE
    @GET
    @Path("/code/{customerCode}")
    public Response getCustomerByCode(@PathParam("customerCode") String customerCode) {
        Customer customer = customerRepository.findByCustomerCode(customerCode).getFirst();
        if (customer == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Customer not found.").build();
        }
        return Response.ok(customerMapper.toRepresentation(customer)).build();
    }

    // GET CUSTOMER BY EMAIL
    @GET
    @Path("/email/{email}")
    public Response getCustomerByEmail(@PathParam("email") String email) {
        List<Customer> customers = customerRepository.findByEmailAddress(email);

        if (customers.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("Customer not found.").build();
        }

        return Response.ok(customerMapper.toRepresentation(customers.get(0))).build();
    }

    @GET
    @Path("/phone/{telephoneNumber}")
    public Response getCustomerByPhone(@PathParam("telephoneNumber") String telephoneNumber) {
        List<Customer> customers = customerRepository.findByTelephoneNumber(telephoneNumber);

        if (customers.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("Customer not found.").build();
        }

        return Response.ok(customerMapper.toRepresentation(customers.get(0))).build();
    }


    // CREATE A NEW CUSTOMER
    @POST
    @Transactional
    public Response createCustomer(CustomerRepresentation customerRepresentation) {
        // Validate input
        if (customerRepresentation.customerCode == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Missing customer code.").build();
        }

        // Ensure required fields in personal info are present
        if (customerRepresentation.firstName == null || customerRepresentation.lastName == null ||
                customerRepresentation.telephoneNumber == null ||
                customerRepresentation.birthdate == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Personal info is incomplete.").build();
        }

        // Check if phone number is valid (10 digits)
        if (!customerRepresentation.telephoneNumber.matches("\\d{10}")) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid phone number format.").build();
        }

        // Convert representation to entity
        Customer newCustomer = customerMapper.toModel(customerRepresentation);

        // Persist the new customer
        customerRepository.persist(newCustomer);
        URI location = UriBuilder.fromResource(CustomerResource.class).path("/{id}").build(newCustomer.getId());

        return Response.created(location).entity(customerMapper.toRepresentation(newCustomer)).build();
    }
}