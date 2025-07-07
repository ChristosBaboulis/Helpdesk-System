package com.example.helpdesk.resource;

import com.example.helpdesk.IntegrationBase;
import com.example.helpdesk.auth.AuthRequest;
import com.example.helpdesk.auth.AuthResponse;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class LoginResourceTest extends IntegrationBase {

    @Test
    public void testSuccessfulLoginReturnsToken() {
        AuthRequest request = new AuthRequest("csupport1", "securePass123");

        AuthResponse response = given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200)
                .extract()
                .as(AuthResponse.class);

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getToken());
        Assertions.assertFalse(response.getToken().isBlank());
    }

    @Test
    public void testInvalidPasswordReturns401() {
        AuthRequest request = new AuthRequest("csupport1", "wrongPassword");

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(401);
    }

    @Test
    public void testUnknownUsernameReturns401() {
        AuthRequest request = new AuthRequest("unknownUser", "password");

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(401);
    }
}
