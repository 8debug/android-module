package com.ybase.common;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by yhr on 2017/1/15.
 *
 */

public class YDate {

    public final static String FORMAT_DATE_SIMPLE = "yyyy-MM-dd";

    private static SimpleDateFormat getSimpleDateFormat( String format ){
        return new SimpleDateFormat(format, Locale.CHINESE);
    }

    public static String formatDate( Date date, String format ){
        return getSimpleDateFormat(format).format(date);
    }

    public static Date formatDate( String date, String format ){
        Date d = null;
        try {
            SimpleDateFormat sf = getSimpleDateFormat(format);
            d = sf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d;
    }


    public static int getAge( String strDate ){
        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINESE);
            Calendar dob = Calendar.getInstance();
            dob.setTime( sf.parse(strDate) );
            Calendar today = Calendar.getInstance();
            int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

            return age;
        } catch (ParseException e) {
            e.printStackTrace();
            Log.e("yhr", "getAge", e);
        }
        return -1;
    }


}
