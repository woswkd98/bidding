package com.example.demo.Config;

import java.text.*;
import java.util.Date;
import java.util.TimeZone;

public class GetTimeZone {

    private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static DateFormat dfOnlyDate = new SimpleDateFormat("yyyy-MM-dd");
    private static TimeZone seoulTime = TimeZone.getTimeZone("Asia/Seoul");

    public static String getSeoulDate() {

        Date date = new Date();

        df.setTimeZone(seoulTime);
        return df.format(date);
    }

    public static String getTimeToDate(long t) {

        Date date = new Date();
        date.setTime(t);

        df.setTimeZone(seoulTime);
        return df.format(date);
    }

    public static Long DateToGetTime(Date date) {

        return date.getTime();
    }

    public static Date StringToDate(String Date) {

        try {
            return df.parse(Date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    } 


}