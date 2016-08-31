package gesoft.gphotoview;

import android.content.Context;
import android.view.View;

import java.util.List;

/**
 * Created by yhr on 2016/6/27.
 *
 */
public class GPhotoView {

    public static void startImagePagerActivity(Context context, List<String> imgUrls, int position, int width, int height){
        ImagePagerActivity.startImagePagerActivity(context, imgUrls, position, width, height);

    }

    public static void startImagePagerActivity( View view, List<String> imgUrls, int position ){
        ImagePagerActivity.startImagePagerActivity( view.getContext(), imgUrls, position, view.getMeasuredWidth(), view.getMeasuredHeight());

    }

}
