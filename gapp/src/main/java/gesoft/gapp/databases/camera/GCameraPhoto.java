package gesoft.gapp.databases.camera;

/**
 * Created by yhr on 2016/9/20.
 * 照相机图片
 */
public class GCameraPhoto {

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    private double lng;
    private double lat;
    private String path;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    private long time;

}
