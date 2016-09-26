package gesoft.gapp.http;

import android.support.annotation.Nullable;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import gesoft.gapp.common.L;
import gesoft.gapp.http.retrofit.converter.JsonConverterFactory;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GHttp {

    public final static String BASE_URL = "http://10.70.23.32:8080/jdgis/zc/";

    public static String URL = BASE_URL + "mobile.do";
    //请求数据
    public static String URL_3 = URL + "?sign=3";
    //上传文件
    public static String URL_4 = URL + "?sign=4";

    static GHttpSerivce mHttpService;

    public GHttp(Converter.Factory factory){

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl( GHttp.BASE_URL )
                .addConverterFactory(factory)
                .client(okHttpClient)
                .build();

        mHttpService = retrofit.create(GHttpSerivce.class);
    }

    public GHttp( Converter.Factory factory, OkHttpClient okHttpClient ){
        mHttpService = new Retrofit.Builder()
                            .baseUrl( GHttp.BASE_URL)
                            .addConverterFactory(factory)
                            .client(okHttpClient)
                            .build()
                            .create(GHttpSerivce.class);
    }

    /**
     * json工厂
     * @return
     */
    public static JsonConverterFactory getJsonConverterFactory(){
        return JsonConverterFactory.create();
    }


    /*private Retrofit getRetrofitJson(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl( GHttp.BASE_URL )
                .addConverterFactory(JsonConverterFactory.create())
                .build();
        return retrofit;
    }*/

    public interface IAjax<T>{
        void onResponse(T a);
    }

    public interface IAjaxCall<T>{
        void onResponse(T a);
        void onFailure();
    }

    @Deprecated
    private void request( Call request , final IAjax iAjax){
        request.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                iAjax.onResponse(response.body());
            }

            @Override
            public void onFailure(Call call, Throwable e) {
                L.e(e);
                call.cancel();
            }
        });
    }


    private void request( Call request , final IAjaxCall iAjax){
        request.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                iAjax.onResponse(response.body());
            }

            @Override
            public void onFailure(Call call, Throwable e) {
                L.e(e);
                iAjax.onFailure();
                call.cancel();
            }
        });
    }

    /**
     * 请求数据返回json格式
     * @param url
     * @param mapAjax
     */
    @Deprecated
    public void ajaxPost(String url, Map<String, String> mapAjax, @Nullable final IAjax iAjax){

        Call request = mHttpService.ajaxPost(url, mapAjax);

        request(request, iAjax);
    }

    public void ajaxPost(String url, Map<String, String> mapAjax, @Nullable final IAjaxCall iAjax){

        Call request = mHttpService.ajaxPost(url, mapAjax);

        request(request, iAjax);
    }

    /**
     * 上传文件
     * @param url
     * @param mapAjax
     */
    @Deprecated
    public void ajaxUpload(String url, Map<String, Object> mapAjax, @Nullable final IAjax iAjax){

        Map<String, RequestBody> params = parseMap( mapAjax );

        Call request = mHttpService.ajaxUpload(url, params);

        request(request, iAjax);

    }

    public void ajaxUpload(String url, Map<String, Object> mapAjax, @Nullable final IAjaxCall iAjax){

        Map<String, RequestBody> params = parseMap( mapAjax );

        Call request = mHttpService.ajaxUpload(url, params);

        request(request, iAjax);

    }

    /**
     * 将Map<String, Object>转换为Map<String, RequestBody>
     * @param param
     * @return
     */
    Map<String, RequestBody> parseMap( Map<String, Object> param ){
        Map<String, RequestBody> params = new HashMap<>();
        try {
            Set<String> keys = param.keySet();
            for (String key : keys) {
                Object obj = param.get(key);
                RequestBody requestBody;
                if( obj instanceof String ){
                    requestBody = RequestBody.create( MediaType.parse("text/plain"), obj.toString() );
                    params.put(key, requestBody);
                }else if( obj instanceof File) {
                    File file = (File)obj;
                    requestBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
                    params.put(key+"\"; filename=\""+file.getName()+"", requestBody);
                }

            }
        } catch (Exception e) {
            L.e(e);
        }
        return params;
    }

}
