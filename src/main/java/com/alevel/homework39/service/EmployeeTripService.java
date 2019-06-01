package com.alevel.homework39.service;

import com.alevel.homework39.dto.EmployeeTrip;

import java.util.List;
import java.util.Optional;

public interface EmployeeTripService {
    List<EmployeeTrip> findAll();

    Optional<EmployeeTrip> findById(Long id);

    EmployeeTrip findByName(String name);

    Optional<EmployeeTrip> getMax();

    Optional<EmployeeTrip> getMin();

    void update(Long id, EmployeeTrip todo);

    Long save(EmployeeTrip todo);

    void delete(long id);
}
