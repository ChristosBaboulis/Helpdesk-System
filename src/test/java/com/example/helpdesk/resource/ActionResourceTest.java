package com.example.helpdesk.resource;

import com.example.helpdesk.domain.Action;
import com.example.helpdesk.representation.CommunicationActionRepresentation;
import com.example.helpdesk.representation.TechnicalActionRepresentation;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
public class ActionResourceTest {

    //TODO: UPDATE THESE TESTS WITH LOGIN PROCESS
//    @Test
//    public void testCreateCommunicationAction() {
//        String jsonBody = "{ " +
//                "\"title\": \"Call Support\", " +
//                "\"description\": \"Customer called for help\", " +
//                "\"callDuration\": 15.5 " +
//                "}";
//
//        given()
//                .contentType(ContentType.JSON)
//                .body(jsonBody)
//                .when()
//                .post("/actions/communication")
//                .then()
//                .statusCode(201)
//                .contentType(ContentType.JSON)
//                .body("title", equalTo("Call Support"))
//                .body("description", equalTo("Customer called for help"))
//                .body("callDuration", equalTo(15.5f));
//    }

//    @Test
//    public void testCreateTechnicalAction() {
//        String jsonBody = "{ " +
//                "\"title\": \"Fix Network Issue\", " +
//                "\"description\": \"Resolved network outage\" " +
//                "}";
//
//        given()
//                .contentType(ContentType.JSON)
//                .body(jsonBody)
//                .when()
//                .post("/actions/technical")
//                .then()
//                .statusCode(201)
//                .contentType(ContentType.JSON)
//                .body("title", equalTo("Fix Network Issue"))
//                .body("description", equalTo("Resolved network outage"));
//    }

    @Test
    public void testGetCommunicationActionById() {
        given()
                .when()
                .get("/actions/communication/7001") // Updated ID
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("title", equalTo("Call Support"))
                .body("description", equalTo("Customer called for help regarding internet issues"))
                .body("callDuration", equalTo(15.5f)); // Float comparison
    }

    @Test
    public void testGetTechnicalActionById() {
        given()
                .when()
                .get("/actions/technical/7002") // Updated ID
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("title", equalTo("Fix Router Issue"))
                .body("description", equalTo("Replaced faulty router with a new one"));
    }

    @Test
    public void testGetCommunicationActionsByDate() {
        String response = given()
                .when()
                .get("/actions/communication/by-date/2025-03-16")
                .then()
                .statusCode(200) // Stop here if the status code is incorrect
                .extract()
                .asString(); // Extract response as a string

        // Parse JSON response
        List<CommunicationActionRepresentation> actions =
                io.restassured.path.json.JsonPath.from(response).getList("", CommunicationActionRepresentation.class);

        // Assertions
        Assertions.assertFalse(actions.isEmpty(), "List of communication actions should not be empty");
        Assertions.assertEquals("Call Support", actions.get(0).title, "Incorrect title");
        Assertions.assertEquals("Customer called for help regarding internet issues", actions.get(0).description, "Incorrect description");
        Assertions.assertEquals(15.5, actions.get(0).callDuration, 0.01, "Incorrect call duration");
    }

    @Test
    public void testGetTechnicalActionsByDate() {
        String response = given()
                .when()
                .get("/actions/technical/by-date/2025-03-16")
                .then()
                .statusCode(200) // Stop here if the status code is incorrect
                .extract()
                .asString(); // Extract response as a string

        // Parse JSON response
        List<TechnicalActionRepresentation> actions =
                io.restassured.path.json.JsonPath.from(response).getList("", TechnicalActionRepresentation.class);

        // Assertions
        Assertions.assertFalse(actions.isEmpty(), "List of technical actions should not be empty");
        Assertions.assertEquals("Fix Router Issue", actions.get(0).title, "Incorrect title");
        Assertions.assertEquals("Replaced faulty router with a new one", actions.get(0).description, "Incorrect description");
    }

}
