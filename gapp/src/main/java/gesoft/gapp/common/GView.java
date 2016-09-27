package gesoft.gapp.common;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.CountDownTimer;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

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
        group.setEnabled(isEnabled);
        for (int i = 0, n = group.getChildCount(); i <n ; i++) {
            group.getChildAt(i).setEnabled(isEnabled);
        }
    }

    /**
     * 弹出软键盘
     * @param editText  控件
     */
    public static void showSoftInput( final EditText editText){
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        InputMethodManager inputManager = (InputMethodManager)editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(editText, 0);
        /*Timer timer = new Timer();
        timer.schedule(new TimerTask(){
           public void run(){
               InputMethodManager inputManager = (InputMethodManager)editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
               inputManager.showSoftInput(editText, 0);
               //inputManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
           }
       }, 998);*/
    }

    /**
     * 关闭软键盘
     * toggleSoftInput成对出现才能实现关闭效果
     * @param context
     */
    public static void hideSoftInput( Context context ){
        InputMethodManager inputManager = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }
}
