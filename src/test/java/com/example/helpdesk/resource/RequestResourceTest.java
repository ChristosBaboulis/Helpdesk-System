package com.example.helpdesk.resource;

import com.example.helpdesk.Fixture;
import com.example.helpdesk.IntegrationBase;
import com.example.helpdesk.domain.Request;
import com.example.helpdesk.representation.RequestRepresentation;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.when;

@QuarkusTest
public class RequestResourceTest extends IntegrationBase {
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
}
