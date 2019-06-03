package com.alevel.homework39.service;

import com.alevel.homework39.dto.EmployeeTrip;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class TripCostService {
    private String name;
    private float cost;

    public TripCostService() {
    }

    public TripCostService(String name, float cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public void setName(EmployeeTrip employeeTrip) {
        name = employeeTrip.getName();
    }

    public float getCost() {
        return cost;
    }

    public void setCost(EmployeeTrip employeeTrip, float fuelConsumption, float fuelCost) {
        cost = (float) (employeeTrip.getRoute() * 1.609 / 100 * fuelConsumption * fuelCost);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TripCostService tripCost = (TripCostService) o;
        return Float.compare(tripCost.cost, cost) == 0 &&
                Objects.equals(name, tripCost.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, cost);
    }

    @Override
    public String toString() {
        return "TripCost{" +
                "name='" + name + '\'' +
                ", cost=" + cost +
                '}';
    }
}
