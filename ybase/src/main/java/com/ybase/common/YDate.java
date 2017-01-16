package com.ybase.common;

import android.content.Context;
import android.text.format.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by yhr on 2017/1/15.
 *
 */

public class YDate {

    public final static String FORM_SIMPLE_DATE = "yyyy-MM-dd";

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


}
