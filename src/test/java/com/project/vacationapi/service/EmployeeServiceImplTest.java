package com.project.vacationapi.service;

import com.project.vacationapi.entity.Employee;
import com.project.vacationapi.repository.EmployeeRepository;
import com.project.vacationapi.repository.VacationRequestRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private VacationRequestRepository vacationRequestRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService ;

    @Test
    public void testGetAvailableVacationDays() {
        Long employeeId = 1L;
        int year = 2023;
        Employee employee = new Employee();
        employee.setId(employeeId);
        employee.setVacationDays(20);

        int usedVacationDays = 10;

        Mockito.when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        Mockito.when(vacationRequestRepository.countUsedVacationDaysByEmployeeId(employeeId, year)).thenReturn(usedVacationDays);

        int availableVacationDays = employeeService.getAvailableVacationDays(employeeId, year);

        assertEquals(10, availableVacationDays);
    }
}

