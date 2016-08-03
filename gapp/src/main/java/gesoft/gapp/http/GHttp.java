package gesoft.gapp.http;

import android.support.annotation.Nullable;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import gesoft.gapp.common.L;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GHttp<T> {

    public final static String BASE_URL = "http://10.70.23.32:8080/jdgis/zc/";

    public static String URL = BASE_URL + "mobile.do";
    //请求数据
    public static String URL_3 = URL + "?sign=3";
    //上传文件
    public static String URL_4 = URL + "?sign=4";

    static GHttpSerivce mHttpService;

    public GHttp(Converter.Factory factory){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl( GHttp.BASE_URL )
                .addConverterFactory(factory)
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

    public interface IAjax<T>{
        void onResponse(T a);
    }

    private ISetSuccess mSetSuccess;

    public interface ISetSuccess<T>{
        boolean isSuccess(T t);
    }

    public GHttp setIsSuccess( ISetSuccess setSuccess ){
        mSetSuccess = setSuccess;
        return this;
    }

    private void request( Call<T> request , final IAjax<T> iAjax){
        request.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                T t = response.body();
                if( mSetSuccess==null ){
                    try {
                        throw new Exception(" 未实现ISetSuccess接口 ");
                    } catch (Exception e) {
                        L.e(e);
                    }
                }else if( mSetSuccess.isSuccess(t) && iAjax!=null ){
                    iAjax.onResponse(t);
                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable e) {
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
    public void ajaxPost(String url, Map<String, String> mapAjax, @Nullable final IAjax<T> iAjax){

        Call<T> request = mHttpService.ajaxPost(url, mapAjax);

        request(request, iAjax);
    }

    /**
     * 上传文件
     * @param url
     * @param mapAjax
     */
    public void ajaxUpload(String url, Map<String, Object> mapAjax, @Nullable final IAjax<T> iAjax){

        Map<String, RequestBody> params = parseMap( mapAjax );

        Call<T> request = mHttpService.ajaxUpload(url, params);

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
