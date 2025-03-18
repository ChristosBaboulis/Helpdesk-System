package com.example.helpdesk.service;

import com.example.helpdesk.persistence.RequestRepository;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

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
}
