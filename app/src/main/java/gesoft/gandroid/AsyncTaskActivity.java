package gesoft.gandroid;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class AsyncTaskActivity extends AppCompatActivity {

    ListView name_list;
    private String[] users = {
            "nameA","addressA","ageA","cityA","stateA",
            "nameB","addressB","ageB","cityB","stateB",
            "nameC","addressC","ageC","cityC","stateC",
            "nameD","addressD","ageD","cityD","stateD",
            "nameE","addressE","ageE","cityE","stateE",
            "nameF","addressF","ageF","cityF","stateF",
            "nameG","addressG","ageG","cityG","stateG",
            "nameH","addressH","ageH","cityH","stateH"
    };
    ProgressBar pBar;
    AddArrayToListView arr_to_list_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        //Progress Bar na
        pBar = (ProgressBar) findViewById(R.id.progressBar);
        if( pBar!=null )
            pBar.setVisibility(View.VISIBLE);
        //Adapter untuk Listview
        name_list = (ListView) findViewById(R.id.listView);
        name_list.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new ArrayList<String>()));
        //Asynctask Adapter
        arr_to_list_view = new AddArrayToListView();
        arr_to_list_view.execute();



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if( fab!=null )
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_again) {
            //Executor exec = new ThreadPoolExecutor(15,200,10, TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>());
            new AddArrayToListView().execute();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    class AddArrayToListView extends AsyncTask<Void, String, Void> {
        private ArrayAdapter<String> adapter;
        private int counter = 0;
        // Handle Loading Progress
        ProgressDialog pDialog = new ProgressDialog(AsyncTaskActivity.this);
        @Override
        protected void onPreExecute () {
            adapter = (ArrayAdapter<String>) name_list.getAdapter();
            adapter.clear();
            adapter.notifyDataSetChanged();
            // This for init Progress Dialog
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.setTitle("ON Progress...");
            pDialog.setCancelable(false);
            pDialog.setProgress(0);
            // This Will Handle cycle Asynctask when click cancel button;
            pDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel Progress...", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    arr_to_list_view.cancel(true);
                    pBar.setVisibility(View.INVISIBLE);
                    dialog.dismiss();
                }
            });
            pDialog.show();
        }
        @Override
        protected Void doInBackground(Void... params){
            for(String item:users){
                publishProgress(item);
                try{
                    Thread.sleep(100);
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }
                if(isCancelled()){
                    arr_to_list_view.cancel(true);
                }
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(String... values){
            String strItem = values[0];
            adapter.add(strItem);
            adapter.notifyDataSetChanged();
            counter ++;
            Integer current_status = (int)((counter/(float)users.length)*100);
            pBar.setProgress(current_status);
            //set progress only working  for horizontal loading
            pDialog.setProgress(current_status);
            //set message will not working when using horizontal loading
            pDialog.setMessage(String.valueOf(current_status)+ "%   " + strItem);
        }
        @Override
        protected void onPostExecute(Void result){
            //hide top Progress Bar
            pBar.setVisibility(View.VISIBLE);
            //remove progress Dialog
            pDialog.dismiss();
        }
    }
}
