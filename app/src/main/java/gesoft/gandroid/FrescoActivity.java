package gesoft.gandroid;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import gesoft.gapp.common.GImage;
import gesoft.gapp.common.L;
import gesoft.gapp.common.T;

public class FrescoActivity extends AppCompatActivity {

    @Bind(R.id.fresco)
    SimpleDraweeView fresco;
    @Bind(R.id.img)
    ImageView img;

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

    public void loadImg(View view) {
        String strUri = "/storage/sdcard0/dcim/map.jpg";
        File file = new File(strUri);
        if (file.exists()) {
            Uri uri = Uri.fromFile(file);
            GImage.loadImg(this, uri.getPath(), fresco);
        } else {
            T.show(this, strUri + "  不存在");
        }

        Uri uri = Uri.parse("http://10.70.23.32/demo/test.jpeg");
        fresco.setImageURI(uri);
    }

    public void nextImg(View view) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.c);

        int width = img.getMeasuredWidth();
        int height = img.getMeasuredHeight();

        Paint paint = new Paint();
        Rect rect = new Rect();
        int textSize = 20;
        String text;
        float textWidth;

        /*text = "left-top";
        bitmap = GImage.addWaterMarker(bitmap, text, textSize, 0, textSize, width, height);

        text = "left-bottom";
        bitmap = GImage.addWaterMarker(bitmap, text, textSize, 0, height, width, height);

        text = "right-top";
        textWidth = paint.measureText(text, 0, text.length());
        //float textWidth = paint.measureText("this is a right-top");
        bitmap = GImage.addWaterMarker(bitmap, text, textSize, width-textWidth, textSize, width, height);*/

        text = "right-top";
        //textWidth = paint.measureText(text, 0, text.length());
        bitmap = GImage.addWaterMarker(bitmap, text, textSize, width-rect.width()-rect.left-rect.right, textSize, width, height);

        text = "right-bottom";
        paint.getTextBounds(text, 0, text.length(), rect);
        //textWidth = paint.measureText(text, 0, text.length());
        L.d("width=="+width+", rect_centerX=="+rect.centerX()+", rect_width=="+rect.width()+", rect_left=="+rect.left+", rect_right=="+rect.right+", rect_height="+rect.height()+", height=="+height+", rect_top=="+rect.top+", rect_bottom=="+rect.bottom);
        bitmap = GImage.addWaterMarker(bitmap, text, textSize, width-rect.width()-rect.right, height-textSize, width, height);
        img.setImageBitmap(bitmap);
        //fresco.setImageBitmap(bitmap);
    }

    public static int getTextWidth(Paint paint, String str) {
        int iRet = 0;
        if (str != null && str.length() > 0) {
            int len = str.length();
            float[] widths = new float[len];
            paint.getTextWidths(str, widths);
            for (int j = 0; j < len; j++) {
                iRet += (int) Math.ceil(widths[j]);
            }
        }
        return iRet;
    }


    public void localLargeImg(View view) {
        try {
            Uri uri = Uri.parse("http://10.70.23.32/demo/test.jpeg");
            //uri = Uri.parse("res://gesoft.gandroid/" + R.mipmap.map);

            /*ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                    .setResizeOptions(new ResizeOptions(fresco.getMeasuredWidth()+100, fresco.getMeasuredHeight()+100))
                    .setImageType(ImageRequest.ImageType.SMALL)
                    .setAutoRotateEnabled(true)
                    .build();
            PipelineDraweeController controller = (PipelineDraweeController)Fresco.newDraweeControllerBuilder()
                    .setOldController(fresco.getController())
                    .setImageRequest(request)
                    .build();*/



            /*ControllerListener listener = new BaseControllerListener();
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setUri(uri)
                    .setTapToRetryEnabled(true)
                    .setOldController(fresco.getController())
                    .setControllerListener(listener)
                    .build();*/
            // fresco.setController(controller);
        } catch (Exception e) {
            L.e(e);
        }
    }
}
