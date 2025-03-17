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
import io.quarkus.test.TestTransaction;
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
    @TestTransaction
    public void updateStatus(){
        requestRepository.getEntityManager().clear();
        Status beforeStatus = requestRepository.search(6000).getStatus();
        Assertions.assertEquals(Status.ACTIVE, beforeStatus);

        given()
                .contentType(ContentType.JSON)
                .when().put("http://localhost:8081/requests/6000/updateStatus/REJECTED")
                .then().statusCode(200);

        RequestRepresentation r = when().get(Fixture.API_ROOT + HelpdeskUri.REQUESTS +"/" + Fixture.Requests.UML_USER_GUIDE_ID)
                .then()
                .statusCode(200)
                .extract().as(RequestRepresentation.class);
        Assertions.assertEquals(Status.REJECTED, r.status);
    }

    @Test
    @TestTransaction
    public void testAssignTechnician(){
        String uri = Fixture.API_ROOT + HelpdeskUri.REQUESTS +"/";

        given()
                .contentType(ContentType.JSON)
                .when().put(uri+"assignTechnician/6000")
                .then().statusCode(200);

        RequestRepresentation r = when().get(Fixture.API_ROOT + HelpdeskUri.REQUESTS +"/" + Fixture.Requests.UML_USER_GUIDE_ID)
                .then()
                .statusCode(200)
                .extract().as(RequestRepresentation.class);
        Assertions.assertEquals(Status.RESOLVING, r.status);
        Assertions.assertNotNull(r.technician);
        Assertions.assertEquals(4002, r.technician.id);
    }

    @Test
    @TestTransaction
    public void testAddActionToRequest() throws InterruptedException {
        int requestId = 6000;
        int actionId = 7001;

        Request request = requestRepository.search(6000);
        Assertions.assertEquals(0, request.getActions().size());

        // 1. Successfully add an action to a request
        given()
                .contentType(ContentType.JSON)
                .when()
                .put("/requests/" + requestId + "/addAction/" + actionId)
                .then()
                .statusCode(200);

        // 2. Try adding the same action again → should return 409 Conflict
        given()
                .contentType(ContentType.JSON)
                .when()
                .put("/requests/" + requestId + "/addAction/" + actionId)
                .then()
                .statusCode(409);

        // 3. Non-existent request ID → should return 404
        int invalidRequestId = 9999;
        given()
                .contentType(ContentType.JSON)
                .when()
                .put("/requests/" + invalidRequestId + "/addAction/" + actionId)
                .then()
                .statusCode(404);

        // 4. Non-existent action ID → should return 404
        int invalidActionId = 8888;
        given()
                .contentType(ContentType.JSON)
                .when()
                .put("/requests/" + requestId + "/addAction/" + invalidActionId)
                .then()
                .statusCode(404);
    }
}
