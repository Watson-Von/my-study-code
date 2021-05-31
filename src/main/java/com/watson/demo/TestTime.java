package com.watson.demo;

import java.util.Date;
import java.util.GregorianCalendar;

public class TestTime {

    public static void main(String[] args) {


        long sd = System.currentTimeMillis();
        sd += 60000L * 60L * 2L;
        Date dat = new Date(sd);
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(dat);
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sb = format.format(gc.getTime());
        System.out.println(sb);

    }

}
