package com.project.vacationapi.service;

import com.project.vacationapi.entity.Employee;

import java.util.Optional;

public interface EmployeeService {
    int getAvailableVacationDays(Long employeeId, int year);

    Optional<Employee> getEmployeeById(long l);
}
