package com.example.helpdesk.persistence;

import com.example.helpdesk.Fixture;
import com.example.helpdesk.domain.Request;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

@QuarkusTest
public class RequestRepositoryTest {
    @Inject
    RequestRepository requestRepository;

    @Test
    public void testFindByTelephoneNumber(){
        List<Request> requests = requestRepository.findByTelephoneNumber("1234567890");
        Assertions.assertEquals(Fixture.Requests.PHONE_NUMBER, requests.get(0).getTelephoneNumber());

        Request r2 = requestRepository.findByTelephoneNumber("1234567890").getFirst();
        Assertions.assertEquals(Fixture.Requests.PHONE_NUMBER, r2.getTelephoneNumber());
    }

    @Test
    public void testSearch(){
        Request request = requestRepository.search(6000);
        Assertions.assertEquals(6000, request.getId());
    }
}
