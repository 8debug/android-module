package gesoft.gandroid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import gesoft.gapp.common.GAct;
import gesoft.gapp.common.GData;
import gesoft.gapp.common.L;
import gesoft.gapp.http.GHttp;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitActivity extends AppCompatActivity {

    ListView lv;
    GHttp gHttp = new GHttp(ScalarsConverterFactory.create());
    File mFile;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        lv = (ListView) findViewById(R.id.lv);
        context = this;
    }

    public void ajaxJSON(View v){
        Map<String, String> mapAjax = new HashMap<>();
        mapAjax.put("pageStart", "1");
        gHttp.ajaxPost(GHttp.URL_3, mapAjax, new GHttp.IAjax<String>() {
            @Override
            public void onResponse(String str) {
                JSONObject response = GData.parseJSON(str);
                JSONArray array = response.optJSONArray("data");
                String[] strings = new String[ array.length() ];
                for(int i=0; i<array.length(); i++){
                    strings[i] = array.optString(i);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, strings);
                L.d(strings);
                L.d(lv);
                L.d(adapter);
                lv.setAdapter(adapter);
            }
        });

    }

    public void startCamera(View v){
        mFile = GAct.startActivityCamera(this, 110);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if( requestCode==110 && resultCode==RESULT_OK ){
            Map<String, Object> mapAjax = new HashMap<>();
            mapAjax.put("loginName", "yhr");
            mapAjax.put("zipFile", mFile);
            gHttp.ajaxUpload(GHttp.URL_4, mapAjax, new GHttp.IAjax<String>() {
                @Override
                public void onResponse(String str) {

                }
            });

            /*Map<String, RequestBody> params = new HashMap<>();
            RequestBody sign = RequestBody.create(MediaType.parse("text/plain"), "4");
            params.put("sign", sign);

            RequestBody zipPhotoFile = RequestBody.create(MediaType.parse("application/otcet-stream"), mFile);
            params.put("zipFile\"; filename=\""+mFile.getName()+"", zipPhotoFile);

            gHttp.ajaxUploadTest(params);*/
        }
    }
}
