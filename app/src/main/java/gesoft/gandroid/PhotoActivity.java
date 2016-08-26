package gesoft.gandroid;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.lling.photopicker.GPhotoApplication;
import com.lling.photopicker.PhotoPickerActivity;

import java.io.File;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import gesoft.gapp.common.L;
import gesoft.gapp.common.T;

public class PhotoActivity extends Activity {

    @Bind(R.id.fresco_demo)
    SimpleDraweeView fresco;

    private int maxNum = 1;
    private final static int PICK_PHOTO = 1111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        ButterKnife.bind(this);

        GPhotoApplication.setContext(getApplicationContext());

        fresco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PhotoPickerActivity.class);
                intent.putExtra(PhotoPickerActivity.EXTRA_SHOW_CAMERA, true);
                intent.putExtra(PhotoPickerActivity.EXTRA_SELECT_MODE, PhotoPickerActivity.MODE_MULTI);
                intent.putExtra(PhotoPickerActivity.EXTRA_MAX_MUN, maxNum);
                startActivityForResult(intent, PICK_PHOTO);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        try {
            if (RESULT_OK == resultCode && requestCode == PICK_PHOTO) {
                ArrayList<String> array = data.getStringArrayListExtra(PhotoPickerActivity.KEY_RESULT);
                String strUri = array.get(0);
                File file = new File(strUri);
                T.show(this, "file="+file.exists()+",  strUri="+strUri);
                fresco.setImageURI( strUri );
            }
        } catch (Exception e) {
            L.e(e);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
