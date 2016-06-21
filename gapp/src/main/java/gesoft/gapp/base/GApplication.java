package gesoft.gapp.base;

import android.app.Application;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.decoder.SimpleProgressiveJpegConfig;


public class GApplication extends Application {

	/*public Vibrator mVibrator;
	public LocationService locationService;*/
	public static Context applicationContext;
	private static GApplication instance;
	public static RequestQueue requestQueue;
	private static int REQUEST_COUNTER = 0;

	public static RequestQueue getRequestQueue(){
		return requestQueue;
	}
	
	public static void addRequestQueue( Request<?> request ){
		REQUEST_COUNTER++;
		getRequestQueue().add(request);
	}
	
	public static void finishCurrentRequest(){
		REQUEST_COUNTER--;
	}
	
	public static boolean isFinishRquests(){
		if( REQUEST_COUNTER<=0 ){
			REQUEST_COUNTER = 0;
		}
		return REQUEST_COUNTER==0;
	}
	
	/**
	 * 判断请求全部结束
	 * @return
	 */
	/*public boolean isRequestsFinish(){
		return REQUEST_COUNTER==0;
	}*/
	
	
	public static GApplication getInstance() {
		return instance;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		applicationContext = this;
		instance = this;
		requestQueue = Volley.newRequestQueue(instance);
		ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
													.setProgressiveJpegConfig(new SimpleProgressiveJpegConfig())
													.build();
		Fresco.initialize(instance,config);

		/***
		 * 初始化定位sdk，建议在Application中创建
		 */
		/*locationService = new LocationService(getApplicationContext());
		mVibrator =(Vibrator)getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
		com.lling.photopicker.Application.setContext(this);*/
		//SDKInitializer.initialize(getApplicationContext());
	}


}
