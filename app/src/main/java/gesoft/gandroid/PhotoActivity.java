package gesoft.gandroid;

import android.app.Activity;
import android.content.Intent;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.lling.photopicker.GPhotoApplication;
import com.lling.photopicker.PhotoPickerActivity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import gesoft.gapp.common.GLAdapter;
import gesoft.gapp.common.L;
import gesoft.gapp.common.T;

public class PhotoActivity extends Activity {

    @Bind(R.id.fresco_demo)
    SimpleDraweeView frescoDemo;

    private int maxNum = 1;
    private final static int PICK_PHOTO = 1111;
    private String mPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        ButterKnife.bind(this);

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
    }

    public void readExif( View view ){

        try {
            /*ExifInterface exifInterface = new ExifInterface(mPath);
            String orientation = exifInterface.getAttribute(ExifInterface.TAG_ORIENTATION);
            String dateTime = exifInterface.getAttribute(ExifInterface.TAG_DATETIME);
            String make = exifInterface.getAttribute(ExifInterface.TAG_MAKE);
            String model = exifInterface.getAttribute(ExifInterface.TAG_MODEL);
            String flash = exifInterface.getAttribute(ExifInterface.TAG_FLASH);
            String imageLength = exifInterface.getAttribute(ExifInterface.TAG_IMAGE_LENGTH);
            String imageWidth = exifInterface.getAttribute(ExifInterface.TAG_IMAGE_WIDTH);
            String latitude = exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
            String longitude = exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
            String latitudeRef = exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF);
            String longitudeRef = exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF);
            String exposureTime = exifInterface.getAttribute(ExifInterface.TAG_EXPOSURE_TIME);
            String aperture = exifInterface.getAttribute(ExifInterface.TAG_APERTURE);
            String isoSpeedRatings = exifInterface.getAttribute(ExifInterface.TAG_ISO);
            String dateTimeDigitized = exifInterface.getAttribute(ExifInterface.TAG_DATETIME_DIGITIZED);
            String subSecTime = exifInterface.getAttribute(ExifInterface.TAG_SUBSEC_TIME);
            String subSecTimeOrig = exifInterface.getAttribute(ExifInterface.TAG_SUBSEC_TIME_ORIG);
            String subSecTimeDig = exifInterface.getAttribute(ExifInterface.TAG_SUBSEC_TIME_DIG);
            String altitude = exifInterface.getAttribute(ExifInterface.TAG_GPS_ALTITUDE);
            String altitudeRef = exifInterface.getAttribute(ExifInterface.TAG_GPS_ALTITUDE_REF);
            String gpsTimeStamp = exifInterface.getAttribute(ExifInterface.TAG_GPS_TIMESTAMP);
            String gpsDateStamp = exifInterface.getAttribute(ExifInterface.TAG_GPS_DATESTAMP);
            String whiteBalance = exifInterface.getAttribute(ExifInterface.TAG_WHITE_BALANCE);
            String focalLength = exifInterface.getAttribute(ExifInterface.TAG_FOCAL_LENGTH);
            String processingMethod = exifInterface.getAttribute(ExifInterface.TAG_GPS_PROCESSING_METHOD);
            String soft = exifInterface.getAttribute(ExifInterface.TAG_SOFTWARE);
            *//*String artist = exifInterface.getAttribute(ExifInterface.TAG_ARTIST);*//*

            Log.d("TAG", "## orientation=" + orientation);
            Log.d("TAG", "## dateTime=" + dateTime);
            Log.d("TAG", "## make=" + make);
            Log.d("TAG", "## model=" + model);
            Log.d("TAG", "## flash=" + flash);
            Log.d("TAG", "## imageLength=" + imageLength);
            Log.d("TAG", "## imageWidth=" + imageWidth);
            Log.d("TAG", "## latitude=" + latitude);
            Log.d("TAG", "## longitude=" + longitude);
            Log.d("TAG", "## latitudeRef=" + latitudeRef);
            Log.d("TAG", "## longitudeRef=" + longitudeRef);
            Log.d("TAG", "## exposureTime=" + exposureTime);
            Log.d("TAG", "## aperture=" + aperture);
            Log.d("TAG", "## isoSpeedRatings=" + isoSpeedRatings);
            Log.d("TAG", "## dateTimeDigitized=" + dateTimeDigitized);
            Log.d("TAG", "## subSecTime=" + subSecTime);
            Log.d("TAG", "## subSecTimeOrig=" + subSecTimeOrig);
            Log.d("TAG", "## subSecTimeDig=" + subSecTimeDig);
            Log.d("TAG", "## altitude=" + altitude);
            Log.d("TAG", "## altitudeRef=" + altitudeRef);
            Log.d("TAG", "## gpsTimeStamp=" + gpsTimeStamp);
            Log.d("TAG", "## gpsDateStamp=" + gpsDateStamp);
            Log.d("TAG", "## whiteBalance=" + whiteBalance);
            Log.d("TAG", "## focalLength=" + focalLength);
            Log.d("TAG", "## processingMethod=" + processingMethod);
            Log.d("TAG", "## soft=" + soft);*/

            ExifInterface exif = new ExifInterface(mPath);
            StringBuilder sBuilder = new StringBuilder();
            sBuilder.append("\n光圈：" + exif.getAttribute(ExifInterface.TAG_APERTURE));//2.0
            sBuilder.append("\n拍摄日期：" + exif.getAttribute(ExifInterface.TAG_DATETIME));//2016:05:15 21:34:21
            sBuilder.append("\n曝光时间：" + exif.getAttribute(ExifInterface.TAG_EXPOSURE_TIME));// 0.030
            sBuilder.append("\n是否有闪光灯：" + exif.getAttribute(ExifInterface.TAG_FLASH));// 0
            sBuilder.append("\n焦距： " + exif.getAttribute(ExifInterface.TAG_FOCAL_LENGTH));//4620/1000
            sBuilder.append("\n海拔： " + exif.getAttribute(ExifInterface.TAG_GPS_ALTITUDE));//0/100
            sBuilder.append("\n海拔参数：" + exif.getAttribute(ExifInterface.TAG_GPS_ALTITUDE_REF));// 1
            sBuilder.append("\n时间戳：" + exif.getAttribute(ExifInterface.TAG_GPS_DATESTAMP));// 2016:05:15
            sBuilder.append("\n维度： " + exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE));//23/1,5/1,45224761/1000000
            sBuilder.append("\n南半球还是北半球：" + exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF));// N
            sBuilder.append("\n经度： " + exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE));//113/1,19/1,23122558/1000000
            sBuilder.append("\n东区还是西区：" + exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF));// E
            sBuilder.append("\n高： " + exif.getAttribute(ExifInterface.TAG_IMAGE_LENGTH));//5152
            sBuilder.append("\n宽： " + exif.getAttribute(ExifInterface.TAG_IMAGE_WIDTH));//3888
            sBuilder.append("\n感光度： " + exif.getAttribute(ExifInterface.TAG_ISO));//200
            sBuilder.append("\n生产厂家：" + exif.getAttribute(ExifInterface.TAG_MAKE));// HUAWEI
            sBuilder.append("\n设备型号： " + exif.getAttribute(ExifInterface.TAG_MODEL));//PLK-AL10
            sBuilder.append("\n旋转角度： " + exif.getAttribute(ExifInterface.TAG_ORIENTATION));//1
            sBuilder.append("\n白平衡：" + exif.getAttribute(ExifInterface.TAG_WHITE_BALANCE));// 0
            //sBuilder.append("\n旋转角度为：" + readPictureDegree(Environment.getExternalStorageDirectory().getPath() + "/pic.jpg") + "°");

        } catch (IOException e) {
            L.e(e);
        }
    }

    public void writeExif( View view ){
        try {
            ExifInterface exifInterface = new ExifInterface(mPath);
            exifInterface.setAttribute(ExifInterface.TAG_ORIENTATION, "0");
            exifInterface.setAttribute( ExifInterface.TAG_DATETIME , "2016:09:19 14:17:44" );
            exifInterface.setAttribute( ExifInterface.TAG_MAKE, "XiaomiYhr");
            exifInterface.setAttribute( ExifInterface.TAG_MODEL, "MI 2" );
            exifInterface.setAttribute(ExifInterface.TAG_FLASH, "16");
            exifInterface.setAttribute(ExifInterface.TAG_IMAGE_LENGTH, "4608");
            exifInterface.setAttribute(ExifInterface.TAG_IMAGE_WIDTH, "3456");
            exifInterface.setAttribute( ExifInterface.TAG_GPS_LATITUDE, "41/1,48/1,580364/10000" );
            exifInterface.setAttribute( ExifInterface.TAG_GPS_LONGITUDE, "123/1,26/1,510408/10000" );

            exifInterface.saveAttributes();
            readExif(null);
        } catch (IOException e) {
            L.e(e);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        try {
            if (RESULT_OK == resultCode && requestCode == PICK_PHOTO) {
                ArrayList<String> array = data.getStringArrayListExtra(PhotoPickerActivity.KEY_RESULT);
                for (String path : array) {
                    mPath = path;
                    File file = new File(mPath);
                    frescoDemo.setImageURI(Uri.fromFile(file));
                    //writeExif( path );
                }
            }
        } catch (Exception e) {
            L.e(e);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
