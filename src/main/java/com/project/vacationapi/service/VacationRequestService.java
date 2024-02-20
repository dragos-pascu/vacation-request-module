package com.project.vacationapi.service;
import com.project.vacationapi.entity.VacationRequest;
import java.util.List;

public interface VacationRequestService {

    VacationRequest createVacationRequest(VacationRequest vacationRequest, Long employeeId);

    VacationRequest updateVacationRequest(Long vacationRequestId, VacationRequest vacationRequestDetails);

    List<VacationRequest> getAllVacationRequests();
}
