package gesoft.gapp.common;

import android.view.View;

/**
 * Created by Administrator on 2016/8/4.
 */

public interface GRAdapterListener {

    interface OnConvert<T>{
        void onConvert( GVHolder holder, T t );
    }

    interface OnItemClick extends View.OnClickListener{
    }

    interface OnItemLongClick extends View.OnLongClickListener{
    }

}
