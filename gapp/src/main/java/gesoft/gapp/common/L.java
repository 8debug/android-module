package gesoft.gapp.common;

import android.util.Log;

/**
 * Created by yhr on 2016/3/17.
 */
public class L {

    private final static String TAG_ERROR = "error_ge";

    private final static String TAG_DEBUG = "debug_ge";

    private final static String MSG = "gesoft";

    private final static boolean IS_DEBUG = true;

    public static void error(Exception e){
        if( IS_DEBUG ){
            Log.e(TAG_ERROR, MSG, e);
        }
    }

    public static void error(String tag, String msg, Exception e){
        if( IS_DEBUG ){
            Log.e(tag, msg, e);
        }
    }

    public static void error(String tag, String msg, Throwable e){
        if( IS_DEBUG ){
            Log.e(tag, msg, e);
        }
    }

    public static void e(Exception e){
        if( IS_DEBUG ){
            error(TAG_ERROR, MSG, e);
        }
    }

    public static void e(Throwable e){
        if( IS_DEBUG ){
            error(TAG_ERROR, MSG, e);
        }
    }

    public static void e(String tag, String msg, Exception e){
        if( IS_DEBUG ){
            error(tag, msg, e);
        }
    }

    public static void debug(Object msg){
        if( IS_DEBUG ){
            Log.d(TAG_DEBUG, String.valueOf(msg));
        }
    }

    public static void d(Object msg){
        if( IS_DEBUG ){
            debug(String.valueOf(msg));
        }
    }
}
