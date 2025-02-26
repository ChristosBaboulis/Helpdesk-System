package com.example.helpdesk.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;

public class SystemDateTest {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    LocalDate stub = LocalDate.parse("03/01/1990", formatter);

    @Test
    public void checkSystemDateFunctionality() {
        SystemDate testDate = new SystemDate();
        SystemDate.now();

        SystemDateStub.setStub(stub);
        SystemDate.now();

        SystemDateStub.reset();
    }
}
