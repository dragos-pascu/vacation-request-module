package com.project.vacationapi.service;
import com.project.vacationapi.entity.Holiday;
import com.project.vacationapi.repository.HolidayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HolidayServiceImpl implements HolidayService{

    @Autowired
    private HolidayRepository holidayRepository;

    @Override
    public List<Holiday> getHolidays() {
        return holidayRepository.findAll();
    }
}
