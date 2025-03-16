package com.example.helpdesk.resource;

import com.example.helpdesk.Fixture;
import com.example.helpdesk.representation.TechnicianRepresentation;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.when;

@QuarkusTest
public class TechnicianResourceTest {
    @Test
    public void find() {
        TechnicianRepresentation t = when().get(Fixture.API_ROOT + HelpdeskUri.TECHNICIANS +"/" + Fixture.Technicians.TECHNICIAN_ID)
                .then()
                .statusCode(200)
                .extract().as(TechnicianRepresentation.class);
        Assertions.assertEquals(Fixture.Technicians.TECHNICIAN_ID, t.id);
    }
}