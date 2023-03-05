package com.project.vacationapi.service;

import com.project.vacationapi.entity.Employee;
import com.project.vacationapi.entity.VacationRequest;
import com.project.vacationapi.repository.EmployeeRepository;
import com.project.vacationapi.repository.VacationRequestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VacationRequestServiceImplTest {

    @Mock
    private VacationRequestRepository vacationRequestRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    private VacationRequestServiceImpl requestVacationService;

    @BeforeEach
    void setUp() {
        requestVacationService = new VacationRequestServiceImpl(vacationRequestRepository, employeeRepository);

    }

    @Test
    void testCreateVacationRequest() {
        // Arrange
        Long employeeId = 1L;
        Employee employee = Employee.builder()
                .id(employeeId)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .build();


        when(employeeRepository.findById(employeeId)).thenReturn(java.util.Optional.of(employee));

        VacationRequest vacationRequest = VacationRequest.builder()
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(5))
                .status(VacationRequest.VacationRequestStatus.PENDING)
                .employee(employee)
                .build();

        when(vacationRequestRepository.save(ArgumentMatchers.any(VacationRequest.class))).thenReturn(vacationRequest);

        // Act
        VacationRequest createdVacationRequest = requestVacationService.createVacationRequest(vacationRequest, employeeId);

        // Assert
        assertNotNull(createdVacationRequest);
        assertEquals(createdVacationRequest.getStatus(), VacationRequest.VacationRequestStatus.PENDING);
        verify(employeeRepository, times(1)).findById(employeeId);
        verify(vacationRequestRepository, times(1)).save(vacationRequest);

    }

@Disabled
    @Test
    public void updateVacationRequest_ValidRequest_ReturnsUpdatedRequest() {
        // Arrange
        VacationRequest existingRequest = VacationRequest.builder()
                .id(1L)
                .startDate(LocalDate.of(2022, 1, 1))
                .endDate(LocalDate.of(2022, 1, 5))
                .status(VacationRequest.VacationRequestStatus.PENDING)
                .build();

        VacationRequest updatedRequest = VacationRequest.builder()
                .id(1L)
                .startDate(LocalDate.of(2022, 2, 1))
                .endDate(LocalDate.of(2022, 2, 5))
                .status(VacationRequest.VacationRequestStatus.APPROVED)
                .build();

        when(vacationRequestRepository.findById(1L)).thenReturn(Optional.of(existingRequest));
        //when(vacationRequestRepository.save(any(VacationRequest.class))).thenReturn(updatedRequest);

        // Act
        VacationRequest result = requestVacationService.updateVacationRequest(1L, updatedRequest);

        // Assert
        assertEquals(1L, result.getId());
        assertEquals(LocalDate.of(2022, 2, 1), result.getStartDate());
        assertEquals(LocalDate.of(2022, 2, 5), result.getEndDate());
        assertEquals(VacationRequest.VacationRequestStatus.APPROVED, result.getStatus());
    }


    @Test
    public void testGetAllVacationRequests() {
        // create a list of vacation requests to return from the repository
        List<VacationRequest> vacationRequests = Arrays.asList(
                VacationRequest.builder()
                        .id(1L)
                        .startDate(LocalDate.now())
                        .endDate(LocalDate.now().plusDays(5))
                        .status(VacationRequest.VacationRequestStatus.PENDING)
                        .employee(new Employee())
                        .build(),
                VacationRequest.builder()
                        .id(2L)
                        .startDate(LocalDate.now().plusDays(10))
                        .endDate(LocalDate.now().plusDays(15))
                        .status(VacationRequest.VacationRequestStatus.APPROVED)
                        .employee(new Employee())
                        .build(),
                VacationRequest.builder()
                        .id(3L)
                        .startDate(LocalDate.now().plusDays(20))
                        .endDate(LocalDate.now().plusDays(25))
                        .status(VacationRequest.VacationRequestStatus.REJECTED)
                        .employee(new Employee())
                        .build()
        );

        // mock the vacationRequestRepository to return the above list of vacation requests
        when(vacationRequestRepository.findAll()).thenReturn(vacationRequests);

        // call the getAllVacationRequests() method and assert that it returns the same list of vacation requests
        List<VacationRequest> result = requestVacationService.getAllVacationRequests();
        assertEquals(vacationRequests, result);
    }
}