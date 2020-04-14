package com.epam.exercises.cleaningcode.exercise2;

import java.util.Calendar;
import java.util.Date;

public final class DateUtil {

    private DateUtil() {}

    public static Date incrementDate(Date date) {
        return getCalendar(date, 1).getTime();
    }

    public static Date decrementDate(Date date) {
        return getCalendar(date, -1).getTime();
    }

    private static Calendar getCalendar(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        return calendar;
    }

    public static Date buildDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();
    }

    public static void main(String[] args) {
        Date date = new Date();
        date = incrementDate(date);
        System.out.println(date);

        System.out.println(buildDate(2014, 10, 10));
    }

}
