package org.example.service;

import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

public class MyDateFormat {


    public static String formatTaskTime(long duration){
        return String.format("%02d hrs, %02d min",
                TimeUnit.MILLISECONDS.toHours(duration),
                TimeUnit.MILLISECONDS.toMinutes(duration) -
                        TimeUnit.MILLISECONDS.toHours(duration) * 60);
    }
    public static String formatReleaseTime(long duration, String releaseVersion){
        return String.format("Release version: %s - %02d hrs, %02d min",
                releaseVersion,
                TimeUnit.MILLISECONDS.toHours(duration),
                TimeUnit.MILLISECONDS.toMinutes(duration) -
                        TimeUnit.MILLISECONDS.toHours(duration) * 60);
    }
    public static final SimpleDateFormat formatterWithTime = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss");
    public static final SimpleDateFormat formatterWithoutTime = new SimpleDateFormat("yyyy-MM-dd");

}
