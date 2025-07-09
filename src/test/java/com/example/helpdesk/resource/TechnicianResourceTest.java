package com.example.helpdesk.resource;

import com.example.helpdesk.Fixture;
import com.example.helpdesk.IntegrationBase;
import com.example.helpdesk.persistence.TechnicianRepository;
import com.example.helpdesk.representation.TechnicianRepresentation;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

@QuarkusTest
public class TechnicianResourceTest extends IntegrationBase {

    @Inject
    TechnicianRepository technicianRepository;

    @Test
    public void testFindById() {
        TechnicianRepresentation t = when().get(Fixture.API_ROOT + HelpdeskUri.TECHNICIANS +"/" + Fixture.Technicians.TECHNICIAN_ID)
                .then()
                .statusCode(200)
                .extract().as(TechnicianRepresentation.class);
        Assertions.assertEquals(Fixture.Technicians.TECHNICIAN_ID, t.id);
    }

    @Test
    public void testFindByTechnicianCode(){
        TechnicianRepresentation t = when().get(Fixture.API_ROOT + HelpdeskUri.TECHNICIANS +"/TECH001")
                .then()
                .statusCode(200)
                .extract().as(TechnicianRepresentation.class);
        Assertions.assertEquals("TECH001", t.technicianCode);
    }

    @Test
    @TestTransaction
    public void testRemoveSpecialty(){
        technicianRepository.getEntityManager().clear();
        String uri = Fixture.API_ROOT + HelpdeskUri.TECHNICIANS +"/" +Fixture.Technicians.TECHNICIAN_ID;

        given()
                .contentType(ContentType.JSON)
                .when().put(uri+"/removeSpecialty/1000")
                .then().statusCode(200);

        TechnicianRepresentation tr = when().get(uri)
                .then()
                .statusCode(200)
                .extract().as(TechnicianRepresentation.class);
        Assertions.assertEquals(0, tr.specialties.size());
    }

    @Test
    @TestTransaction
    public void removeSpecialty_Failure(){
        String uri = Fixture.API_ROOT + HelpdeskUri.TECHNICIANS +"/";

        //PASS WRONG Specialty ID
        given()
                .contentType(ContentType.JSON)
                .when().put(uri+"Fixture.Technicians.TECHNICIAN_ID/removeSpecialty/9999")
                .then().statusCode(404);

        //PASS WRONG Technician ID
        given()
                .contentType(ContentType.JSON)
                .when().put(uri+"9999/removeSpecialty/1000")
                .then().statusCode(404);

        //REMOVE Specialty THAT IS NOT ASSIGNED TO Technician
        given()
                .contentType(ContentType.JSON)
                .when().put(uri+"Fixture.Technicians.TECHNICIAN_ID/removeSpecialty/1003")
                .then().statusCode(404);
    }

    @Test
    @TestTransaction
    public void testAddSpecialty(){
        Integer size = technicianRepository.findById(Fixture.Technicians.TECHNICIAN_ID).getSpecialties().size();
        Assertions.assertEquals(1, size);
        String uri = Fixture.API_ROOT + HelpdeskUri.TECHNICIANS +"/" +Fixture.Technicians.TECHNICIAN_ID;

        given()
                .contentType(ContentType.JSON)
                .when().put(uri+"/addSpecialty/1001")
                .then().statusCode(200);

        TechnicianRepresentation tr = when().get(uri)
                .then()
                .statusCode(200)
                .extract().as(TechnicianRepresentation.class);
        Assertions.assertEquals(2, tr.specialties.size());
    }

    @Test
    @TestTransaction
    public void testAddSpecialty_Failure(){
        technicianRepository.getEntityManager().clear();
        String uri = Fixture.API_ROOT + HelpdeskUri.TECHNICIANS +"/";

        //ADD NON-EXISTING Specialty
        given()
                .contentType(ContentType.JSON)
                .when().put(uri+"Fixture.Technicians.TECHNICIAN_ID/addSpecialty/9999")
                .then().statusCode(404);

        //ADD Specialty TO NON-EXISTING Technician
        given()
                .contentType(ContentType.JSON)
                .when().put(uri+"9999/addSpecialty/1001")
                .then().statusCode(404);
    }

}
