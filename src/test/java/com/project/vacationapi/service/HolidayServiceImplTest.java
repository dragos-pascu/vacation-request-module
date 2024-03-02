package com.project.vacationapi.service;

import com.project.vacationapi.entity.Holiday;
import com.project.vacationapi.repository.HolidayRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class HolidayServiceImplTest {

    @Mock
    private HolidayRepository holidayRepository;

    @InjectMocks
    private HolidayServiceImpl holidayService;



    @Test
    public void testGetHolidays() {
        Holiday holiday1 = Holiday.builder().id(1L).name("New Year's Day").date(LocalDate.of(2023, 1, 1)).build();
        Holiday holiday2 = Holiday.builder().id(2L).name("St. Andrew's Day").date(LocalDate.of(2023, 11, 30)).build();
        List<Holiday> holidays = Arrays.asList(holiday1, holiday2);

        when(holidayRepository.findAll()).thenReturn(holidays);

        List<Holiday> result = holidayService.getHolidays();

        assertEquals(2, result.size());
        assertEquals(holiday1.getId(), result.get(0).getId());
        assertEquals(holiday1.getName(), result.get(0).getName());
        assertEquals(holiday1.getDate(), result.get(0).getDate());
        assertEquals(holiday2.getId(), result.get(1).getId());
        assertEquals(holiday2.getName(), result.get(1).getName());
        assertEquals(holiday2.getDate(), result.get(1).getDate());
    }

}