package gesoft.gapp.http;

import android.content.Context;

import com.android.volley.Cache.Entry;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import gesoft.gapp.base.GApplication;
import gesoft.gapp.common.L;
import gesoft.gapp.common.T;


public class GHttp {

	public static String BASE_URL = "http://10.70.23.21:8080/jdgis";

    public static String URL = BASE_URL + "/gis/mobile.do?";

	//登录接口
	public static final String URL_LOGIN = URL + "sign=1";
	//轨迹上传接口
	public static final String URL_LOCATION = URL + "sign=11";

	public static final String URL_TASK_CODE = URL + "sign=26";
	//添加临检申请
	public static final String URL_TASK_ADD = URL + "sign=24";

    private static Context mContext;

    private MyReqeusts mRequestsFinish;

	private static Map<Integer, String> MAP_STATUS_CODE;
    
    private static GHttp http = new GHttp();

	public GHttp(){
		MAP_STATUS_CODE = new HashMap<>();
		MAP_STATUS_CODE.put(400, "错误请求");
		MAP_STATUS_CODE.put(401 , "请求要求身份验证");
		MAP_STATUS_CODE.put(403 , "服务器拒绝请求");
		MAP_STATUS_CODE.put(404 , "未找到");
		MAP_STATUS_CODE.put(405 , "禁用请求中指定的方法");
		MAP_STATUS_CODE.put(406  , "无法使用请求的内容特性响应请求的网页");
		MAP_STATUS_CODE.put(407  , "需要代理授权");
		MAP_STATUS_CODE.put(408  , "请求超时");
		MAP_STATUS_CODE.put(409  , "服务器在完成请求时发生冲突");
		MAP_STATUS_CODE.put(410  , "如果请求的资源已永久删除");
		MAP_STATUS_CODE.put(413    , "请求实体过大");
		MAP_STATUS_CODE.put(414    , "请求的 URI 过长");
		MAP_STATUS_CODE.put(415   , "不支持的媒体类型");
		MAP_STATUS_CODE.put(416    , "请求范围不符合要求");
		MAP_STATUS_CODE.put(417    , "未满足期望值");
	}

    public static GHttp getInstance(){
        return http;
    }

	//请求计数器
	private int requestCount = 0;

	/**
	 * 减少计数器
	 */
	private void countDecrease(){
		requestCount--;
		if( requestCount<=0 ){
			requestCount=0;
		}
	}

	/**
	 * 增加计数器
	 */
	private void countIncrease(){
		requestCount++;
		if( requestCount<0 ){
			requestCount=0;
		}
	}

	private boolean countResetZero(){
		if (requestCount<0)requestCount=0;
		return requestCount==0;
	}

    /**
     * 请求响应
     * @author yhr
     *
     */
    public interface MyResponse{
    	void onResponse(JSONObject response) throws Exception;
    	
    }
    
    /**
     * 全部请求响应完毕
     * @author yhr
     *
     */
    public interface MyReqeusts{
    	void onFinish(Object obj);
    }

    /**
     * 请求响应错误
     * @author yhr
     *
     */
    public interface MyResponseError{
    	void onErrorResponse(VolleyError error) throws Exception;
    }
    
    public static GHttp getInstance(Context context){
    	mContext = context;
        return http;
    }
    
    private static RetryPolicy mRetryPolicy = new DefaultRetryPolicy(
            10*1000,//默认超时时间，应设置一个稍微大点儿的  
          DefaultRetryPolicy.DEFAULT_MAX_RETRIES,//默认最大尝试次数
          DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
  );
    
    public void setOnRequestsFinish( MyReqeusts requestsFinish){
    	mRequestsFinish = requestsFinish;
    }
    
    public static boolean isResponse(JSONObject response){
		String sign = String.valueOf(response.opt("sign"));
    	if("1".equals(sign)){
    		return true;
    	}else{
			T.show( response.optString("msg"));
		}
		return false;
    }

    /**
     * 请求返回json数据 默认使用Toast提示网络错误信息
     * @param url
     * @param mapAjax
     * @param myresponse
     */
    public void requestPostDefault(String url, Map<String, String> mapAjax, final MyResponse myresponse){

		countIncrease();
        getRequestJson(url, mapAjax, new Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				try {
					countDecrease();
					if( countResetZero() && mRequestsFinish!=null ){
						mRequestsFinish.onFinish(response);
					}
					myresponse.onResponse(response);
				} catch (Exception e) {
					L.e(e);
				}
			}
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				L.e(error);
				String str = "";
				countDecrease();
				if( countResetZero() && mRequestsFinish!=null ){
					mRequestsFinish.onFinish(error);
				}
				if( error.networkResponse==null ){
					str = "网络超时，请重试！";
				}else{
					str = getHttpStatusCn(error.networkResponse.statusCode);
				}
				T.show(str);
			}
		});
    }

    /**
     * 获取缓存
     * @param url
     * @return
     */
    public static String getHttpCache(String url){
    	Entry entry = GApplication.getRequestQueue().getCache().get(url);
    	if( entry==null ){
    		return null;
    	}else{
    		return new String(entry.data);
    	}
    }


    public static String getHttpStatusCn(int statusCode){
    	String strMsg = MAP_STATUS_CODE.get(statusCode);
		strMsg = strMsg==null?"错误代码"+statusCode:strMsg;
    	return strMsg;
    }

    private JsonObjectPostRequest getRequestJson(String url, Map<String, String> mapAjax, final Listener<JSONObject> listener, final ErrorListener errorListener){
    	Listener<JSONObject> success = new Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				listener.onResponse(response);
			}
		};

		ErrorListener error = new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				errorListener.onErrorResponse(error);
			}
		};
		
    	JsonObjectPostRequest requestJson = new JsonObjectPostRequest(url, mapAjax, success, error);
        //volley  当网络延迟过大时，会出现多次请求的情况
        requestJson.setRetryPolicy( mRetryPolicy );
        GApplication.addRequestQueue(requestJson);
        return requestJson;
    }

	/**
	 * 上传文件
	 * @param url
	 * @param mapAjax
	 * @param myresponse
	 */
	public void requestPostUpload(String url, Map<String, Object> mapAjax, final MyResponse myresponse){

		Set<String> keys = mapAjax.keySet();
		Map<String, String> _mapAjax = new HashMap<String, String>();
		File file = null;
		String filename = "";
		for (String key : keys) {
			if( mapAjax.get(key) instanceof File){
				file = (File)mapAjax.get(key);
				filename = key;
			}else{
				_mapAjax.put(key, String.valueOf(mapAjax.get(key)));
			}
		}

		//不传递File时
		if( file==null ){
			requestPostDefault(url, _mapAjax, myresponse);
			return;
		}

		countIncrease();
		MultipartRequest req = new MultipartRequest(url, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				String str = error!=null?getHttpStatusCn(error.networkResponse.statusCode):"提交失败";
				countDecrease();
				if ( countResetZero() && mRequestsFinish!=null ){
					mRequestsFinish.onFinish(error);
				}
				T.show(str);
			}
		}, new Listener<String>() {
			@Override
			public void onResponse(String s) {
				try {
					JSONObject response = new JSONObject(s);
					myresponse.onResponse(response);

					countDecrease();
					if ( countResetZero() && mRequestsFinish!=null ){
						mRequestsFinish.onFinish(response);
					}
				}catch(Exception e){
					L.e(e);
				}
			}
		}, filename, file, _mapAjax);
		//volley  当网络延迟过大时，会出现多次请求的情况
		req.setRetryPolicy( mRetryPolicy );
		GApplication.addRequestQueue(req);
	}
    
    public void setRetryPolicy(RetryPolicy retryPolicy){
    	mRetryPolicy = retryPolicy;
    }

}
