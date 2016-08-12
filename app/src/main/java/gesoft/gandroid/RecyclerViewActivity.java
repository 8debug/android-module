package gesoft.gandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import gesoft.gapp.common.GDecoration;
import gesoft.gapp.common.GRAdapter;
import gesoft.gapp.common.GRAdapterListener;
import gesoft.gapp.common.GVHolder;

public class RecyclerViewActivity extends AppCompatActivity implements GRAdapterListener.OnConvert<String> {

    @Bind(R.id.recycler_grid)
    RecyclerView recyclerGrid;
    @Bind(R.id.recycler_linear)
    RecyclerView recyclerLinear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        ButterKnife.bind(this);

        List<String> listStr = new ArrayList<>(Arrays.asList("杨皓然", "岳增光", "杨刚", "张永茂", "袁宇飞", "张楠"));

        GRAdapter<String> adapter = new GRAdapter<>(R.layout.adapter_item, this);
        adapter.addAll(listStr);

        recyclerGrid.setAdapter(adapter);
        recyclerLinear.setAdapter(adapter);
        recyclerLinear.addItemDecoration(new GDecoration(this, GDecoration.VERTICAL_LIST));

    }

    @Override
    public void onConvert(GVHolder holder, String s) {
        holder.setText(R.id.tv, s);
    }
}
