package gesoft.gapp.common.listview;

import android.view.View;


/**
 * Created by yhr on 2016/8/4.
 *
 */

public interface GLAdapterListener {

    interface OnConvert<T>{
        void onConvert(GLVHolder holder, T item);
    }

    interface OnItemClick<T>{
        void onItemClick(View view, GLVHolder holder, T item);
    }

    interface OnItemLongClick<T>{
        void onItemLongClick(View view, GLVHolder holder, T item);
    }

}
