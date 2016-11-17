package gesoft.gandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.Arrays;

import gesoft.gapp.common.listview.GLAdapter;
import gesoft.gapp.common.listview.GLAdapterListener;
import gesoft.gapp.common.listview.GLVHolder;

public class ListViewActivity extends AppCompatActivity implements GLAdapterListener.OnConvert<String> {

    ListView lv;
    String[] array = new String[]{"1", "2", "3", "4"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        lv = (ListView)findViewById(R.id.lv);
        GLAdapter<String> adapter = new GLAdapter<>(android.R.layout.simple_list_item_1, Arrays.asList(array), this );
        lv.setAdapter(adapter);
    }

    @Override
    public void onConvert(GLVHolder holder, String item) {
        holder.setText(android.R.id.text1, item);
    }
}
