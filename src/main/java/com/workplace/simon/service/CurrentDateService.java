package com.workplace.simon.service;

import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Calendar;

@Service
public class CurrentDateService implements CurrentDate {
    @Override
    public Date getDate() {
        // return new Date(System.currentTimeMillis());
        return new Date(Calendar.getInstance().getTime().getTime());
    }
}
