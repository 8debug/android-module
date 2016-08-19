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
                .baseUrl("https://api.github.com")
                .addConverterFactory(JsonConverterFactory.create())
                .build();
        mHttpService = retrofit.create(GHttpSerivce.class);
    }

    @Test
    public void getCables() throws Exception {
        //此处需要返回String类型
        String URL = "https://api.github.com/repos/square/retrofit/contributors";
        Map<String, String> mapAjax = new HashMap<>();
        Response<JSONObject> response = mHttpService.ajaxGet(URL, mapAjax).execute();
        System.out.println( response.body() );

    }

}