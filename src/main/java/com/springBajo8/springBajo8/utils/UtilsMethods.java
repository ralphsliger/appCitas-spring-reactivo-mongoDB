package com.springBajo8.springBajo8.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UtilsMethods {

    public static LocalDate StringToLocalDate(String fecha){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(fecha, formatter);
    }
}
