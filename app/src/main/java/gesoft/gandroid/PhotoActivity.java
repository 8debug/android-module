package gesoft.gandroid;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.lling.photopicker.GPhotoApplication;
import com.lling.photopicker.PhotoPickerActivity;

import java.io.File;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import gesoft.gapp.common.GLAdapter;
import gesoft.gapp.common.L;

public class PhotoActivity extends Activity {

    @Bind(R.id.list)
    ListView list;
    @Bind(R.id.fresco_demo)
    SimpleDraweeView frescoDemo;

    private int maxNum = 1;
    private final static int PICK_PHOTO = 1111;
    GLAdapter<Uri> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        ButterKnife.bind(this);

        GPhotoApplication.setContext(getApplicationContext());

        frescoDemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PhotoActivity.this, PhotoPickerActivity.class);
                intent.putExtra(PhotoPickerActivity.EXTRA_SHOW_CAMERA, true);
                intent.putExtra(PhotoPickerActivity.EXTRA_SELECT_MODE, PhotoPickerActivity.MODE_MULTI);
                intent.putExtra(PhotoPickerActivity.EXTRA_MAX_MUN, maxNum);
                startActivityForResult(intent, PICK_PHOTO);
            }
        });

        mAdapter = new GLAdapter<Uri>(R.layout.adapter_photo) {
            @Override
            public void convert(GViewHolder helper, Uri uri) {
                SimpleDraweeView fresco = helper.getView(R.id.fresco);
                fresco.setImageURI(uri);
                fresco.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), PhotoPickerActivity.class);
                        intent.putExtra(PhotoPickerActivity.EXTRA_SHOW_CAMERA, true);
                        intent.putExtra(PhotoPickerActivity.EXTRA_SELECT_MODE, PhotoPickerActivity.MODE_SINGLE);
                        intent.putExtra(PhotoPickerActivity.EXTRA_MAX_MUN, maxNum);
                        startActivityForResult(intent, PICK_PHOTO);
                    }
                });
            }
        };
        list.setAdapter(mAdapter);
        //initScan();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        try {
            if (RESULT_OK == resultCode && requestCode == PICK_PHOTO) {
                ArrayList<String> array = data.getStringArrayListExtra(PhotoPickerActivity.KEY_RESULT);
                for (String path : array) {
                    File file = new File(path);
                    frescoDemo.setImageURI(Uri.fromFile(file));
                    mAdapter.add(Uri.fromFile(file));
                }
                mAdapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            L.e(e);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
