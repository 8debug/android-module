# android-module

### gapp 通用module或aar，常用方法+颜色+布局

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
GPhotoView.startImagePagerActivity(Context context, List<String> imgUrls, int position, int width, int height)
```

### gbmap 百度地图
#### 定位功能
1、在主`module`中添加
```
<meta-data android:name="com.baidu.lbsapi.API_KEY" android:value="..." />
```
2、`activity`继承`GBLocationActivity`   或者 `fragment` 继承 `GBLocationFragment` 实现`GBLocation.IGBLocation` 接口

3、开始定位/结束定位
```
startLocation();	//开始定位
stopLocation();		//结束定位
```
