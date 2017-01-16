package com.ybase.common.toast;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class T {
 
	private static Context mContext;
 
	public static void setContext( Context ctx ){
		mContext = ctx;
	}

	public static void show( String msg){
		if( mContext!=null ){
			show(mContext, msg);
		}
	}
	 
	/** 
	 *  
	 * @param context 
	 * @param text 
	 * @author Administrator 
	 * @date 2015年11月5日 
	 */ 
	private static void show(Context context, String text){
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}
	 
	/** 
	 *  
	 * @param text
	 * @param length 
	 * @author Administrator 
	 * @date 2015年11月5日 
	 */ 
	public static void show( String text, Integer length){
		Toast.makeText( mContext, text, length).show();
	} 

} 