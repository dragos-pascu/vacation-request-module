package com.project.vacationapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.project.vacationapi.entity.Employee;
import com.project.vacationapi.entity.Holiday;
import com.project.vacationapi.entity.VacationRequest;
import com.project.vacationapi.service.EmployeeService;
import com.project.vacationapi.service.HolidayService;
import com.project.vacationapi.service.VacationRequestService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest
public class VacationRequestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @MockBean
    private VacationRequestService vacationRequestService;

    @MockBean
    private HolidayService holidayService;

    @Test
    public void testCreateVacationRequest() throws Exception {


        Long employeeId = 1L;
        Employee employee = Employee.builder()
                .id(employeeId)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .build();

        VacationRequest vacationRequest = VacationRequest.builder()
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(5))
                .status(VacationRequest.VacationRequestStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .employee(employee)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        Mockito.when(vacationRequestService.createVacationRequest(Mockito.any(VacationRequest.class), Mockito.anyLong()))
                .thenReturn(vacationRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/create-vacation-request")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("employeeId", employeeId.toString())
                        .content(objectMapper.writeValueAsString(vacationRequest))) // use the objectMapper here
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.startDate", Matchers.is(LocalDate.now().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endDate", Matchers.is(LocalDate.now().plusDays(5).toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is(VacationRequest.VacationRequestStatus.PENDING.name())));

        Mockito.verify(vacationRequestService, Mockito.times(1))
                .createVacationRequest(Mockito.any(VacationRequest.class), Mockito.anyLong());
    }


    @Test
    public void testUpdateVacationRequest() throws Exception {
        Long employeeId = 1L;
        Employee employee = Employee.builder()
                .id(employeeId)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .build();

        Long vacationRequestId = 1L;
        VacationRequest oldVacationRequest = VacationRequest.builder()
                .id(vacationRequestId)
                .startDate(LocalDate.now().minusDays(1))
                .endDate(LocalDate.now().plusDays(4))
                .status(VacationRequest.VacationRequestStatus.PENDING)
                .createdAt(LocalDateTime.now().minusDays(1))
                .updatedAt(LocalDateTime.now().minusDays(1))
                .employee(employee)
                .build();

        VacationRequest updatedVacationRequest = VacationRequest.builder()
                .id(vacationRequestId)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(5))
                .status(VacationRequest.VacationRequestStatus.APPROVED)
                .createdAt(oldVacationRequest.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .employee(employee)
                .build();

        Mockito.when(vacationRequestService.updateVacationRequest(Mockito.anyLong(), Mockito.any(VacationRequest.class)))
                .thenReturn(updatedVacationRequest);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        mockMvc.perform(MockMvcRequestBuilders.put("/api/update-request/" + vacationRequestId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedVacationRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.startDate", Matchers.is(LocalDate.now().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endDate", Matchers.is(LocalDate.now().plusDays(5).toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is(VacationRequest.VacationRequestStatus.APPROVED.name())));

        Mockito.verify(vacationRequestService, Mockito.times(1))
                .updateVacationRequest(Mockito.anyLong(), Mockito.any(VacationRequest.class));
    }

    @Test
    public void testGetAvailableVacationDays() throws Exception {
        Long employeeId = 1L;
        int year = 2023;
        int availableVacationDays = 20;

        Mockito.when(employeeService.getAvailableVacationDays(employeeId, year))
                .thenReturn(availableVacationDays);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/employees/" + employeeId + "/available-vacation-days")
                        .param("year", String.valueOf(year)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("20"));

        Mockito.verify(employeeService, Mockito.times(1))
                .getAvailableVacationDays(employeeId, year);

    }

    @Test
    public void testGetAllVacationRequests() throws Exception {

        List<VacationRequest> requests = Arrays.asList(

        VacationRequest.builder()
                .id(1L)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(5))
                .status(VacationRequest.VacationRequestStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build(),

        VacationRequest.builder()
                .id(2L)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(10))
                .status(VacationRequest.VacationRequestStatus.APPROVED)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build()


        );


        Mockito.when(vacationRequestService.getAllVacationRequests()).thenReturn(requests);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/get-requests")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].status", Matchers.is(VacationRequest.VacationRequestStatus.PENDING.name())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].status", Matchers.is(VacationRequest.VacationRequestStatus.APPROVED.name())));

        Mockito.verify(vacationRequestService, Mockito.times(1)).getAllVacationRequests();
    }


    @Test
    public void testGetHolidays() throws Exception {
        List<Holiday> holidays = Arrays.asList(

                Holiday.builder().
                        id(1L).
                        name("New Year's Day").
                        date(LocalDate.of(2023, 1, 1)).
                        build(),
                Holiday.
                        builder().
                        id(2L).
                        name("St. Andrew's Day").
                        date(LocalDate.of(2023, 11, 30)).
                        build()

        );
        Mockito.when(holidayService.getHolidays()).thenReturn(holidays);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/holidays")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is("New Year's Day")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].date", Matchers.is("2023-01-01")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name", Matchers.is("St. Andrew's Day")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].date", Matchers.is("2023-11-30")));

        Mockito.verify(holidayService, Mockito.times(1)).getHolidays();


    }


}
