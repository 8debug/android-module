# android-module

### gapp 通用module或aar，常用方法+颜色+布局

### gpush 信鸽推送
1、初始化推送`GPushXG.setApplication(mContext);`
2、注册设备   
            `GPushXG.registerPush(getApplicationContext(), new GPushXG.Reg() {
                        @Override
                        public void onSuccess(Object token) {
                            ...
                        }
                    });`
3、在`MessageReceiver`中写自己的逻辑代码

### gphotoview 图片浏览
调用`GPhotoView.startImagePagerActivity(Context context, List<String> imgUrls, int position, int width, int height)`

### gbmap 百度地图
#### 定位功能
1、`activity`继承`GBLocationActivity`   
2、设置开始定位和定位结束的回调函数   
            `mGBLocation.setIGBLocation(new GBLocation.IGBLocation() {
                @Override
                public void onStart() {
                    ...
                }
                @Override
                public void onFinish(boolean isSuccess, GBLocation.GLBean bean) {
                    mGBLocation.stop();
                    ...
                }
            });`
3、开始定位`mGBLocation.start();`,结束定位`mGBLocation.stop();`
