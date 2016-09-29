package gesoft.gshare;

import android.content.Context;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by yhr on 2016/9/29.
 * 分享模块
 */

public class GShareSDK {

    /**
     * 分享
     * @param context
     * @param title         标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
     * @param qqTitleUrl    标题的网络链接，QQ和QQ空间等使用
     * @param text          分享文本，所有平台都需要这个字段
     * @param imgUrl        确保SDcard下面存在此张图片
     * @param wxUrl         仅在微信（包括好友和朋友圈）中使用
     * @param comment       对这条分享的评论，仅在人人网和QQ空间使用
     * @param qqSite        分享此内容的网站名称，仅在QQ空间使用
     * @param qqSiteUrl     分享此内容的网站地址，仅在QQ空间使用
     */
    public static void share( Context context,
                              String title,
                              String qqTitleUrl,
                              String text,
                              String imgUrl,
                              String wxUrl,
                              String comment,
                              String qqSite,
                              String qqSiteUrl ) {
        ShareSDK.initSDK( context );
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle( title );
        // titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl( qqTitleUrl );
        // text是分享文本，所有平台都需要这个字段
        oks.setText( text );
        oks.setImagePath( imgUrl );//确保SDcard下面存在此张图片
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl( wxUrl );
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment( comment );
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite( qqSite );
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl( qqSiteUrl );

        oks.show(context);
    }

}
