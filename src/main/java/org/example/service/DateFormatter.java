package org.example.service;

import java.text.SimpleDateFormat;

public interface DateFormatter {

    SimpleDateFormat formatterWithTime = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss");
    SimpleDateFormat formatterWithoutTime = new SimpleDateFormat("yyyy-MM-dd");

}
