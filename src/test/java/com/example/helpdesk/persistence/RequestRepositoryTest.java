package com.example.helpdesk.persistence;

import com.example.helpdesk.domain.Request;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.List;

@QuarkusTest
public class RequestRepositoryTest {
    @Inject
    RequestRepository requestRepository;

    @Test
    public void test(){
        List<Request> requests = requestRepository.findByTelephoneNumber("1234567890");
        requests.forEach(r -> System.out.println("Request: " + r.getTelephoneNumber()));

        Request r2 = requestRepository.findByTelephoneNumber("1234567890").getFirst();
        System.out.println("Request 2: " + r2.getTelephoneNumber());
    }
}
