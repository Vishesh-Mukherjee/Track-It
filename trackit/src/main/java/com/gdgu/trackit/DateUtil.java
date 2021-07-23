package com.gdgu.trackit;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    
    public static LocalDate convert(String strDate) {
        return LocalDate.parse(strDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    public static String format(LocalDate localDate) {
        return localDate.format(DateTimeFormatter.ofPattern("EEE, MMM d, ''yy")); 
    }

}
