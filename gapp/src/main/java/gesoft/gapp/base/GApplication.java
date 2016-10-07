package gesoft.gapp.base;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.decoder.SimpleProgressiveJpegConfig;


public class GApplication extends Application {

	private static Context applicationContext;

	@Override
	public void onCreate() {
		super.onCreate();
	}

	public static Context getContext(){
		return applicationContext;
	}

	public static void setContext( Context context ){
		applicationContext = context;
		ImagePipelineConfig config = ImagePipelineConfig.newBuilder(context)
				.setProgressiveJpegConfig(new SimpleProgressiveJpegConfig())
				.build();
		Fresco.initialize(context,config);
	}
}
