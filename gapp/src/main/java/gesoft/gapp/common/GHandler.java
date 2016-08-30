package gesoft.gapp.common;

import android.os.Handler;
import android.os.Message;

/**
 * Created by Administrator on 2016/8/29.
 */
public class GHandler {

    public interface GRunnable{
        void runTask();
        void runUI();
    }

    public static Handler run(final GRunnable gRunnable ){
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if( msg.what==1 ){
                    gRunnable.runUI();
                }
                super.handleMessage(msg);
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                gRunnable.runTask();
                handler.sendEmptyMessage(1);
            }
        }).start();

        return handler;
    }



}
