package com.example.helpdesk.service;

import com.example.helpdesk.domain.CommunicationAction;
import com.example.helpdesk.domain.Request;
import com.example.helpdesk.persistence.RequestRepository;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;

@RequestScoped
public class StatisticService {

    @Inject
    RequestRepository requestRepository;

    public Integer requestsPerMonth(Integer monthNumber){

        //CHECK IF MONTH IS VALID
        if (monthNumber == null || monthNumber < 1 || monthNumber > 12) {
            throw new IllegalArgumentException("Invalid Month: " + monthNumber);
        }

        return Optional.ofNullable(requestRepository.findBySubmissionMonth(monthNumber))
                .map(List::size)
                .orElse(0); //IF THERE ARE NO Requests, RETURN 0
    }

    public Double averageCommunicationActionsPerCategory(Integer categoryId) {
        if (categoryId == null) {
            throw new IllegalArgumentException("categoryId cannot be null");
        }

        //FIND ALL REQUESTS FOR THIS CATEGORY
        List<Request> requests = requestRepository.findByRequestCategoryId(categoryId);

        if (requests.isEmpty()) {
            return 0.0; //IF THERE ARE NO Requests AVERAGE IS 0
        }

        //CALCULATE COMMUNICATION ACTIONS OF Requests
        int totalCommunicationActions = requests.stream()
                .mapToInt(req -> (int) req.getActions().stream()
                        .filter(action -> action instanceof CommunicationAction)
                        .count()
                ).sum();

        //FIND Requests WITH AT LEAST 1 CommunicationAction
        long validRequests = requests.stream()
                .filter(req -> req.getActions().stream().anyMatch(action -> action instanceof CommunicationAction))
                .count();

        if (validRequests == 0) {
            return 0.0; //AVOID DIVISION BY 0
        }

        //CALCULATE AVERAGE FOR Requests WITH CommunicationActions
        return (double) totalCommunicationActions / validRequests;
    }

}
