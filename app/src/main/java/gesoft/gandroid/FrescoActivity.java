package gesoft.gandroid;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.net.URI;

import butterknife.Bind;
import butterknife.ButterKnife;
import gesoft.gapp.common.GImage;
import gesoft.gapp.common.L;

public class FrescoActivity extends AppCompatActivity {

    @Bind(R.id.fresco)
    SimpleDraweeView fresco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_fresco);
            ButterKnife.bind(this);
        } catch (Exception e) {
            L.e(e);
        }
    }

    public void loadImg(View view){
        Uri uri = Uri.parse("/storage/sdcard0/dcim/map.jpg");
        GImage.loadImg(this, uri.getPath(), fresco);
    }


    public void localLargeImg(View view){
        try {
            Uri uri = Uri.parse("http://10.70.23.32/demo/map.jpg");
            //uri = Uri.parse("res://gesoft.gandroid/" + R.mipmap.map);

            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                    .setResizeOptions(new ResizeOptions(fresco.getMeasuredWidth()+100, fresco.getMeasuredHeight()+100))
                    .setImageType(ImageRequest.ImageType.SMALL)
                    .setAutoRotateEnabled(true)
                    .build();
            PipelineDraweeController controller = (PipelineDraweeController)Fresco.newDraweeControllerBuilder()
                    .setOldController(fresco.getController())
                    .setImageRequest(request)
                    .build();



            /*ControllerListener listener = new BaseControllerListener();
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setUri(uri)
                    .setTapToRetryEnabled(true)
                    .setOldController(fresco.getController())
                    .setControllerListener(listener)
                    .build();*/
            fresco.setController(controller);
        } catch (Exception e) {
            L.e(e);
        }
    }
}
