package com.alevel.homework39.dto;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;

@Entity
public class EmployeeTrip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Name of object cannot be empty")
    private String name;

    @Min(value = 0, message = "route: must be greater than or equal to 0")
    private int route; // число миль(!), которые сотрудник предприятия проехал на корпоративной машине.

    public EmployeeTrip() {
    }

    public EmployeeTrip(String name, int route) {
        this.name = name;
        this.route = route;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRoute() {
        return route;
    }

    public void setRoute(int route) {
        this.route = route;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeTrip that = (EmployeeTrip) o;
        return route == that.route &&
                id.equals(that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, route);
    }

    @Override
    public String toString() {
        return "EmployeeTrip{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", route=" + route +
                '}';
    }
}
