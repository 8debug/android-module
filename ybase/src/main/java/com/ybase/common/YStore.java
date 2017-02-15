package com.ybase.common;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

/**
 * Created by yhr on 2017/2/15.
 *
 */

public class YStore {

    public static void save( Context context, String name,  String key, Object obj ){
        SharedPreferences shared = context.getSharedPreferences( name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        String value = String.valueOf(obj);
        if( obj instanceof String ){
            editor.putString(key, value);
        }else if( obj instanceof Integer ){
            editor.putInt(key, Integer.valueOf(value));
        }else if( obj instanceof Boolean ){
            editor.putBoolean(key, Boolean.valueOf(value));
        }else if( obj instanceof Float ){
            editor.putFloat(key, Float.valueOf(value));
        }else if( obj instanceof Long ){
            editor.putLong(key, Long.valueOf(value));
        }else if( obj instanceof Set){
            editor.putStringSet(key, (Set<String>)obj);
        }
        editor.apply();
    }

    public static Object getValue( Context context, String name, String key, Object obj ){
        SharedPreferences shared = context.getSharedPreferences( name, Context.MODE_PRIVATE);
        String value = String.valueOf(obj);
        if( obj instanceof String ){
            return shared.getString(key, value);
        }else if( obj instanceof Integer ){
            return shared.getInt(key, Integer.valueOf(value));
        }else if( obj instanceof Boolean ){
            return shared.getBoolean(key, Boolean.valueOf(value));
        }else if( obj instanceof Float ){
            return shared.getFloat(key, Float.valueOf(value));
        }else if( obj instanceof Long ){
            return shared.getLong(key, Long.valueOf(value));
        }else if( obj instanceof Set ){
            return shared.getStringSet(key,(Set<String>)obj );
        }
        return null;
    }

}
