package com.ybase.common.toast;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class T {
 
	static Toast toast;

	public static void show( Context context, String msg ){
		if( toast==null ){
			toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
		}
		toast.setText(msg);
		toast.show();
	}

} 