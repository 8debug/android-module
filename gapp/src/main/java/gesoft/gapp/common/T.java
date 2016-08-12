package gesoft.gapp.common;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import gesoft.gapp.base.GApplication;


/**
 * 重新封装Toast
 * @author yuezg
 * @date 2015年11月5日
 */
public class T {

	private static Context mContext;

	public static void setContext( Context ctx ){
		mContext = ctx;
	}
	
	/**
	 * 
	 * @param context
	 * @param text
	 * @author Administrator
	 * @date 2015年11月5日
	 */
	public static void show(Context context, String text){
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}
	
	public static void show(Object msg){
		T.show( mContext, String.valueOf(msg));
	}
	
	/**
	 * 
	 * @param context
	 * @param text
	 * @param length
	 * @author Administrator
	 * @date 2015年11月5日
	 */
	public static void show(Context context, String text, Integer length){
		if(length == null){
			show(context, text);
			return;
		}
		Toast.makeText(context, text, length).show();
	}
	
	/**
	 * 
	 * @param context
	 * @param text
	 * @author Administrator
	 * @date 2015年11月5日
	 */
	public static void showAtCenter(Context context, String text){
		Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
	
	/**
	 * 
	 * @param context
	 * @param text
	 * @param length
	 * @author Administrator
	 * @date 2015年11月5日
	 */
	public static void showAtCenter(Context context, String text, Integer length){
		if(length == null){
			showAtCenter(context, text);
			return;
		}
		Toast toast = Toast.makeText(context, text, length);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
}
