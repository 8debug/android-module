package gesoft.gbmap.service;

import android.content.Context;
import android.os.Binder;

import gesoft.gbmap.GBLocation;

/**
 * Created by yhr on 2016/9/13.
 *
 */
public class GLocationBinder extends Binder{

    private Context mContext;
    private GBLocation mGBLocation;

   /* public GLocationBinder( Context context, GBLocation.IGBLocation igbLocation ){
        mContext = context;
        mGBLocation = new GBLocation(mContext.getApplicationContext());
        mGBLocation.initLocation( false );
        mGBLocation.setIGBLocation(igbLocation);
    }*/

}
