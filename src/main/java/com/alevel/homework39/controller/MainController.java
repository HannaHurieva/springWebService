package com.alevel.homework39.controller;

import com.alevel.homework39.dto.EmployeeTrip;
import com.alevel.homework39.service.EmployeeTripNotFoundException;
import com.alevel.homework39.service.EmployeeTripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/main")
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

    @GetMapping("/value")
    public String getValue()
    {
        return "<b> Fuel cost = " + fuelCost + " euro </b>" +
                "<br><b> Fuel consumption = " + fuelConsumption +" </b></br>";
    }

    @GetMapping
    public List<EmployeeTrip> findAll() {
        return employeeTripService.findAll();
    }

    @GetMapping("/{id}")
    public EmployeeTrip find(@PathVariable Long id) {
        return employeeTripService.findById(id).
                orElseThrow(() -> new EmployeeTripNotFoundException(id));

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeTrip save(@RequestBody EmployeeTrip employeeTrip) {
        Long id = employeeTripService.save(employeeTrip);
        employeeTrip.setId(id);

        return employeeTrip;
    }


    @GetMapping("/cost")
    public String getMaxMinCost() {

        EmployeeTrip tripMax = employeeTripService.getMax().orElse(new EmployeeTrip());
        float costMax = (float) (tripMax.getRoute() * 1.609 / 100 * fuelConsumption * fuelCost );
        String nameMax = tripMax.getName();

        EmployeeTrip tripMin = employeeTripService.getMin().orElse(new EmployeeTrip());
        float costMin = (float) (tripMin.getRoute() * 1.609 / 100 * fuelConsumption * fuelCost);
        String nameMin = tripMin.getName();

        return "<b> Max trip : </b>" + nameMax + "<b> cost " + costMax + " euro </b> " +
                "<br><b> Min trip : </b>" + nameMin + "<b> cost " + costMin + " euro </b></br> ";
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
