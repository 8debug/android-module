package gesoft.gbmap;

import android.content.Context;
import android.content.res.Resources;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.Poi;

import java.util.List;

import gesoft.gbmap.base.GBMapApplication;
import gesoft.gbmap.service.LocationService;

import static gesoft.gbmap.base.GBMapApplication.locationService;

/**
 * Created by yhr on 2016/6/30.
 * 定位封装类
 */

public class GBLocation {

    private LocationService locationService;

    IGBLocation mIGBLocation;

    public void setIGBLocation(IGBLocation igbLocation){
        mIGBLocation = igbLocation;
    }

    public interface IGBLocation{
        void onStart();
        void onFinish(boolean isSuccess, GLBean bean);
    }

    private static String str(Object obj){
        return String.valueOf(obj);
    }

    static boolean isNumber(String str){
        try {
            Double.valueOf(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    static boolean isNotNumber(String str){
        return !isNumber(str);
    }

    public GBLocation(){
        locationService = GBMapApplication.locationService;
        initLocation();
    }

    public static void setApplication(Context context){
        GBMapApplication.setApplicationContext(context);
    }

    /**
     * 初始化定位，注册监听+设置定位参数
     */
    public void initLocation(){
        locationService.registerListener(mListener);
        locationService.setLocationOption(locationService.getDefaultLocationClientOption());
    }

    /**
     * 初始化定位，反注册监听+停止定位服务
     */
    public void destoryLocation(){
        //注销掉监听
        locationService.unregisterListener(mListener);
        //停止定位服务
        locationService.stop();
    }

    /**
     * 启动定位
     */
    public void start(){
        if( mIGBLocation!=null )mIGBLocation.onStart();
        stop();
        locationService.start();
    }

    /**
     * 停止定位
     */
    public void stop(){
        locationService.stop();
    }

    private BDLocationListener mListener = new BDLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // TODO Auto-generated method stub
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {


                String mStrLng = str(location.getLongitude());
                String mStrLat = str(location.getLatitude());

                GLBean bean = new GLBean();
                bean.setLat(location.getLatitude());
                bean.setLng(location.getLongitude());
                bean.setAddress(location.getAddrStr());
                bean.setCity(location.getCity());
                bean.setCountry(location.getCountry());
                bean.setDiscribe(location.getLocationDescribe());
                bean.setDistrict(location.getDistrict());
                bean.setStreet(location.getStreet());
                bean.setTime(location.getTime());
                bean.setListPoi(location.getPoiList());

                Boolean isSuccess = true;
                String msg = "";

                /*mStrRadius = str(location.getRadius());
                mCountry = location.getCountry();
                mCity = location.getCity();
                mDistrict = location.getDistrict();
                mStreet = location.getStreet();
                mAddress = location.getAddrStr();
                mDiscribe = location.getLocationDescribe();
                mListPoi = location.getPoiList();*/


                Resources res = GBMapApplication.instance.getResources();

                if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                    msg = res.getString(R.string.bd_location_success_gps);
                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                    msg = res.getString(R.string.bd_location_success_network);
                } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                    msg = res.getString(R.string.bd_location_success_offline);
                } else if (location.getLocType() == BDLocation.TypeServerError) {
                    msg = res.getString(R.string.bd_location_error_server);
                    isSuccess = false;
                } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                    msg = res.getString(R.string.bd_location_error_netWork);
                    isSuccess = false;
                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                    msg = res.getString(R.string.bd_location_error_criteria);
                    isSuccess = false;
                }

                if( mIGBLocation!=null ){
                    bean.setMsg(msg);
                    isSuccess = ( isSuccess && isNumber(mStrLng) && isNumber(mStrLat) );
                    mIGBLocation.onFinish(isSuccess, bean);
                }


            }
        }
    };


    /**
     * 定位结果Bean
     */
    public class GLBean{
        private String time;
        private Double lng;
        private Double lat;
        private Float radius;
        private String city;
        private String country;
        private String district;
        private String street;
        private String address;
        private String discribe;
        private String msg;
        private List<Poi> listPoi;

        public List<Poi> getListPoi() {
            return listPoi;
        }

        public void setListPoi(List<Poi> listPoi) {
            this.listPoi = listPoi;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public Double getLng() {
            return lng;
        }

        public void setLng(Double lng) {
            this.lng = lng;
        }

        public Double getLat() {
            return lat;
        }

        public void setLat(Double lat) {
            this.lat = lat;
        }

        public Float getRadius() {
            return radius;
        }

        public void setRadius(Float radius) {
            this.radius = radius;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getDiscribe() {
            return discribe;
        }

        public void setDiscribe(String discribe) {
            this.discribe = discribe;
        }


    }

}
