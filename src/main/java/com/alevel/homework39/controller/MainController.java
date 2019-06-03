package com.alevel.homework39.controller;

import com.alevel.homework39.dto.EmployeeTrip;
import com.alevel.homework39.service.EmployeeTripNotFoundException;
import com.alevel.homework39.service.EmployeeTripService;
import com.alevel.homework39.service.TripCost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

/*    @PostMapping("/main")
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeTrip save(@RequestBody EmployeeTrip employeeTrip) {
        Long id = employeeTripService.save(employeeTrip);
        employeeTrip.setId(id);

        return employeeTrip;
    }*/

    @PostMapping("/main")
    public String add(@RequestParam String name,
                      @RequestParam int route,
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

    @GetMapping("/{id}")
    public EmployeeTrip find(@PathVariable Long id) {
        return employeeTripService.findById(id).
                orElseThrow(() -> new EmployeeTripNotFoundException(id));

    }

    @GetMapping("/value")
    public String getValue() {
        return "value";
/*        return "<b> Fuel cost = " + fuelCost + " euro </b>" +
                "<br><b> Fuel consumption = " + fuelConsumption +" </b></br>";*/
    }

    @GetMapping("/cost")
    public String getCost(Map<String, Object> model) {

        EmployeeTrip tripMax = employeeTripService.getMax().orElse(new EmployeeTrip());
        TripCost tripCostMax = new TripCost();
        tripCostMax.setName(tripMax);
        tripCostMax.setCost(tripMax, fuelConsumption, fuelCost);

        List<TripCost> tripCostList = new ArrayList<>();
        tripCostList.add(tripCostMax);

        EmployeeTrip tripMin = employeeTripService.getMin().orElse(new EmployeeTrip());
        TripCost tripCostMin = new TripCost();
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

    @PatchMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody EmployeeTrip employeeTrip) {
        employeeTripService.update(id, employeeTrip);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        employeeTripService.delete(id);
    }
}
