package gesoft.gapp.http;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import gesoft.gapp.common.L;
import gesoft.gapp.http.retrofit.converter.JsonConverterFactory;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GHttp {

    public static String BASE_URL;

    public final static String URL = "mobile.do";
    //请求数据
    public final static String URL_3 = "3";
    //上传文件
    public final static String URL_4 = "4";

    public final static int METHOD_GET = 0;

    public final static int METHOD_POST = 1;

    //ajax请求计数
    int ajaxcount;

    GHttpSerivce mHttpService;

    IAjaxFinish mAjaxFinish;

    public void setAjaxFinish(IAjaxFinish ajaxFinish){
        mAjaxFinish = ajaxFinish;
    }

    //初始化计数器
    void initAjaxCount(){
        ajaxcount = 0;
    }

    public GHttp(String baseUrl){
        initAjaxCount();
        Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl( baseUrl )
                                        .addConverterFactory(JsonConverterFactory.create())
                                        .build();
        mHttpService = retrofit.create(GHttpSerivce.class);
    }

    public interface IAjaxResponse{
        //完成一次请求
        void onResponse(JSONObject response);
    }

    public interface IAjaxFinish{
        //完成一次请求
        void onFinish();
    }

    /**
     * 默认post请求
     * @param url
     * @param mapAjax
     * @param iAjax
     */
    public void ajaxJSON(String url, Map<String, String> mapAjax, final IAjaxResponse iAjax){
        ajaxJSON(METHOD_POST, url, mapAjax, iAjax);
    }

    /**
     * 请求数据返回json格式
     * @param method
     * @param url
     * @param mapAjax
     */
    public void ajaxJSON(int method, String url, Map<String, String> mapAjax, final IAjaxResponse iAjax){

        mapAjax = mapAjax==null?new HashMap<String, String>():mapAjax;

        Call<JSONObject> request = method == METHOD_GET ? mHttpService.ajaxGet(url, mapAjax) : mHttpService.ajaxPost(url, mapAjax);

        ajaxcount++;
        request.enqueue(getCallback(iAjax));
    }

    /**
     * 上传文件
     * @param url
     * @param mapAjax
     */
    public void ajaxUpload(String url, Map<String, Object> mapAjax, final IAjaxResponse iAjax){

        Map<String, RequestBody> params = parseMap( mapAjax );

        Call<JSONObject> request = mHttpService.ajaxUpload(url, params);
        request.enqueue(getCallback(iAjax));
    }

    /**
     *
     * @param iAjax 请求结束的回调函数
     * @return
     */
    Callback getCallback(final IAjaxResponse iAjax){
        return new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                ajaxcount--;
                JSONObject jsonResponse = response.body();
                if ( iAjax!=null ){
                    iAjax.onResponse(jsonResponse);
                }

                if( ajaxcount<=0 && mAjaxFinish!=null  ){
                    mAjaxFinish.onFinish();
                }

            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable e) {
                L.e(e);
                call.cancel();
                ajaxcount--;

                if( ajaxcount<=0 && mAjaxFinish!=null  ){
                    mAjaxFinish.onFinish();
                }
            }
        };
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
