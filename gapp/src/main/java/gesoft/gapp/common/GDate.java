package gesoft.gapp.common;

import android.text.format.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Administrator on 2016/5/27.
 */
public class GDate {

    public static String getDate(String format, Date date){
        CharSequence str = DateFormat.format(format, date);
        return String.valueOf(str);
    }

    public static String getDate(String format, Calendar calendar){
        CharSequence str = DateFormat.format(format, calendar);
        return String.valueOf(str);
    }

    public static String getNowDate(String format){
        return getDate(format, new Date());
    }

    /**
     * 获取当前天是当前月的几号
     * @return
     */
    public static int getDay(){
        return getCal().get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取当前天是当前月
     * @return
     */
    public static int getMonth(){
        return getCal().get(Calendar.MONTH);
    }
    /**
     * 获取当前天是当前年
     * @return
     */
    public static int getYear(){
        return getCal().get(Calendar.YEAR);
    }

    static Calendar getCal(){
        return Calendar.getInstance();
    }

    /**
     * strDate2>strDate1 >0   strDate2==strDate1 = 0     strDate2<strDate1 <-1
     * @param strDate1
     * @param strDate2
     * @return
     */
    public static int compareDate(String format, String strDate1, String strDate2){
        Calendar cal1 = getCal();
        Calendar cal2 = getCal();
        try{
            Date date1 = parse(format, strDate1);
            Date date2 = parse(format, strDate2);

            cal1.setTime(date1);
            cal2.setTime(date2);

        }catch (Exception e){
            L.e(e);
        }
        return cal2.compareTo(cal1);
    }

    /**
     * strDate转Date
     * @param format
     * @param strDate
     * @return
     */
    public static Date parse(String format, String strDate){
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINESE);
            date =  sdf.parse(strDate);
        } catch (ParseException e) {
            L.e(e);
        }
        return date;
    }

}
