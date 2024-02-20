package com.project.vacationapi.service;

import com.project.vacationapi.repository.VacationRequestRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest

class VacationRequestServiceImplTest {


    @Autowired
    VacationRequestService vacationRequestService;

    @MockBean
    private VacationRequestRepository vacationRequestRepository;

    @Test
    void createVacationRequest() {
    }

    @Test
    void updateVacationRequest() {
    }

    @Test
    void getAllVacationRequests() {
    }
}