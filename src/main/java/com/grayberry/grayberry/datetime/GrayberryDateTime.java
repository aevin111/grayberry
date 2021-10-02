package com.grayberry.grayberry.datetime;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GrayberryDateTime
{
    public String getDateTime(LocalDateTime localDateTime)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dateTime = localDateTime.format(formatter);
        return dateTime;
    }
}
