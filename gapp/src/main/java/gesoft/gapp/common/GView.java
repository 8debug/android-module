package gesoft.gapp.common;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioGroup;

import java.util.Calendar;

/**
 * Created by yhr on 2016/5/12.
 */
public class GView {

    /**
     * 获取焦点
     * @param views
     */
    public static void setFocuse(View ...views){
        for (View view : views) {
            view.setFocusable(true);
            view.setFocusableInTouchMode(true);
        }
    }

    /**
     * 日历dialog
     * @param ctx
     * @param datePickerInterface
     * @return
     */
    public static DatePickerDialog getDatePicker(Context ctx, final DatePickerInterface datePickerInterface ){
        DatePickerDialog datePickerDialog = new DatePickerDialog(ctx, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar cal = Calendar.getInstance();
                cal.set(year, monthOfYear, dayOfMonth);
                CharSequence date = GDate.getDate("yyyy-MM-dd", cal);
                datePickerInterface.onSetDate(date);
            }
        }, GDate.getYear(), GDate.getMonth(), GDate.getDay());
        return datePickerDialog;
    }

    public interface DatePickerInterface{
        void onSetDate(CharSequence date);
    }

    /**
     * 启用/禁用RadioGroup
     * @param group
     * @param isEnabled
     */
    public static void setEnabledRadioGroup( RadioGroup group, boolean isEnabled ){
        for (int i = 0, n = group.getChildCount(); i <n ; i++) {
            group.getChildAt(i).setEnabled(isEnabled);
        }
    }


}
