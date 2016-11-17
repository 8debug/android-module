package gesoft.gapp.common.recyclerview;

import android.view.View;

/**
 * Created by yhr on 2016/8/4.
 *
 */

public interface GRAdapterListener {

    interface OnConvert<T>{
        void onConvert(GRVHolder holder, T item );
    }

    interface OnItemClick<T>{
        void onItemClick(View view, GRVHolder holder, T item );
    }

    interface OnItemLongClick<T>{
        void onItemLongClick(View view, GRVHolder holder, T item );
    }

}
