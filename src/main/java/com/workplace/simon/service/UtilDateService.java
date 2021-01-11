package com.workplace.simon.service;

import com.workplace.simon.model.Period;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class UtilDateService implements UtilDate {

    public static final int DAYS_OF_WEEK = 6;

    @Override
    public List<Date> getStartAndEndDate() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - calendar.getFirstDayOfWeek();
        calendar.add(Calendar.DAY_OF_MONTH, -dayOfWeek);

        Date weekStart = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, DAYS_OF_WEEK);
        Date weekEnd = calendar.getTime();

        ArrayList<Date> result = new ArrayList<>();
        result.add(0, weekStart);
        result.add(1, weekEnd);

        return result;
    }

    public Period getPeriod() {
        Period period = new Period();

        List<java.util.Date> currentWeek = this.getStartAndEndDate();

        period.setStartDate(new java.sql.Date(currentWeek.get(0).getTime()));
        period.setEndDate(new java.sql.Date(currentWeek.get(1).getTime()));

        return period;
    }
}
