# android-module

### gapp 常用方法+颜色+布局
gapp中包括如下依赖
```
compile 'com.android.support:design:23.4.0'
compile 'com.jakewharton:butterknife:7.0.1'
compile 'com.facebook.fresco:fresco:0.13.0'
compile 'com.squareup.retrofit2:retrofit:2.1.0'
compile 'com.squareup.okhttp3:okhttp:3.4.1'
compile 'com.demievil.library:refreshlayout:1.0.0@aar'
compile 'com.squareup.retrofit2:converter-scalars:2.0.0'
```
1、将```GApplication ```中对fresco进行了初始化，将```GApplication ```引入到 ```AndroidManifest.xml ```中作为基础 ```Application ```

2、gapp中的方法
```java
GApp.getAppName( Context context );	//获取应用程序名称
GApp.getVersionName( Context context );	//当前应用的版本名称
GData.putJSON( JSONObject j, String key, Object value );//保存json
GData.putJSONArray( JSONArray array, int index, Object obj );//向JSONArray中添加json对象
GData.URLEncoder( String str, String encode );//对字符串进行编码
GData.URLEncoderGBK( String str );//对字符串进行GBK编码
GData.URLEncoderUTF8( String str );//对字符串进行UTF8编码
GData.parseJSON( Object obj );//对象转JSONObject对象
GData.parseList( JSONArray array );//JSONArray转List<JSONObject> listJson

GDate.getDate( String format, Date date );//转化时间
GDate.getDate( String format, Calendar calendar );//转化时间
GDate.getNowDate( String format );//当前时间
GDate.getDay();//获取当前天是当前月的几号
GDate.getMonth();//获取当前月
GDate.getYear();//获取当前年

//strDate2>strDate1 >0   strDate2==strDate1 = 0     strDate2<strDate1 <-1
GDate.compareDate( String format, String strDate1, String strDate2 );
GDate.parse( String format, String strDate );//strDate转Date

GFile.deleteFile( String path );//删除文件
GFile.deleteFile( File file );//删除文件
GFile.deleteFiles( String ... arrayPath );//删除多个文件
GFile.deleteFiles( File... arrayFile );//删除多个文件
GFile.getFileList( String zip路径, boolean 是否包含文件夹, boolean 是否包含文件);//取得压缩包中的 文件列表(文件夹,文件自选)
GFile.upZip( String zip文件名, String 解压文件的名字 );//返回压缩包中的文件InputStream
GFile.unZipFolder( String zipFileString, String outPathString );//解压一个压缩文档到指定位置
GFile.zipFolder( String srcFileString, String zipFileString );//压缩文件,文件夹
GFile.getZipFiles( String zipPath, String... arrayFilePath );//压缩多个文件
GFile.zipFiles( String folderString, String fileString, java.util.zip.ZipOutputStream zipOutputSteam );//压缩文件
GFile.createFileImage( Context context );//在SD卡根目录中创建jpg文件，文件名为时间戳
GFile.createFileImage( Context context, String fileName );//创建文件
GFile.delFileOrDir( String pathDir );//删除文件或目录

GIntent.getIntentCamera( Context ctx, File fileOut );//获取拍照的Intent
GIntent.getIntentCall( Context context, String phoneNumber );//获取打电话的Intent，需要拨号权限

GNumber.isNumber( String str );//验证是否为纯数字

GView.setFocuse( View ...views );//获取焦点
GView.setEnabledRadioGroup(  RadioGroup group, boolean isEnabled  );//启用/禁用RadioGroup

GPhone.getPhoneName();//获取手机型号
GPhone.getAndroidVersion();//获取手机的android版本
GPhone.getIMEI( Context context );//获取手机IMEI码
GPhone.getPhoneNumber( Context context );//获取手机号码
GPhone.getMetric( Context context );//获取屏幕宽度, 屏幕高度, 屏幕密度, 屏幕密度DPI

GSDCard.isSDCardEnable();//判断SDCard是否可用
GSDCard.getSDCardPath();//获取SD卡路径
GSDCard.getSDCardAllSize();//获取SD卡的剩余容量 单位byte
GSDCard.getFreeBytes(String filePath);//获取指定路径所在空间的剩余可用容量字节数，单位byte
GSDCard.getRootDirectoryPath();//获取系统存储路径

GImage.revitionImageSize( String path );//压缩图片
GImage.getBitmap( String imgPath );
GImage.storeImage( Bitmap bitmap, String outPath );//保存图片
GImage.addWaterMarker( Bitmap bitmap, String text, int textSize, float x, float y, int width, int height );//添加文字水印
```


### gpush 信鸽推送
1、初始化推送
```
GPushXG.setApplication(mContext);
```
2、注册设备
```
GPushXG.registerPush(getApplicationContext(), new GPushXG.Reg() {
    @Override
    public void onSuccess(Object token) {
        ...
    }
});
```
3、在`MessageReceiver`中写自己的逻辑代码

### gphotoview 图片浏览
```
//第一种方式
GPhotoView.startImagePagerActivity(Context context, List<String> imgUrls, int position, int width, int height);
//dierz
GPhotoView.startImagePagerActivity( View view, List<String> imgUrls, int position );
```

### gbmap
#### 定位功能采用百度定位
1、在主`module`中添加
```handlebars
<meta-data android:name="com.baidu.lbsapi.API_KEY" android:value="..." />
```
2、`fragment`继承 `fragment` 继承 `GBLocationFragment` 实现`GBLocation.IGBLocation` 接口
```java
void onLocationStart();//定位开始前

/**
 * 定位结束后
 * @param isSuccess 定位成功返回true否则false
 * @param bean  定位成功返回定位信息，否则返回null
 */
void onLocationFinish(boolean isSuccess, GLBean bean);
```

3、开始定位/结束定位
```java
startLocation();	//开始综合定位
startLocationGPS(); //开始GPS定位
stopLocation();		//结束综合定位

```

### gcrashemail
#### 发送崩溃日志到邮箱
1、账号密码以及smtp配置需要修改源码
```java
/*	例子
**	若要收集全局crash信息将代码写到自定义的Application中
*/
GEmail email = new GEmail();
email.setUserName("xxx@163.com");
email.setUserPwd("xxxxx");
email.setToAddress("xxx@163.com");
email.setNick(GApp.getAppName(mContext)+"_"+GApp.getVersionName(mContext));
email.setSubject(GPhone.getPhoneName()+", "+GPhone.getPhoneNumber(mContext));
GCrashHandler crashHandler = GCrashHandler.getInstance();
crashHandler.setGEMail(email);
crashHandler.init(mContext);
```

### gphotopicker
#### 仿微信相册
1、调用相册
```java
Intent intent = new Intent(context, PhotoPickerActivity.class);
//相册中是否显示照相
intent.putExtra(PhotoPickerActivity.EXTRA_SHOW_CAMERA, true);
//允许多选
intent.putExtra(PhotoPickerActivity.EXTRA_SELECT_MODE, PhotoPickerActivity.MODE_MULTI);
//最多选3张
intent.putExtra(PhotoPickerActivity.EXTRA_MAX_MUN, 3);
//PICK_PHOTO为自定义请求码
startActivityForResult(intent, PICK_PHOTO);
```

2、获取相册中图片
```java
public void onActivityResult(int requestCode, int resultCode, Intent data) {
	int resultOk = PhotoPickerActivity.RESULT_OK;
	if ( resultOk  == resultCode && requestCode == PICK_PHOTO) {
		//获取图片
		ArrayList<String> array = data.getStringArrayListExtra(PhotoPickerActivity.KEY_RESULT);
		int action = data.getIntExtra(PhotoPickerActivity.KEY_ACTION, 0);
        //通过照相获取的图片
        if( action==PhotoPickerActivity.ACTION_CAMERA ){}
        }
}
```