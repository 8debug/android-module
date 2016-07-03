package gesoft.gphotoview;

import android.content.Context;

import java.util.List;

/**
 * Created by Administrator on 2016/6/27.
 */
public class GPhotoView {

    public static void startImagePagerActivity(Context context, List<String> imgUrls, int position, int width, int height){
        ImagePagerActivity.startImagePagerActivity(context, imgUrls, position, width, height);

    }

}
