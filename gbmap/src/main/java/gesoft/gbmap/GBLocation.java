package gesoft.gbmap;

import android.content.Context;
import android.content.res.Resources;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.location.Poi;

import java.util.ArrayList;
import java.util.List;

import gesoft.gbmap.base.GBMapApplication;
import gesoft.gbmap.service.LocationService;

/**
 * Created by yhr on 2016/6/30.
 * 定位封装类
 */

public class GBLocation {

    private LocationService locationService;

    IGBLocation mIGBLocation;

    private boolean mIsGPS = false;

    public void setIGBLocation(IGBLocation igbLocation){
        mIGBLocation = igbLocation;
    }

    public interface IGBLocation{
        /**
         * 定位开始前
         */
        void onLocationStart();

        /**
         * 定位结束后
         * @param isSuccess 判断是否定位成功
         * @param bean  定位成功返回定位信息，否则返回null
         */
        void onLocationFinish(boolean isSuccess, GLBean bean);
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

    public GBLocation( Context applicationContext){
        setApplication(applicationContext);
        locationService = new LocationService( applicationContext );
        //initLocation();
    }

    public static void setApplication(Context context){
        GBMapApplication.setApplicationContext(context);
    }

    /**
     * 初始化定位，注册监听+设置定位参数
     */
    public void initLocation( boolean isGPS ){
        mIsGPS = isGPS;
        LocationClientOption option = locationService.getDefaultLocationClientOption();
        option.setLocationMode( mIsGPS ? LocationMode.Device_Sensors : option.getLocationMode() );
        /*if( mIsGPS ){
            option = new LocationClientOption();
            option.setLocationMode(LocationMode.Device_Sensors);
            option.setCoorType(Utils.CoorType_BD09LL);
            option.setIsNeedAddress(true);
            option.setIsNeedLocationPoiList(true);
            option.setIsNeedLocationDescribe(true);
            option.setNeedDeviceDirect(true);
        }*/
        //opt.setLocationMode( mIsGPS ? LocationMode.Device_Sensors: LocationMode.Hight_Accuracy );
        locationService.unregisterListener( mListener );
        locationService.registerListener( mListener );
        locationService.setLocationOption( option );
        locationService.stop();
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
        stop();
        if( mIGBLocation!=null )mIGBLocation.onLocationStart();

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

                List<Poi> listPoi = location.getPoiList();
                List<GPoi> listGpoi = new ArrayList<>();

                if( listPoi!=null ){
                    for (Poi poi : listPoi) {
                        GPoi gpoi = new GPoi();
                        gpoi.setId(poi.getId());
                        gpoi.setName(poi.getName());
                        gpoi.setRank(poi.getRank());
                        listGpoi.add(gpoi);
                    }
                }

                bean.setListPoi(listGpoi);

                Boolean isSuccess = true;
                String msg = "";

                Resources res = GBMapApplication.instance.getResources();

                boolean isResultGPS = false;
                boolean isResultNetwork = false;
                boolean isResultOffline = false;

                if ( isResultGPS = location.getLocType() == BDLocation.TypeGpsLocation ){// GPS定位结果
                    msg = res.getString(R.string.bd_location_success_gps);
                } else if ( isResultNetwork = location.getLocType() == BDLocation.TypeNetWorkLocation ){// 网络定位结果
                    msg = res.getString(R.string.bd_location_success_network);
                } else if ( isResultOffline = location.getLocType() == BDLocation.TypeOffLineLocation ){// 离线定位结果
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
                    if( !mIsGPS || ( mIsGPS && isResultGPS ) ){
                        bean.setMsg(msg);
                        isSuccess = ( isSuccess && isNumber(mStrLng) && isNumber(mStrLat) );
                        mIGBLocation.onLocationFinish(isSuccess, bean);
                    }
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
        private List<GPoi> listPoi;

        public List<GPoi> getListPoi() {
            return listPoi;
        }

        public void setListPoi(List<GPoi> listPoi) {
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

    public class GPoi{
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Double getRank() {
            return rank;
        }

        public void setRank(Double rank) {
            this.rank = rank;
        }

        private String id;
        private String name;
        private Double rank;
    }

}
