package com.alevel.homework39.repository;

import com.alevel.homework39.dto.EmployeeTrip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeTripRepo extends JpaRepository<EmployeeTrip, Long> {

    EmployeeTrip findByName (String name);

    Optional<EmployeeTrip> findFirstByOrderByRouteDesc();

    Optional<EmployeeTrip> findFirstByOrderByRouteAsc();
}
