package gesoft.gapp.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;


/**
 * Created by yhr on 2016/5/12.
 */
public abstract class GActivity extends AppCompatActivity {

    protected Context mContext;
    protected ProgressDialog mPro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mPro = new ProgressDialog(mContext);
        onCreateG(savedInstanceState);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if( item.getItemId()==android.R.id.home ){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        onDestroyG();
    }

    protected void onDestroyG(){
        if( mPro!=null ){
            mPro.dismiss();
        }
    }

    protected abstract void onCreateG(Bundle savedInstanceState);

}
