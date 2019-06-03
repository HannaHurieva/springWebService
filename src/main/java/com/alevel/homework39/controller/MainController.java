package com.alevel.homework39.controller;

import com.alevel.homework39.dto.EmployeeTrip;
import com.alevel.homework39.service.EmployeeTripService;
import com.alevel.homework39.service.TripCostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller

public class MainController {

    private final EmployeeTripService employeeTripService;

    @Autowired
    public MainController(EmployeeTripService employeeTripService) {
        this.employeeTripService = employeeTripService;
    }

    @Value("${fuel.cost}")
    private float fuelCost;

    @Value("${fuel.consumption}")
    private float fuelConsumption;

    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "greeting";
    }

    @PostMapping("/main")
    public String add(@Valid @RequestParam String name,
                      @Valid @RequestParam  int route,
                      Map<String, Object> model) {

        EmployeeTrip employeeTrip = new EmployeeTrip(name, route);
        employeeTripService.save(employeeTrip);

        Iterable<EmployeeTrip> trips = employeeTripService.findAll();
        model.put("trips", trips);

        return "main";
    }

    @GetMapping("/main")
    public String findAll(Map<String, Object> model) {
        Iterable<EmployeeTrip> trips = employeeTripService.findAll();

        model.put("trips", trips);
        return "main";
    }

    @GetMapping("/value")
    public String getValue() {
        return "value";
    }

    @GetMapping("/cost")
    public String getCost(Map<String, Object> model) {

        EmployeeTrip tripMax = employeeTripService.getMax().orElse(new EmployeeTrip());
        TripCostService tripCostMax = new TripCostService();
        tripCostMax.setName(tripMax);
        tripCostMax.setCost(tripMax, fuelConsumption, fuelCost);

        List<TripCostService> tripCostList = new ArrayList<>();
        tripCostList.add(tripCostMax);

        EmployeeTrip tripMin = employeeTripService.getMin().orElse(new EmployeeTrip());
        TripCostService tripCostMin = new TripCostService();
        tripCostMin.setName(tripMin);
        tripCostMin.setCost(tripMin, fuelConsumption, fuelCost);

        tripCostList.add(tripCostMin);
        model.put("trips", tripCostList);

        return "cost";
    }

    @PostMapping("filter")
    public String filter(@RequestParam String filter,
                         Map<String, Object> model) {
        Iterable<EmployeeTrip> trips;

        if (filter != null && !filter.isEmpty()) {
            trips = employeeTripService.findByName(filter);
        } else {
            trips = employeeTripService.findAll();
        }

        model.put("trips", trips);

        return "main";
    }
}
