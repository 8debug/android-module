package gesoft.gcrashemail.crash;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeUtility;

import gesoft.gcrashemail.bean.GEmail;
import gesoft.gcrashemail.email.MultiMailsender;

/** 
 * UncaughtException处理类,当程序发生Uncaught异常的时候,有该类来接管程序,并记录发送错误报告. 
 *  
 * @author user 
 *  
 */  
public class GCrashHandler implements UncaughtExceptionHandler {

    /*private String mSubject;
    private String mNick;*/
    private GEmail mGEmail;

    /*public void setNick( String nick ){
        mNick = nick;
    }*/

    public void setGEMail( GEmail mail ){
        mGEmail = mail;
    }
      
    public static final String TAG = "GCrashHandler";
      
    //系统默认的UncaughtException处理类   
    private Thread.UncaughtExceptionHandler mDefaultHandler;  
    //CrashHandler实例  
    private static GCrashHandler INSTANCE = new GCrashHandler();
    //程序的Context对象  
    private Context mContext;  
    //用来存储设备信息和异常信息  
    private Map<String, String> infos = new HashMap<String, String>();  
  
    //用于格式化日期,作为日志文件名的一部分  
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");  
  
    /** 保证只有一个CrashHandler实例 */  
    private GCrashHandler() {
    }  
  
    /** 获取CrashHandler实例 ,单例模式 */  
    public static GCrashHandler getInstance() {
        return INSTANCE;  
    }  
  
    /** 
     * 初始化 
     *  
     * @param context 
     */  
    public void init(Context context) {  
        mContext = context;  
        //获取系统默认的UncaughtException处理器  
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();  
        //设置该CrashHandler为程序的默认处理器  
        Thread.setDefaultUncaughtExceptionHandler(this);  
    }  
  
    /** 
     * 当UncaughtException发生时会转入该函数来处理 
     */  
    @Override  
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {  
            //如果用户没有处理则让系统默认的异常处理器来处理  
            mDefaultHandler.uncaughtException(thread, ex);  
        } else {  
            try {  
                Thread.sleep(3000);  
            } catch (InterruptedException e) {  
                Log.e(TAG, "error : ", e);  
            }  
            //退出程序  
            android.os.Process.killProcess(android.os.Process.myPid());  
            System.exit(1);  
        }  
    }  
  
    /** 
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成. 
     *  
     * @param ex 
     * @return true:如果处理了该异常信息;否则返回false. 
     */  
    private boolean handleException(Throwable ex) {  
        if (ex == null) {  
            return false;  
        }  
        //使用Toast来显示异常信息  
        new Thread() {  
            @Override  
            public void run() {  
                Looper.prepare();  
                Toast.makeText(mContext, "很抱歉,程序出现异常,即将退出.", Toast.LENGTH_LONG).show();  
                Looper.loop();  
            }  
        }.start();  
        //收集设备参数信息   
        //collectDeviceInfo(mContext);
        //保存日志文件   
        //saveCrashInfo2File(ex);

        sendEmail( ex );

        return true;  
    }

    /*public void setEmailMessage( String subject ){
        mSubject = subject;
    }*/

    void sendEmail( final Throwable ex ){
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {

                    //这个类主要是设置邮件
                    MultiMailsender.MultiMailSenderInfo mailInfo = new MultiMailsender.MultiMailSenderInfo();
                    mailInfo.setMailServerHost("smtp.163.com");
                    mailInfo.setMailServerPort("25");
                    mailInfo.setValidate(true);
                    mailInfo.setUserName( mGEmail.getUserName() );
                    mailInfo.setPassword( mGEmail.getUserPwd() );//您的邮箱密码
                    //mailInfo.setUserName("y.h.r@163.com");
                    //mailInfo.setPassword("13050920586");//您的邮箱密码

                    String nick = TextUtils.isEmpty( mGEmail.getNick() )?"": MimeUtility.encodeText( mGEmail.getNick() );
                    mailInfo.setFromAddress( nick+"<"+mGEmail.getUserName()+">" );

                    mailInfo.setToAddress( mGEmail.getUserName() );
                    mailInfo.setSubject( mGEmail.getSubject() );
                    mailInfo.setContent( getStackTrace(ex) );
                    //抄送
                /*String[] receivers = new String[]{"y.h.r@163.com"};
                String[] ccs = receivers; mailInfo.setReceivers( receivers );
                mailInfo.setCcs(ccs);
                MultiMailsender.sendMailtoMultiCC(mailInfo);//发送抄送
                */

                    //这个类主要来发送邮件
                    //MultiMailsender sms = new MultiMailsender();

                    //发送文体格式
                    MultiMailsender.sendTextMail(mailInfo);

                    //发送html格式
                    //MultiMailsender.sendMailtoMultiReceiver(mailInfo);
                } catch (Exception e) {
                    Log.e("error", "error", e);
                }

            }
        }).start();
    }

    public String getStackTrace( Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        throwable.printStackTrace(pw);
        return sw.getBuffer().toString();
    }
      
    /** 
     * 收集设备参数信息 
     * @param ctx 
     */  
    public void collectDeviceInfo(Context ctx) {  
        try {  
            PackageManager pm = ctx.getPackageManager();  
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);  
            if (pi != null) {  
                String versionName = pi.versionName == null ? "null" : pi.versionName;  
                String versionCode = pi.versionCode + "";  
                infos.put("versionName", versionName);  
                infos.put("versionCode", versionCode);  
            }  
        } catch (NameNotFoundException e) {  
            Log.e(TAG, "an error occured when collect package info", e);  
        }  
        Field[] fields = Build.class.getDeclaredFields();  
        for (Field field : fields) {  
            try {  
                field.setAccessible(true);  
                infos.put(field.getName(), field.get(null).toString());  
                Log.d(TAG, field.getName() + " : " + field.get(null));  
            } catch (Exception e) {  
                Log.e(TAG, "an error occured when collect crash info", e);  
            }  
        }  
    }  
  
    /** 
     * 保存错误信息到文件中 
     *  
     * @param ex 
     * @return  返回文件名称,便于将文件传送到服务器 
     */  
    private String saveCrashInfo2File(Throwable ex) {  
          
        StringBuffer sb = new StringBuffer();  
        for (Map.Entry<String, String> entry : infos.entrySet()) {  
            String key = entry.getKey();  
            String value = entry.getValue();  
            sb.append(key + "=" + value + "\n");  
        }  
          
        Writer writer = new StringWriter();  
        PrintWriter printWriter = new PrintWriter(writer);  
        ex.printStackTrace(printWriter);  
        Throwable cause = ex.getCause();  
        while (cause != null) {  
            cause.printStackTrace(printWriter);  
            cause = cause.getCause();  
        }  
        printWriter.close();  
        String result = writer.toString();  
        sb.append(result);  
        try {  
            long timestamp = System.currentTimeMillis();  
            String time = formatter.format(new Date());  
            String fileName = "crash-" + time + "-" + timestamp + ".log";  
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {  
                String path = "/sdcard/crash/";  
                File dir = new File(path);  
                if (!dir.exists()) {  
                    dir.mkdirs();  
                }  
                FileOutputStream fos = new FileOutputStream(path + fileName);  
                fos.write(sb.toString().getBytes());  
                fos.close();  
            }  
            return fileName;  
        } catch (Exception e) {  
            Log.e(TAG, "an error occured while writing file...", e);  
        }  
        return null;  
    }  
}  