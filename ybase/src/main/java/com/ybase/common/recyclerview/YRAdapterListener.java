package com.ybase.common.recyclerview;

/**
 * Created by yhr on 2016/8/4.
 *
 */

public interface YRAdapterListener {

    interface OnConvert<T>{
        void onConvert(YRVHolder holder, T item);
    }

}
