package gesoft.gandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import gesoft.gapp.common.GDecoration;
import gesoft.gapp.common.GRAdapter;
import gesoft.gapp.common.GRAdapterListener;
import gesoft.gapp.common.GROnScrollListener;
import gesoft.gapp.common.GVHolder;

public class RecyclerViewActivity extends AppCompatActivity
        implements GRAdapterListener.OnConvert<String>  {

    @Bind(R.id.recycler_grid)
    RecyclerView recyclerGrid;
    @Bind(R.id.recycler_horizontal)
    RecyclerView recyclerHorizontal;
    @Bind(R.id.recycler_vertical)
    RecyclerView recyclerVertical;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        ButterKnife.bind(this);

        List<String> listStr = new ArrayList<>();
        for (int i = 1; i <=5 ; i++) {
            listStr.add("第" + i + "个");
        }
        GRAdapter<String> adapter = new GRAdapter<>( this, R.layout.adapter_item, listStr, this);
        //adapter.addFooterView( R.layout.g_listview_footer );
        recyclerGrid.setAdapter(adapter);

        recyclerHorizontal.setAdapter(adapter);
        recyclerHorizontal.addItemDecoration(new GDecoration(this, GDecoration.VERTICAL_LIST));

        List<String> listStrVertical = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            listStrVertical.add("第1页，第"+i+"项");
        }
        final GRAdapter<String> adapter2 = new GRAdapter<>( this, R.layout.adapter_item, listStrVertical, this);
        adapter2.addFooterView( R.layout.g_listview_footer );
        recyclerVertical.setAdapter(adapter2);
        recyclerVertical.addOnScrollListener(new GROnScrollListener( recyclerVertical.getLayoutManager() ) {
            @Override
            public void onLoadMore( final int current_page) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(3000);
                            for (int i = 1; i <=2 ; i++) {
                                adapter2.add("第"+current_page+"页，第"+i+"项");
                            }
                            recyclerVertical.post(new Runnable() {
                                @Override
                                public void run() {
                                    recyclerVertical.getAdapter().notifyDataSetChanged();
                                }
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }

    @Override
    public void onConvert(GVHolder holder, String s) {
        holder.setText(R.id.tv, s);
    }

}
