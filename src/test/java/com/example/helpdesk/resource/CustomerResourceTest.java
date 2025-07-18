package com.example.helpdesk.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
public class CustomerResourceTest {

    //1. TEST RETRIEVING A Customer BY ID
    @Test
    public void testGetCustomerById_Success() {
        given()
                .when()
                .get("/customers/5001")  //ASSUME THIS Customer EXISTS
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(5001))
                .body("customerCode", equalTo("CUST005"));
    }

    //2. TEST RETRIEVING A Customer BY customerCode
    @Test
    public void testGetCustomerByCode_Success() {
        given()
                .when()
                .get("/customers/code/CUST005")  //ASSUME THIS Customer EXISTS
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("customerCode", equalTo("CUST005"))
                .body("firstName", equalTo("Liam"))
                .body("lastName", equalTo("Miller"));
    }

    //3. TEST RETRIEVING A Customer BY email
    @Test
    public void testGetCustomerByEmail_Success() {
        given()
                .when()
                .get("/customers/email/liam.miller@example.com")  //ASSUME THIS Customer EXISTS
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("emailAddress", equalTo("liam.miller@example.com"))
                .body("firstName", equalTo("Liam"))
                .body("lastName", equalTo("Miller"));
    }

    //4. TEST RETRIEVING A Customer BY phone
    @Test
    public void testGetCustomerByPhone_Success() {
        given()
                .when()
                .get("/customers/phone/7123456789")  //ASSUME THIS telephoneNumber EXISTS IN THE TEST DB
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("telephoneNumber", equalTo("7123456789"))
                .body("firstName", equalTo("Liam"))
                .body("lastName", equalTo("Miller"));
    }

    @Test
    public void testCreateCustomer_Success() {
        //PREPARE VALID Customer DATA
        String jsonBody = "{"
                + "\"customerCode\": \"CUST123\","
                + "\"firstName\": \"John\","
                + "\"lastName\": \"Doe\","
                + "\"telephoneNumber\": \"1234567890\","
                + "\"emailAddress\": \"johndoe@example.com\","
                + "\"birthdate\": \"1990-05-15\","
                + "\"address\": {"
                + "    \"street\": \"Main Street\","
                + "    \"number\": \"12A\","
                + "    \"city\": \"New York\","
                + "    \"zipCode\": \"10001\""
                + "  }"
                + "}";

        //SEND POST Request AND VALIDATE RESPONSE
        ValidatableResponse response = given()
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .when()
                .post("/customers")
                .then()
                .statusCode(201) //EXPECTING CREATED
                .contentType(ContentType.JSON)
                .body("customerCode", equalTo("CUST123"))
                .body("firstName", equalTo("John"))
                .body("lastName", equalTo("Doe"))
                .body("telephoneNumber", equalTo("1234567890"))
                .body("emailAddress", equalTo("johndoe@example.com"))
                .body("birthdate", equalTo("1990-05-15"))
                .body("address.street", equalTo("Main Street"))
                .body("address.number", equalTo("12A"))
                .body("address.city", equalTo("New York"))
                .body("address.zipCode", equalTo("10001"));
    }

    @Test
    public void testCreateCustomer_Fail_MissingCustomerCode() {
        String jsonBody = "{"
                + "\"firstName\": \"John\","
                + "\"lastName\": \"Doe\","
                + "\"telephoneNumber\": \"1234567890\","
                + "\"emailAddress\": \"johndoe@example.com\","
                + "\"birthdate\": \"1990-05-15\","
                + "\"address\": {"
                + "    \"street\": \"Main Street\","
                + "    \"number\": \"12A\","
                + "    \"city\": \"New York\","
                + "    \"zipCode\": \"10001\""
                + "  }"
                + "}";

        given()
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .when()
                .post("/customers")
                .then()
                .statusCode(400)
                .body(containsString("Missing customer code."));
    }

    @Test
    public void testCreateCustomer_Fail_MissingPersonalInfo() {
        String jsonBody = "{"
                + "\"customerCode\": \"CUST123\","
                + "\"emailAddress\": \"johndoe@example.com\","
                + "\"address\": {"
                + "    \"street\": \"Main Street\","
                + "    \"number\": \"12A\","
                + "    \"city\": \"New York\","
                + "    \"zipCode\": \"10001\""
                + "  }"
                + "}";

        given()
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .when()
                .post("/customers")
                .then()
                .statusCode(400)
                .body(containsString("Personal info is incomplete."));
    }

    @Test
    public void testCreateCustomer_Fail_InvalidPhoneNumber() {
        String jsonBody = "{"
                + "\"customerCode\": \"CUST123\","
                + "\"firstName\": \"John\","
                + "\"lastName\": \"Doe\","
                + "\"telephoneNumber\": \"123\","
                + "\"emailAddress\": \"johndoe@example.com\","
                + "\"birthdate\": \"1990-05-15\","
                + "\"address\": {"
                + "    \"street\": \"Main Street\","
                + "    \"number\": \"12A\","
                + "    \"city\": \"New York\","
                + "    \"zipCode\": \"10001\""
                + "  }"
                + "}";

        given()
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .when()
                .post("/customers")
                .then()
                .statusCode(400)
                .body(containsString("Invalid phone number format"));
    }

}
