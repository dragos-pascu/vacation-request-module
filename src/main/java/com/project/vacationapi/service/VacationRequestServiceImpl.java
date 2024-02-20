package com.project.vacationapi.service;
import com.project.vacationapi.entity.Employee;
import com.project.vacationapi.entity.VacationRequest;
import com.project.vacationapi.exception.ResourceNotFoundException;
import com.project.vacationapi.repository.EmployeeRepository;
import com.project.vacationapi.repository.VacationRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class VacationRequestServiceImpl implements VacationRequestService {

    @Autowired
    private VacationRequestRepository vacationRequestRepository;

    @Autowired
    private EmployeeRepository employeeRepository;




    @Override
    public VacationRequest createVacationRequest(VacationRequest vacationRequest, Long employeeId) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        if (employee.isEmpty()) {
            throw new ResourceNotFoundException("Employee", "id", employeeId);
        }

        vacationRequest.setEmployee(employee.get());
        vacationRequest.setStatus(VacationRequest.VacationRequestStatus.PENDING);
        vacationRequest.setCreatedAt(LocalDateTime.now());
        vacationRequest.setUpdatedAt(LocalDateTime.now());

        VacationRequest savedVacationRequest = vacationRequestRepository.save(vacationRequest);

        return savedVacationRequest;
    }

    @Override
    public VacationRequest updateVacationRequest(Long vacationRequestId, VacationRequest vacationRequestDetails) {
        VacationRequest vacationRequest = vacationRequestRepository.findById(vacationRequestId)
                .orElseThrow(() -> new ResourceNotFoundException("VacationRequest", "id", vacationRequestId));

        vacationRequest.setStartDate(vacationRequestDetails.getStartDate());
        vacationRequest.setEndDate(vacationRequestDetails.getEndDate());
        vacationRequest.setStatus(vacationRequestDetails.getStatus());

        VacationRequest updatedVacationRequest = vacationRequestRepository.save(vacationRequest);

        return updatedVacationRequest;
    }

    @Override
    public List<VacationRequest> getAllVacationRequests() {
        List<VacationRequest> requests = vacationRequestRepository.findAll();
        return requests;
    }


}
