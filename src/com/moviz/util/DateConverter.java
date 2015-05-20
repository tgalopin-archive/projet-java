package com.moviz.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Format release dates from and to string in order to display them properly
 *
 * @author Titouan Galopin <galopintitouan@gmail.com>
 */
public class DateConverter {

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static Date stringToDate(String dateString) {
        if (dateString.length() == 0) {
            return null;
        }

        try {
            return simpleDateFormat.parse(dateString);
        } catch (Exception e) {
            return null;
        }
    }

    public static Date localDateToDate(LocalDate date) {
        try {
            return stringToDate(date.toString());
        } catch (Exception e) {
            return null;
        }
    }

    public static LocalDate dateToLocalDate(Date date) {
        try {
            return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        } catch (Exception e) {
            return null;
        }
    }

}
