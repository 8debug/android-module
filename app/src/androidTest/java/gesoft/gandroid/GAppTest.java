package gesoft.gandroid;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.text.format.DateUtils;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import gesoft.gapp.common.GDate;

import static org.junit.Assert.assertEquals;

/**
 * Created by Administrator on 2016/9/23.
 */
@RunWith(AndroidJUnit4.class)
public class GAppTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        /*List<Integer> listInt = new ArrayList<>();
        listInt.add( DateUtils.FORMAT_ABBREV_ALL );
        listInt.add( DateUtils.FORMAT_ABBREV_MONTH );
        listInt.add( DateUtils.FORMAT_ABBREV_RELATIVE );
        listInt.add( DateUtils.FORMAT_ABBREV_TIME );
        listInt.add( DateUtils.FORMAT_NO_MIDNIGHT );

        for (Integer integer : listInt) {
            CharSequence str = GDate.getDate("yyyy-MM-dd HH:mm",new Date());
            Log.d("yhr_test", String.valueOf(str));
        }*/
        CharSequence str = GDate.getDate("yyyy-MM-dd HH:mm",new Date());
        Log.d("yhr_test", String.valueOf(str));
        //assertEquals("gesoft.demomockito", appContext.getPackageName());
    }
}
