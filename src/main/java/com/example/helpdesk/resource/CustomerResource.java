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

    //GET /customers/{id} - RETRIEVE Customer BY ID
    @GET
    @Path("/{id}")
    public Response getCustomerById(@PathParam("id") Integer id) {
        Customer customer = customerRepository.findById(id);
        if (customer == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Customer not found.").build();
        }
        return Response.ok(customerMapper.toRepresentation(customer)).build();
    }

    //GET /customers/code/{customerCode} - RETRIEVE Customer BY customerCode
    @GET
    @Path("/code/{customerCode}")
    public Response getCustomerByCode(@PathParam("customerCode") String customerCode) {
        Customer customer = customerRepository.findByCustomerCode(customerCode).getFirst();
        if (customer == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Customer not found.").build();
        }
        return Response.ok(customerMapper.toRepresentation(customer)).build();
    }

    //GET /customers/email/{email} - RETRIEVE Customer BY email
    @GET
    @Path("/email/{email}")
    public Response getCustomerByEmail(@PathParam("email") String email) {
        List<Customer> customers = customerRepository.findByEmailAddress(email);

        if (customers.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("Customer not found.").build();
        }

        return Response.ok(customerMapper.toRepresentation(customers.getFirst())).build();
    }

    //GET /customers/phone/{telephoneNumber} - RETRIEVE Customer BY telephoneNumber
    @GET
    @Path("/phone/{telephoneNumber}")
    public Response getCustomerByPhone(@PathParam("telephoneNumber") String telephoneNumber) {
        List<Customer> customers = customerRepository.findByTelephoneNumber(telephoneNumber);

        if (customers.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("Customer not found.").build();
        }

        return Response.ok(customerMapper.toRepresentation(customers.getFirst())).build();
    }

    //POST /customers - CREATE A NEW Customer
    @POST
    @Transactional
    public Response createCustomer(CustomerRepresentation customerRepresentation) {
        //VALIDATE INPUT
        if (customerRepresentation.customerCode == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Missing customer code.").build();
        }

        //ENSURE REQUIRED FIELDS IN PersonalInfo ARE PRESENT
        if (customerRepresentation.firstName == null || customerRepresentation.lastName == null ||
                customerRepresentation.telephoneNumber == null ||
                customerRepresentation.birthdate == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Personal info is incomplete.").build();
        }

        //CHECK IF telephoneNumber IS VALID (10 DIGITS)
        if (!customerRepresentation.telephoneNumber.matches("\\d{10}")) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid phone number format.").build();
        }

        //CONVERT REPRESENTATION TO ENTITY
        Customer newCustomer = customerMapper.toModel(customerRepresentation);

        //PERSIST THE NEW Customer
        customerRepository.persist(newCustomer);
        URI location = UriBuilder.fromResource(CustomerResource.class).path("/{id}").build(newCustomer.getId());

        return Response.created(location).entity(customerMapper.toRepresentation(newCustomer)).build();
    }
}
