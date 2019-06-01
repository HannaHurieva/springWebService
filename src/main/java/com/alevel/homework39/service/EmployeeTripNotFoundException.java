package com.alevel.homework39.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EmployeeTripNotFoundException extends RuntimeException {
    public EmployeeTripNotFoundException(Long id) {
        super("EmployeeTrip with id " + id + " not found");
    }
}
