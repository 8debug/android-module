package com.ybase.mvp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.ybase.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by yhr on 2016/11/17.
 * Activity基类，大部分Activity都继承此类
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected Context mContext;

    //快按兩次退出
    private static Boolean isExit = false;
    protected void exitBy2Click( int keyCode ) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ( !isExit ) {
                isExit = true;
                Toast.makeText(mContext, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                Timer tExit = new Timer();
                tExit.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        isExit = false;
                    }
                }, 2000);

            } else {
                BaseActivityCollector.finishAll();
                System.exit(0);
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        BaseActivityCollector.addActivity(this);
        mContext = this;
        onCreateFragment(savedInstanceState);

    }

    protected abstract void onCreateFragment(Bundle savedInstanceState);

    /**
     * 点击toolbar左侧按钮
     * @param view
     */
    protected void onClickToolBarLeft(View view){
        finish();
    }

    public void addFragment(Fragment fragment, int layoutId, String tag ){
        FragmentManager mgr = getSupportFragmentManager();
        FragmentTransaction tr = mgr.beginTransaction();
        Fragment f = mgr.findFragmentByTag( fragment.getTag() );
        if( fragment==f ){
            tr.show( fragment ).commit();
        }else{
            tr.add( layoutId, fragment, tag ).commit();
        }
    }


    public void addFragment( BaseFragment fragment ){
        FragmentManager mgr = getSupportFragmentManager();
        FragmentTransaction tr = mgr.beginTransaction();
        Fragment f = mgr.findFragmentByTag( fragment.getDefaultTag() );
        if( fragment==f ){
            tr.show( fragment ).commit();
        }else{
            tr.add( R.id.layout_content, fragment, fragment.getDefaultTag() ).commit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BaseActivityCollector.removeActivity(this);
    }
}
