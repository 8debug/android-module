package gesoft.gapp;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import gesoft.gapp.http.GHttpSerivce;
import gesoft.gapp.http.retrofit.converter.JsonConverterFactory;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Administrator on 2016/8/4.
 */
public class ResourcePresenterTest {

    GHttpSerivce mHttpService;

    @Before
    public void setUp(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.baidu.com")
                .addConverterFactory(JsonConverterFactory.create())
                .build();
        mHttpService = retrofit.create(GHttpSerivce.class);
    }

    @Test
    public void getCables() throws Exception {
        String URL = "http://www.gytaobao.cn:9218/glcsgisbdw/gis/mobile.do?sign=20";
        Map<String, String> mapAjax = new HashMap<>();
        mapAjax.put("phoneNo", "2654729");
        Response<JSONObject> response = mHttpService.ajaxPost(URL, mapAjax).execute();
        System.out.println( response.body() );

    }

}