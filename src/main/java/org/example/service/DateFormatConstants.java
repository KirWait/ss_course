package org.example.service;

import java.text.SimpleDateFormat;

public class DateFormatConstants {

    public static final SimpleDateFormat formatterWithTime = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss");
    public static final SimpleDateFormat formatterWithoutTime = new SimpleDateFormat("yyyy-MM-dd");

}
