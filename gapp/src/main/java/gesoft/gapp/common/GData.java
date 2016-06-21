package gesoft.gapp.common;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class GData {

    /**
     * 保存json
     * @param j 要保存的json对象
     * @param key
     * @param value
     */
    public static void putJSON(JSONObject j, String key, Object value){
        try{
            j.put(key, value);
        }catch (Exception e){
            L.e(e);
        }
    }

    /**
     * 向JSONArray中添加json对象
     * @param array
     * @param index
     * @param obj
     */
    public static void putJSONArray(JSONArray array, int index, Object obj){
        try{
            array.put(index, obj);
        }catch (Exception e){
            L.e(e);
        }
    }

    /**
     * 对字符串进行编码
     * @param str
     * @param encode    编码格式
     * @return
     */
    public static String URLEncoder(String str, String encode){
        String s = "";
        try{
            s = URLEncoder.encode(str, encode);
        }catch (Exception e){
            L.e(e);
        }
        return s;
    }

    /**
     * 对字符串进行GBK编码
     * @param str
     * @return
     */
    public static String URLEncoderGBK(String str){
        if(!TextUtils.isEmpty(str)){
            return URLEncoder(str, "GBK");
        }
        return str;
    }

    /**
     * 对字符串进行UTF8编码
     * @param str
     * @return
     */
    public static String URLEncoderUTF8(String str){
        if(!TextUtils.isEmpty(str)){
            return URLEncoder(str, "UTF-8");
        }
        return str;
    }

    /**
     * 对象转JSONObject对象
     * @param obj
     * @return
     */
    public static JSONObject parseJSON(Object obj){
        JSONObject j = new JSONObject();
        try {
            if( obj instanceof String ){
                j = new JSONObject(obj.toString());
            }else if( obj instanceof Map){
                j = new JSONObject((Map)obj);
            }
        } catch (JSONException e) {
            L.e(e);
        }

        return j;
    }

    /**
     * JSONArray转List<JSONObject> listJson
     * @param array
     * @return
     */
    public static List<JSONObject> parseList(JSONArray array){
        List<JSONObject> list = new ArrayList<JSONObject>();
        for( int i=0; i<array.length(); i++ ){
            list.add(array.optJSONObject(i));
        }
        return list;
    }

}
