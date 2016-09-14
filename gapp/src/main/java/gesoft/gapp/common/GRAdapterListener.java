package gesoft.gapp.common;

import android.view.View;

/**
 * Created by Administrator on 2016/8/4.
 */

public interface GRAdapterListener {

    interface OnConvert<T>{
        void onConvert( GVHolder holder, T t );
    }

    interface OnItemClick<T>{
        void onItemClick( GVHolder holder, T t );
    }

    interface OnItemLongClick<T>{
        void onItemLongClick( GVHolder holder, T t );
    }

}
