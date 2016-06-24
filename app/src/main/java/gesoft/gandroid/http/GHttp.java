package gesoft.gandroid.http;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import gesoft.gandroid.http.retrofit.converter.JsonConverterFactory;
import gesoft.gapp.common.L;
import gesoft.gapp.common.T;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GHttp {

    public final static String BASE_URL = "http://10.70.23.47:8080/jdgis/zc/";

    public final static String URL = "mobile.do";
    //请求数据
    public final static String URL_3 = "3";
    //上传文件
    public final static String URL_4 = "4";

    public final static int METHOD_GET = 0;

    public final static int METHOD_POST = 1;

    GHttpSerivce mHttpService;

    public GHttp(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl( GHttp.BASE_URL )
                .addConverterFactory(JsonConverterFactory.create())
                .build();
        mHttpService = retrofit.create(GHttpSerivce.class);
    }

    /*private Retrofit getRetrofitJson(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl( GHttp.BASE_URL )
                .addConverterFactory(JsonConverterFactory.create())
                .build();
        return retrofit;
    }*/

    public interface IAjax{
        void onResponse(JSONObject response);
    }


    /**
     * 请求数据返回json格式
     * @param method
     * @param url
     * @param mapAjax
     */
    public void ajaxJSON(int method, String url, Map<String, String> mapAjax, final IAjax iAjax){

        Call<JSONObject> request = method == METHOD_GET ? mHttpService.ajaxGet(url, mapAjax) : mHttpService.ajaxPost(url, mapAjax);
        request.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                JSONObject json = response.body();
                if ( isSuccess(json) && iAjax!=null ){
                    iAjax.onResponse(json);
                }
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable e) {
                call.cancel();
            }
        });
    }

    /**
     * 上传文件
     * @param url
     * @param mapAjax
     */
    public void ajaxUpload(String url, Map<String, Object> mapAjax, final IAjax iAjax){

        Map<String, RequestBody> params = parseMap( mapAjax );

        Call<JSONObject> request = mHttpService.ajaxUpload(url, params);
        request.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                JSONObject json = response.body();
                if ( isSuccess(json) && iAjax!=null ){
                    iAjax.onResponse(json);
                }
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable e) {
                call.cancel();
            }
        });
    }

    boolean isSuccess( JSONObject jsonResponse ){
        if( jsonResponse.optInt("sign")==1 ){
            return true;
        }else{
            T.show(jsonResponse.optString("msg"));
            return false;
        }
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
