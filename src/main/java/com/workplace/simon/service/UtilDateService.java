package com.workplace.simon.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class UtilDateService implements UtilDate {

    @Override
    public List<Date> getStartAndEndDate() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - calendar.getFirstDayOfWeek();
        calendar.add(Calendar.DAY_OF_MONTH, -dayOfWeek);

        Date weekStart = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, 6);
        Date weekEnd = calendar.getTime();

        ArrayList<Date> result = new ArrayList<>();
        result.add(0, weekStart);
        result.add(1, weekEnd);

        return result;
    }
}
