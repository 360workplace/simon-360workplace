package com.workplace.simon.service;

import com.workplace.simon.model.Period;

import java.util.Date;
import java.util.List;

public interface UtilDate {
    List<Date> getStartAndEndDate();

    Period getPeriod();
}
