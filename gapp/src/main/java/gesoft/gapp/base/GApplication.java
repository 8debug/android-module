package gesoft.gapp.base;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.decoder.SimpleProgressiveJpegConfig;


public class GApplication extends Application {

	public static GApplication instance;

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
													.setProgressiveJpegConfig(new SimpleProgressiveJpegConfig())
													.build();
		Fresco.initialize(instance,config);

	}


}
