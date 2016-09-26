package gesoft.gandroid;


import android.app.Fragment;
import android.content.Intent;
import android.media.ExifInterface;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.lling.photopicker.PhotoPickerActivity;

import java.util.ArrayList;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

import gesoft.gapp.common.GDate;
import gesoft.gapp.common.L;
import gesoft.gapp.common.T;
import gesoft.gapp.databases.camera.GCameraPhoto;
import gesoft.gapp.databases.camera.GCameraSQLHelper;
import gesoft.gbmap.GBLocation;
import gesoft.gbmap.fragment.GBLocationFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class BLocationFragment extends GBLocationFragment {

    public final static String TAG = BLocationFragment.class.getName();
    private final static int PICK_PHOTO = 2222;
    private GCameraSQLHelper mSqlHelper;
    GBLocation.GLBean mLocationBean;
    ArrayList<String> mArray;

    @Bind(R.id.btn)
    Button btn;
    @Bind(R.id.btn_camera)
    Button btnCamera;
    @Bind(R.id.btn_location)
    Button btnLocation;
    @Bind(R.id.tv)
    TextView tv;


    @Override
    protected View onCreateViewG(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blocation, container, false);
        ButterKnife.bind(this, view);
        mSqlHelper = GCameraSQLHelper.getInstance(getActivity(), 1);
        //setLocationDevice(true);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLocation();
            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPhotos();
            }
        });

        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuffer buffer = new StringBuffer();
                for (String path : mArray) {
                    GCameraPhoto photo = mSqlHelper.queryLocation(path);
                    if( photo!=null ){
                        String time = DateUtils.formatDateTime(getActivity(), photo.getTime(),DateUtils.FORMAT_SHOW_DATE);
                        String str = photo.getLng() + ", " + photo.getLat() + ", " + photo.getPath() + ", " + time;
                        buffer.append(str);
                    }else{
                        T.show("图片中未找到位置信息");
                    }
                }
                tv.setText(buffer.toString());
            }
        });

        return view;
    }

    public BLocationFragment() {
        // Required empty public constructor
    }

    public void openPhotos( ){
        startLocation();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (PhotoPickerActivity.RESULT_OK == resultCode && requestCode == PICK_PHOTO) {
                mArray = data.getStringArrayListExtra(PhotoPickerActivity.KEY_RESULT);
                int action = data.getIntExtra(PhotoPickerActivity.KEY_ACTION, 0);
                if( action==PhotoPickerActivity.ACTION_CAMERA ){
                    for (String path : mArray) {
                        ExifInterface exifInterface = new ExifInterface(path);
                        /*exifInterface.setAttribute( ExifInterface.TAG_GPS_LATITUDE, String.valueOf(mLocationBean.getLat()) );
                        exifInterface.setAttribute( ExifInterface.TAG_GPS_LONGITUDE, String.valueOf(mLocationBean.getLng()) );*/
                        exifInterface.setAttribute( ExifInterface.TAG_DATETIME , GDate.getDate("yyyy-MM-dd HH:mm",new Date()) );
                        exifInterface.setAttribute( ExifInterface.TAG_GPS_ALTITUDE,"1/1000");
                        /*exifInterface.setAttribute(ExifInterface.TAG_ORIENTATION,"6");
                        exifInterface.setAttribute(ExifInterface.TAG_IMAGE_WIDTH,"2000");*/
                        exifInterface.saveAttributes();
                        //mSqlHelper.insertLocation(mLocationBean.getLng(), mLocationBean.getLat(), path, new Date().getTime());
                    }
                }
            }
        } catch (Exception e) {
            L.e(e);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onLocationStart() {
        btn.setText("正在综合定位...");
    }

    @Override
    public void onLocationFinish(boolean isSuccess, GBLocation.GLBean bean) {
        stopLocation();
        mLocationBean = bean;
        tv.setText(bean.getLat()+", "+bean.getLng()+" address="+bean.getDiscribe()+"  "+bean.getMsg());
        btn.setText("开始定位");

        mSqlHelper.getWritableDatabase();
        Intent intent = new Intent(getActivity(), PhotoPickerActivity.class);
        intent.putExtra(PhotoPickerActivity.EXTRA_SHOW_CAMERA, true);
        intent.putExtra(PhotoPickerActivity.EXTRA_SELECT_MODE, PhotoPickerActivity.MODE_MULTI);
        intent.putExtra(PhotoPickerActivity.EXTRA_MAX_MUN, 3);
        startActivityForResult(intent, PICK_PHOTO);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
