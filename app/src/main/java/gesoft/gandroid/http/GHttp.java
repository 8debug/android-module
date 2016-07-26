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

    public final static String BASE_URL = "http://10.70.23.32:8080/jdgis/zc/";

    public static String URL = BASE_URL + "mobile.do";
    //请求数据
    public static String URL_3 = URL + "?sign=3";
    //上传文件
    public static String URL_4 = URL + "?sign=4";

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

    private ISetSuccess mSetSuccess;

    public interface ISetSuccess<T>{
        boolean isSuccess(T t);
    }

    public GHttp setIsSuccess( ISetSuccess setSuccess ){
        mSetSuccess = setSuccess;
        return this;
    }

    private void request( Call<JSONObject> request , final IAjax iAjax){
        request.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                JSONObject json = response.body();
                if( mSetSuccess==null ){
                    try {
                        throw new Exception(" 未实现ISetSuccess接口 ");
                    } catch (Exception e) {
                        L.e(e);
                    }
                }else if( mSetSuccess.isSuccess(json) && iAjax!=null ){
                    iAjax.onResponse(json);
                }
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable e) {
                call.cancel();
                L.e(e);
            }
        });
    }

    /**
     * 请求数据返回json格式
     * @param url
     * @param mapAjax
     */
    public void ajaxPost(String url, Map<String, String> mapAjax, final IAjax iAjax){

        Call<JSONObject> call = mHttpService.ajaxPost(url, mapAjax);

        request(call, iAjax);

    }

    /**
     * 上传文件
     * @param url
     * @param mapAjax
     */
    public void ajaxUpload(String url, Map<String, Object> mapAjax, final IAjax iAjax){

        Map<String, RequestBody> params = parseMap( mapAjax );

        Call<JSONObject> call = mHttpService.ajaxUpload(url, params);

        request(call, iAjax);
    }

    /*boolean isSuccess( JSONObject jsonResponse ){
        if( jsonResponse.optInt("sign")==1 ){
            return true;
        }else{
            T.show(jsonResponse.optString("msg"));
            return false;
        }
    }*/

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
