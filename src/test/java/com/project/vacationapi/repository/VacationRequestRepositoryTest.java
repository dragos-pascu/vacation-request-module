package com.project.vacationapi.repository;
import com.project.vacationapi.entity.Employee;
import com.project.vacationapi.entity.VacationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DataJpaTest
class VacationRequestRepositoryTest {

    @Autowired
    private VacationRequestRepository vacationRequestRepository;

    @Autowired
    private  EmployeeRepository employeeRepository;
    @Autowired
    private TestEntityManager entityManager;


    @BeforeEach
    void setup(){
        // Create an employee entity and save it to the database
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setEmail("john.doe@example.com");
        employee = employeeRepository.save(employee);

        // Create a vacation request entity for the employee and save it to the database
        VacationRequest vacationRequest = new VacationRequest();
        vacationRequest.setEmployee(employee);
        vacationRequest.setStartDate(LocalDate.of(2022, 3, 1));
        vacationRequest.setEndDate(LocalDate.of(2022, 3, 5));
        vacationRequest.setStatus(VacationRequest.VacationRequestStatus.APPROVED);
        vacationRequest.setCreatedAt(LocalDateTime.now());
        vacationRequest.setUpdatedAt(LocalDateTime.now());
        vacationRequestRepository.save(vacationRequest);

        entityManager.persist(employee);
    }

    @Test
    void countUsedVacationDaysByEmployeeId_ShouldReturnCorrectValue() {
        System.out.println("test");

        Optional<Employee> employee = employeeRepository.findById(1L);
        System.out.println(employee.get().toString());

        // Call the countUsedVacationDaysByEmployeeId method with the employee id and year
        Integer usedVacationDays = vacationRequestRepository.countUsedVacationDaysByEmployeeId(employee.get().getId(), 2022);

        // Assert that the returned value is the expected value
        assertEquals(5, usedVacationDays);
    }


}