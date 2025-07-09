package com.example.helpdesk.resource;

import com.example.helpdesk.Fixture;
import com.example.helpdesk.IntegrationBase;
import com.example.helpdesk.domain.Request;
import com.example.helpdesk.domain.Status;
import com.example.helpdesk.persistence.RequestRepository;
import com.example.helpdesk.representation.*;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.containsString;

@QuarkusTest
public class RequestResourceTest extends IntegrationBase {

    @Inject
    RequestRepository requestRepository;

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
        //VALID telephoneNumber (SHOULD RETURN 200 OK)
        RequestRepresentation r = when().get(Fixture.API_ROOT + HelpdeskUri.REQUESTS +"/phone/" + Fixture.Requests.PHONE_NUMBER)
                .then()
                .statusCode(200)
                .extract().as(RequestRepresentation.class);
        Assertions.assertEquals(Fixture.Requests.PHONE_NUMBER, r.telephoneNumber);
    }

    @Test
    public void findByInvalidPhoneNumber_Length() {
        //INVALID telephoneNumber: TOO SHORT (SHOULD RETURN 404 OR 400)
        when()
                .get(Fixture.API_ROOT + HelpdeskUri.REQUESTS + "/phone/12345")
                .then()
                .statusCode(400); // Expecting Bad Request
    }

    @Test
    public void findByInvalidPhoneNumber_Letters() {
        //INVALID telephoneNumber: CONTAINS LETTERS (SHOULD RETURN 400)
        when()
                .get(Fixture.API_ROOT + HelpdeskUri.REQUESTS + "/phone/12345ABC90")
                .then()
                .statusCode(400);
    }

    @Test
    public void findByInvalidPhoneNumber_Symbols() {
        //INVALID telephoneNumber: CONTAINS SYMBOLS (SHOULD RETURN 400)
        when()
                .get(Fixture.API_ROOT + HelpdeskUri.REQUESTS + "/phone/12345-789@")
                .then()
                .statusCode(400);
    }
    //------------------------------------------------------------------------------------------

    //----------------------------------- STATUS TESTS -----------------------------------------
    @Test
    public void findByValidStatus() {
        //VALID status (SHOULD RETURN 200 OK)
        RequestRepresentation r = when()
                .get(Fixture.API_ROOT + HelpdeskUri.REQUESTS + "/status/"+Fixture.Requests.STATUS)
                .then()
                .statusCode(200)
                .extract().as(RequestRepresentation.class);

        Assertions.assertEquals("ACTIVE", r.status.toString());
    }

    @Test
    public void findByInvalidStatus_NonAlphabetic() {
        //INVALID status CONTAINING NUMBERS (SHOULD RETURN 400 BAD REQUEST)
        when()
                .get(Fixture.API_ROOT + HelpdeskUri.REQUESTS + "/status/ACTIVE123")
                .then()
                .statusCode(400);
    }

    @Test
    public void findByInvalidStatus_SpecialCharacters() {
        //INVALID status CONTAINING SPECIAL CHARACTERS (SHOULD RETURN 400 BAD REQUEST)
        when()
                .get(Fixture.API_ROOT + HelpdeskUri.REQUESTS + "/status/ACTIVE!")
                .then()
                .statusCode(400);
    }

    @Test
    public void findByNonExistentStatus() {
        //NON-EXISTENT status (SHOULD RETURN 404 NOT FOUND)
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
                .then().statusCode(204);

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

        Request request = requestRepository.findById(6000);
        Assertions.assertNull(request.getTechnician());
        Assertions.assertEquals(Status.ACTIVE, request.getStatus());

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
    public void testAddActionToRequest(){
        int requestId = 6000;
        int actionId = 7001;
        String uri = Fixture.API_ROOT + HelpdeskUri.REQUESTS +"/";

        Request request = requestRepository.search(6000);
        Assertions.assertEquals(0, request.getActions().size());

        //1. SUCCESSFULLY ADD AN Action TO A Request
        //ASSIGN A Technician FIRST
        given()
                .contentType(ContentType.JSON)
                .when().put(uri+"assignTechnician/6000")
                .then().statusCode(200);

        //ASSIGN Action
        given()
                .contentType(ContentType.JSON)
                .when()
                .put("/requests/" + requestId + "/addAction/" + actionId)
                .then()
                .statusCode(200);

        //TEST CLOSING OF CASE
        given()
                .contentType(ContentType.JSON)
                .when().put("http://localhost:8081/requests/6000/updateStatus/CLOSED")
                .then().statusCode(204);

        //2. TRY ADDING THE SAME Action AGAIN → SHOULD RETURN 409 CONFLICT
        given()
                .contentType(ContentType.JSON)
                .when()
                .put("/requests/" + requestId + "/addAction/" + actionId)
                .then()
                .statusCode(409);

        //3. NON-EXISTENT Request ID → SHOULD RETURN 404
        int invalidRequestId = 9999;
        given()
                .contentType(ContentType.JSON)
                .when()
                .put("/requests/" + invalidRequestId + "/addAction/" + actionId)
                .then()
                .statusCode(404);

        //4. NON-EXISTENT Action ID → SHOULD RETURN 404
        int invalidActionId = 8888;
        given()
                .contentType(ContentType.JSON)
                .when()
                .put("/requests/" + requestId + "/addAction/" + invalidActionId)
                .then()
                .statusCode(404);
    }

    @Test
    public void testCreateRequest() {
        //CREATE Request REPRESENTATION OBJECT
        RequestRepresentation requestRepresentation = new RequestRepresentation();
        requestRepresentation.telephoneNumber = "1234567890";
        requestRepresentation.problemDescription = "Internet not working";

        //RELATED ENTITIES
        requestRepresentation.requestCategory = new RequestCategoryRepresentation();
        requestRepresentation.requestCategory.id = 2001;    //MUST EXIST IN DB

        requestRepresentation.customer = new CustomerRepresentation();
        requestRepresentation.customer.id = 5001;           //MUST EXIST IN DB

        requestRepresentation.customerSupport = new CustomerSupportRepresentation();
        requestRepresentation.customerSupport.id = 3001;    //MUST EXIST IN DB

        //PERFORM THE Request AND VALIDATE RESPONSE
        Integer requestId = given()
                .contentType(ContentType.JSON)
                .body(requestRepresentation)
                .when()
                .post("/requests")
                .then()
                .statusCode(201)
                .contentType(ContentType.JSON)
                .extract().path("id");

        //VERIFY THE Request IS PERSISTED CORRECTLY
        Request createdRequest = requestRepository.findById(requestId);
        Assertions.assertNotNull(createdRequest);
        Assertions.assertEquals("1234567890", createdRequest.getTelephoneNumber());
        Assertions.assertEquals("Internet not working", createdRequest.getProblemDescription());
        Assertions.assertEquals(2001, createdRequest.getRequestCategory().getId());
        Assertions.assertEquals(5001, createdRequest.getCustomer().getId());
        Assertions.assertEquals(3001, createdRequest.getCustomerSupport().getId());

        //FAILED CASES

        //1. ATTEMPT TO CREATE A Request WITH A MISSING REQUIRED FIELD (E.G., NO telephoneNumber)
                RequestRepresentation invalidRequest1 = new RequestRepresentation();
                invalidRequest1.problemDescription = "Missing phone number";

                given()
                        .contentType(ContentType.JSON)
                        .body(invalidRequest1)
                        .when()
                        .post("/requests")
                        .then()
                        .statusCode(400)        //EXPECTING BAD REQUEST
                        .body(containsString("Missing required fields."));

        //2. ATTEMPT TO CREATE A Request WITH A NON-EXISTENT Category/CustomerSupport
                RequestRepresentation invalidRequest2 = new RequestRepresentation();
                invalidRequest2.telephoneNumber = "1234567890";
                invalidRequest2.problemDescription = "Valid description";
                invalidRequest2.requestCategory = new RequestCategoryRepresentation();
                invalidRequest2.requestCategory.id = 9999;      //NON-EXISTENT ID
                invalidRequest2.customer = new CustomerRepresentation();
                invalidRequest2.customer.id = 9999;             //NON-EXISTENT ID
                invalidRequest2.customerSupport = new CustomerSupportRepresentation();
                invalidRequest2.customerSupport.id = 9999;      //NON-EXISTENT ID

                given()
                        .contentType(ContentType.JSON)
                        .body(invalidRequest2)
                        .when()
                        .post("/requests")
                        .then()
                        .statusCode(404)    //EXPECTING NOT FOUND
                        .body(containsString("Category, Customer, or Support not found."));

        //3. ATTEMPT TO CREATE A Request WITH AN INVALID telephoneNumber FORMAT
                RequestCategoryRepresentation validCategory = new RequestCategoryRepresentation();
                validCategory.id = 2001;

                CustomerRepresentation validCustomer = new CustomerRepresentation();
                validCustomer.id = 5001;

                CustomerSupportRepresentation validSupport = new CustomerSupportRepresentation();
                validSupport.id = 3001;

                RequestRepresentation invalidRequest3 = new RequestRepresentation();
                invalidRequest3.telephoneNumber = "123";    //TOO SHORT
                invalidRequest3.problemDescription = "Valid description";
                invalidRequest3.requestCategory = validCategory;
                invalidRequest3.customer = validCustomer;
                invalidRequest3.customerSupport = validSupport;

                given()
                        .contentType(ContentType.JSON)
                        .body(invalidRequest3)
                        .when()
                        .post("/requests")
                        .then()
                        .statusCode(400)    //EXPECTING BAD REQUEST
                        .body(containsString("Invalid phone number format"));
            }

}
