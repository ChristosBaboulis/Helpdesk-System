package com.example.helpdesk.resource;

import com.example.helpdesk.Fixture;
import com.example.helpdesk.IntegrationBase;
import com.example.helpdesk.domain.Request;
import com.example.helpdesk.domain.Status;
import com.example.helpdesk.persistence.CustomerRepository;
import com.example.helpdesk.persistence.CustomerSupportRepository;
import com.example.helpdesk.persistence.RequestCategoryRepository;
import com.example.helpdesk.persistence.RequestRepository;
import com.example.helpdesk.representation.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

@QuarkusTest
public class RequestResourceTest extends IntegrationBase {
    @Inject
    RequestCategoryRepository requestCategoryRepository;
    @Inject
    RequestCategoryMapper requestCategoryMapper;
    @Inject
    CustomerRepository customerRepository;
    @Inject
    CustomerMapper customerMapper;
    @Inject
    CustomerSupportRepository customerSupportRepository;
    @Inject
    CustomerSupportMapper customerSupportMapper;
    @Inject
    RequestRepository requestRepository;
    @Inject
    RequestMapper requestMapper;
    @Inject
    RequestResource requestResource;

    @Test
    public void find() {
        RequestRepresentation r = when().get(Fixture.API_ROOT + HelpdeskUri.REQUESTS +"/" + Fixture.Requests.UML_USER_GUIDE_ID)
                .then()
                .statusCode(200)
                .extract().as(RequestRepresentation.class);
        Assertions.assertEquals(Fixture.Requests.UML_USER_GUIDE_ID, r.id);
    }

    //----------------------------------- PHONE NUMBER TESTS -----------------------------------
    @Test
    public void findByValidPhoneNumber() {
        // Valid phone number (should return 200 OK)
        RequestRepresentation r = when().get(Fixture.API_ROOT + HelpdeskUri.REQUESTS +"/phone/" + Fixture.Requests.PHONE_NUMBER)
                .then()
                .statusCode(200)
                .extract().as(RequestRepresentation.class);
        Assertions.assertEquals(Fixture.Requests.PHONE_NUMBER, r.telephoneNumber);
    }

    @Test
    public void findByInvalidPhoneNumber_Length() {
        // Invalid phone number: Too short (should return 404 or 400)
        when()
                .get(Fixture.API_ROOT + HelpdeskUri.REQUESTS + "/phone/12345")
                .then()
                .statusCode(400); // Expecting Bad Request
    }

    @Test
    public void findByInvalidPhoneNumber_Letters() {
        // Invalid phone number: Contains letters (should return 400)
        when()
                .get(Fixture.API_ROOT + HelpdeskUri.REQUESTS + "/phone/12345ABC90")
                .then()
                .statusCode(400);
    }

    @Test
    public void findByInvalidPhoneNumber_Symbols() {
        // Invalid phone number: Contains symbols (should return 400)
        when()
                .get(Fixture.API_ROOT + HelpdeskUri.REQUESTS + "/phone/12345-789@")
                .then()
                .statusCode(400);
    }
    //------------------------------------------------------------------------------------------

    //----------------------------------- STATUS TESTS -----------------------------------------
    @Test
    public void findByValidStatus() {
        // Valid status (should return 200 OK)
        RequestRepresentation r = when()
                .get(Fixture.API_ROOT + HelpdeskUri.REQUESTS + "/status/"+Fixture.Requests.STATUS)
                .then()
                .statusCode(200)
                .extract().as(RequestRepresentation.class);

        Assertions.assertEquals("ACTIVE", r.status.toString());
    }

    @Test
    public void findByInvalidStatus_NonAlphabetic() {
        // Invalid status containing numbers (should return 400 Bad Request)
        when()
                .get(Fixture.API_ROOT + HelpdeskUri.REQUESTS + "/status/ACTIVE123")
                .then()
                .statusCode(400);
    }

    @Test
    public void findByInvalidStatus_SpecialCharacters() {
        // Invalid status containing special characters (should return 400 Bad Request)
        when()
                .get(Fixture.API_ROOT + HelpdeskUri.REQUESTS + "/status/ACTIVE!")
                .then()
                .statusCode(400);
    }

    @Test
    public void findByNonExistentStatus() {
        // Non-existent status (should return 404 Not Found)
        when()
                .get(Fixture.API_ROOT + HelpdeskUri.REQUESTS + "/status/UNKNOWN")
                .then()
                .statusCode(400);
    }
    //------------------------------------------------------------------------------------------

    @Test
    public void testCreateRequest(){
        // Create a new request representation
        RequestRepresentation requestRepresentation = new RequestRepresentation();
        requestRepresentation.telephoneNumber = "1234567890";
        requestRepresentation.problemDescription = "Internet connection issue.";
        requestRepresentation.requestCategory = requestCategoryMapper.toRepresentation(requestCategoryRepository.findById(2001)); // Add valid category
        requestRepresentation.customer = customerMapper.toRepresentation(customerRepository.findById(5001)); // Add valid customer
        requestRepresentation.customerSupport = customerSupportMapper.toRepresentation(customerSupportRepository.findById(3001)); // Add valid customer support

        Assertions.assertNotNull(requestRepresentation.requestCategory);
        Assertions.assertNotNull(requestRepresentation.customer);
        Assertions.assertNotNull(requestRepresentation.customerSupport);
        Assertions.assertNotNull(requestRepresentation.telephoneNumber);

        System.out.println(Fixture.API_ROOT + HelpdeskUri.REQUESTS);

        // Send POST request to create a new request
        RequestRepresentation createdRequest =
                given()
                        .contentType(ContentType.JSON)
                        .body(requestRepresentation)
                        .when()
                        .put(Fixture.API_ROOT + HelpdeskUri.REQUESTS)
                        .then()
                        .statusCode(201)
                        .extract().as(RequestRepresentation.class);

        // Assertions to verify correct creation
        Assertions.assertNotNull(createdRequest.id, "ID should be generated.");
        Assertions.assertEquals(requestRepresentation.telephoneNumber, createdRequest.telephoneNumber);
        Assertions.assertEquals(Status.ACTIVE, createdRequest.status, "New request should have ACTIVE status.");
        Assertions.assertEquals(LocalDate.now(), createdRequest.submissionDate, "Submission date should be today.");
    }

    @Test
    public void testCreateRequest_FailWhenIdProvided() {
        RequestRepresentation requestRepresentation = new RequestRepresentation();
        requestRepresentation.id = 999; // ID should not be provided
        requestRepresentation.telephoneNumber = "1234567890";
        requestRepresentation.problemDescription = "Internet connection issue.";
        requestRepresentation.requestCategory = requestCategoryMapper.toRepresentation(requestCategoryRepository.findById(2001));
        requestRepresentation.customer = customerMapper.toRepresentation(customerRepository.findById(5001));
        requestRepresentation.customerSupport = customerSupportMapper.toRepresentation(customerSupportRepository.findById(3001));

        given()
                .contentType(ContentType.JSON)
                .body(requestRepresentation)
                .when()
                .post("/requests")
                .then()
                .statusCode(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void testCreateRequest_FailWhenMissingFields() {
        RequestRepresentation requestRepresentation = new RequestRepresentation();
        requestRepresentation.problemDescription = "Internet connection issue.";
        requestRepresentation.requestCategory = requestCategoryMapper.toRepresentation(requestCategoryRepository.findById(2001));
        requestRepresentation.customer = customerMapper.toRepresentation(customerRepository.findById(5001));
        requestRepresentation.customerSupport = customerSupportMapper.toRepresentation(customerSupportRepository.findById(3001));

        given()
                .contentType(ContentType.JSON)
                .body(requestRepresentation)
                .when()
                .post("/requests")
                .then()
                .statusCode(Response.Status.BAD_REQUEST.getStatusCode()); // Fails due to missing telephoneNumber
    }

    @Test
    public void testCreateRequest_FailWhenCustomerNotFound() {
        RequestRepresentation requestRepresentation = new RequestRepresentation();
        requestRepresentation.telephoneNumber = "1234567890";
        requestRepresentation.problemDescription = "Internet connection issue.";
        requestRepresentation.requestCategory = requestCategoryMapper.toRepresentation(requestCategoryRepository.findById(2001));
        requestRepresentation.customer = customerMapper.toRepresentation(customerRepository.findById(99999)); // Invalid ID
        requestRepresentation.customerSupport = customerSupportMapper.toRepresentation(customerSupportRepository.findById(3001));

        given()
                .contentType(ContentType.JSON)
                .body(requestRepresentation)
                .when()
                .post("/requests")
                .then()
                .statusCode(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void testCreateRequest_FailWhenRequestCategoryNotFound() {
        RequestRepresentation requestRepresentation = new RequestRepresentation();
        requestRepresentation.telephoneNumber = "1234567890";
        requestRepresentation.problemDescription = "Internet connection issue.";
        requestRepresentation.requestCategory = requestCategoryMapper.toRepresentation(requestCategoryRepository.findById(99999)); // Invalid ID
        requestRepresentation.customer = customerMapper.toRepresentation(customerRepository.findById(5001));
        requestRepresentation.customerSupport = customerSupportMapper.toRepresentation(customerSupportRepository.findById(3001));

        given()
                .contentType(ContentType.JSON)
                .body(requestRepresentation)
                .when()
                .post("/requests")
                .then()
                .statusCode(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void testCreateRequest_FailWhenCustomerSupportNotFound() {
        RequestRepresentation requestRepresentation = new RequestRepresentation();
        requestRepresentation.telephoneNumber = "1234567890";
        requestRepresentation.problemDescription = "Internet connection issue.";
        requestRepresentation.requestCategory = requestCategoryMapper.toRepresentation(requestCategoryRepository.findById(2001)); // Invalid ID
        requestRepresentation.customer = customerMapper.toRepresentation(customerRepository.findById(5001));
        requestRepresentation.customerSupport = customerSupportMapper.toRepresentation(customerSupportRepository.findById(99999));

        given()
                .contentType(ContentType.JSON)
                .body(requestRepresentation)
                .when()
                .post("/requests")
                .then()
                .statusCode(Response.Status.BAD_REQUEST.getStatusCode());
    }

}
