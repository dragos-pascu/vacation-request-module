package com.project.vacationapi.service;
import com.project.vacationapi.entity.Employee;
import com.project.vacationapi.exception.ResourceNotFoundException;
import com.project.vacationapi.repository.EmployeeRepository;
import com.project.vacationapi.repository.VacationRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private VacationRequestRepository vacationRequestRepository;


    @Override
    public int getAvailableVacationDays(Long employeeId, int year) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", employeeId));

        int totalVacationDays = employee.getVacationDays();
        int usedVacationDays = vacationRequestRepository.countUsedVacationDaysByEmployeeId(employeeId, year);

        return totalVacationDays - usedVacationDays;
    }

    @Override
    public Optional<Employee> getEmployeeById(long l) {
        return employeeRepository.findById(l);
    }
}
