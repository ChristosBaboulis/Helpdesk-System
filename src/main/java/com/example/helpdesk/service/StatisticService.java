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

        // Έλεγχος αν ο μήνας είναι έγκυρος
        if (monthNumber == null || monthNumber < 1 || monthNumber > 12) {
            throw new IllegalArgumentException("Invalid Month: " + monthNumber);
        }

        return Optional.ofNullable(requestRepository.findBySubmissionMonth(monthNumber))
                .map(List::size)
                .orElse(0); // Αν δεν υπάρχουν requests, επιστρέφουμε 0
    }

    public Double averageCommunicationActionsPerCategory(Integer categoryId) {
        if (categoryId == null) {
            throw new IllegalArgumentException("categoryId cannot be null");
        }

        // Find all requests for this category
        List<Request> requests = requestRepository.findByRequestCategoryId(categoryId);

        if (requests.isEmpty()) {
            return 0.0; // If there are no requests average is 0
        }

        // Calculate communication Actions of requests
        int totalCommunicationActions = requests.stream()
                .mapToInt(req -> (int) req.getActions().stream()
                        .filter(action -> action instanceof CommunicationAction)
                        .count()
                ).sum();

        // Find requests with at least 1 CommunicationAction
        long validRequests = requests.stream()
                .filter(req -> req.getActions().stream().anyMatch(action -> action instanceof CommunicationAction))
                .count();

        if (validRequests == 0) {
            return 0.0; // Avoid division by 0
        }

        // Calculate average for requests with CommunicationActions
        return (double) totalCommunicationActions / validRequests;
    }

}