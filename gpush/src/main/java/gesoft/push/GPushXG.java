package gesoft.push;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGNotifaction;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.android.tpush.XGPushNotifactionCallback;
import com.tencent.android.tpush.service.XGPushService;

import java.util.List;

/**
 * Created by yhr on 2016/6/12.
 */
public class GPushXG {

    public interface Reg{
        void onSuccess(Object token);
    }

    public static void enableDebug(Context ctx, Boolean isDebug){
        XGPushConfig.enableDebug(ctx, isDebug);
    }

    /**
     * 判断是否在主线程中
     * @param applicationContext
     * @return
     */
    private static boolean isMainProcess(Context applicationContext) {
        ActivityManager am = ((ActivityManager) applicationContext.getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = applicationContext.getPackageName();
        int myPid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * step 1
     * 在主进程设置信鸽相关的内容
     * @param applicationcontext
     */
    public static void setApplication(Context applicationcontext){
        // 在主进程设置信鸽相关的内容
        if ( isMainProcess(applicationcontext) ) {
            // 为保证弹出通知前一定调用本方法，需要在application的onCreate注册
            // 收到通知时，会调用本回调函数。
            // 相当于这个回调会拦截在信鸽的弹出通知之前被截取
            // 一般上针对需要获取通知内容、标题，设置通知点击的跳转逻辑等等
            XGPushManager.setNotifactionCallback(new XGPushNotifactionCallback() {
                @Override
                public void handleNotify(XGNotifaction xGNotifaction) {
                    Log.i("test", "处理信鸽通知：" + xGNotifaction);
                    // 获取标签、内容、自定义内容
                    String title = xGNotifaction.getTitle();
                    String content = xGNotifaction.getContent();
                    String customContent = xGNotifaction.getCustomContent();
                    // 其它的处理
                    // 如果还要弹出通知，可直接调用以下代码或自己创建Notifaction，否则，本通知将不会弹出在通知栏中。
                    xGNotifaction.doNotify();
                }
            });
        }
    }

    /**
     * 注册消息接收（动态）
     * @param ctx
     * @param receiver
     */
    /*public static void registerReceiver(Context ctx, XGPushBaseReceiver receiver){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(GPushConstant.XG_ACTION_PUSH_MESSAGE);
        intentFilter.addAction(GPushConstant.XG_ACTION_FEEDBACK);
        ctx.registerReceiver(receiver,intentFilter);
    }*/

    /**
     * step 2
     * 注册信鸽推送
     * @param applicationContext
     */
    public static void registerPush(final Context applicationContext, final Reg ireg){
        // 开启logcat输出，方便debug，发布时请关闭
        // XGPushConfig.enableDebug(this, true);
        // 如果需要知道注册是否成功，请使用registerPush(getApplicationContext(), XGIOperateCallback)带callback版本
        // 如果需要绑定账号，请使用registerPush(getApplicationContext(),account)版本
        // 具体可参考详细的开发指南
        // 传递的参数为ApplicationContext
        //XGPushManager.registerPush(applicationContext);
        XGPushManager.registerPush(applicationContext, new XGIOperateCallback() {
            @Override
            public void onSuccess(Object token, int i) {
                Toast.makeText(applicationContext, "开启信息推送", Toast.LENGTH_SHORT).show();
                if( ireg!=null ) {
                    ireg.onSuccess(token);
                }
                Log.d("debug_ge", "onSuccess====="+i);
            }

            @Override
            public void onFail(Object o, int i, String s) {
                //Toast.makeText(applicationContext, "", Toast.LENGTH_SHORT).show();
                Log.d("debug_ge", "onFail====="+s+", i==="+i);
            }
        });

        // 2.36（不包括）之前的版本需要调用以下2行代码
        Intent service = new Intent(applicationContext, XGPushService.class);
        applicationContext.startService(service);

        // 其它常用的API：
        // 绑定账号（别名）注册：registerPush(context,account)或registerPush(context,account, XGIOperateCallback)，其中account为APP账号，可以为任意字符串（qq、openid或任意第三方），业务方一定要注意终端与后台保持一致。
        // 取消绑定账号（别名）：registerPush(context,"*")，即account="*"为取消绑定，解绑后，该针对该账号的推送将失效
        // 反注册（不再接收消息）：unregisterPush(context)
        // 设置标签：setTag(context, tagName)
        // 删除标签：deleteTag(context, tagName)
    }
}
