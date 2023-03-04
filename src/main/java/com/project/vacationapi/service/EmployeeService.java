package com.project.vacationapi.service;

public interface EmployeeService {
    int getAvailableVacationDays(Long employeeId, int year);
}
