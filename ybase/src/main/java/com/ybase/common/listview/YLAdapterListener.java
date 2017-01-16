package com.ybase.common.listview;

/**
 * Created by yhr on 2016/8/4.
 *
 */

public interface YLAdapterListener {

    interface OnConvert<T>{
        void onConvert(YLVHolder holder, T item);
    }

}
