package com.boristenelsen.registrationTest.services;

import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class StatistikService {

    public int checkMonth(String month, Date date) {
        String temp = date.toString();
        String time = temp.substring(5, 7);
        if (time.contains(month)) {
            return 1;
        } else return 0;

    }


}
