# android-module

### gapp 通用module或aar，常用方法+颜色+布局

### gpush 信鸽推送
1、初始化推送`GPushXG.setApplication(mContext);`
2、注册设备`GPushXG.registerPush(getApplicationContext(), new GPushXG.Reg() {
            @Override
            public void onSuccess(Object token) {
                ...
            }
        });`
3、在`MessageReceiver`中写自己的逻辑代码

### gphotoview 图片浏览
调用`GPhotoView.startImagePagerActivity(Context context, List<String> imgUrls, int position, int width, int height)`
