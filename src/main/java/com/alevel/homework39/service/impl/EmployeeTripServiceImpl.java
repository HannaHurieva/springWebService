package com.alevel.homework39.service.impl;


import com.alevel.homework39.dto.EmployeeTrip;
import com.alevel.homework39.repository.EmployeeTripRepo;
import com.alevel.homework39.service.EmployeeTripNotFoundException;
import com.alevel.homework39.service.EmployeeTripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public final class EmployeeTripServiceImpl implements EmployeeTripService {

    private final EmployeeTripRepo employeeTripRepo;

    @Autowired
    public EmployeeTripServiceImpl(EmployeeTripRepo employeeTripRepo) {
        this.employeeTripRepo = employeeTripRepo;
    }

    @Override
    public List<EmployeeTrip> findAll() {
        return employeeTripRepo.findAll();
    }

    @Override
    public Optional<EmployeeTrip> findById(Long id) {
        return employeeTripRepo.findById(id);
    }

    @Override
    public EmployeeTrip findByName(String name) {
        return employeeTripRepo.findByName(name);
    }

    @Override
    public Optional<EmployeeTrip> getMax() {
        return employeeTripRepo.findFirstByOrderByRouteDesc();
    }

    @Override
    public Optional<EmployeeTrip> getMin() {
        return employeeTripRepo.findFirstByOrderByRouteAsc();
    }

    @Override
    public void update(Long id, EmployeeTrip employeeTrip) {
        EmployeeTrip old =
                findById(id).orElseThrow(() -> new EmployeeTripNotFoundException(id));
        employeeTrip.setId(id);

        String name = employeeTrip.getName();
        if (name != null) {
            old.setName(name);
        }
        int route = employeeTrip.getRoute();
        if (old.getRoute() != route) {
            old.setRoute(route);
        }

        employeeTripRepo.save(old);
    }

    @Override
    public Long save(EmployeeTrip employeeTrip) {
        return employeeTripRepo.save(employeeTrip).getId();
    }

    @Override
    public void delete(long id) {
        employeeTripRepo.deleteById(id);
    }
}
