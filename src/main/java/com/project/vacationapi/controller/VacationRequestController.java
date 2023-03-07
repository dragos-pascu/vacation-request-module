package com.project.vacationapi.controller;
import com.project.vacationapi.entity.Holiday;
import com.project.vacationapi.entity.VacationRequest;
import com.project.vacationapi.service.EmployeeService;
import com.project.vacationapi.service.HolidayService;
import com.project.vacationapi.service.VacationRequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;



@RestController
@RequestMapping("/api")
public class VacationRequestController {

    private final Logger LOGGER =
            LoggerFactory.getLogger(VacationRequestController.class);

    @Autowired
    EmployeeService employeeService;
    @Autowired
    VacationRequestService vacationRequestService;
    @Autowired
    HolidayService holidayService;

    @PostMapping("/vacation-requests")
    public VacationRequest createVacationRequest(@RequestBody VacationRequest vacationRequest,
                                                 @Valid @RequestParam Long employeeId) {

        VacationRequest createdRequest = vacationRequestService.createVacationRequest(vacationRequest,employeeId);
        return createdRequest;

    }

    @PutMapping("/update-request/{id}")
    public VacationRequest updateVacationRequest(@PathVariable(value = "id") Long vacationRequestId,
                                                 @Valid @RequestBody VacationRequest vacationRequestDetails) {

        VacationRequest updatedVacationRequest = vacationRequestService.updateVacationRequest(vacationRequestId,vacationRequestDetails );
        return updatedVacationRequest;
    }

    @GetMapping("/employees/{employeeId}/available-vacation-days")
    public int getAvailableVacationDays(@PathVariable(value = "employeeId") Long employeeId,
                                        @RequestParam int year) {

        int availableVacationDays = employeeService.getAvailableVacationDays(employeeId,year);
        return availableVacationDays;
    }

    @GetMapping("/get-requests")
    public List<VacationRequest> getAllVacationRequests() {

        List<VacationRequest> requests = vacationRequestService.getAllVacationRequests();
        return requests;
    }

    @GetMapping("/holidays")
    public List<Holiday> getHolidays() {
        List<Holiday> holidays = holidayService.getHolidays();
        return holidays;
    }



}
