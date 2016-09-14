package gesoft.gapp.common;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class GVHolder extends RecyclerView.ViewHolder {

    private final SparseArray<View> mViews;
    //private final View mConvertView;

    public GVHolder(View itemView) {
        super(itemView);
        mViews = new SparseArray<>();
        //mConvertView = itemView;
    }

    public View getConvertView(){
        return itemView;
    }

    /**
     * 通过控件的Id获取对于的控件，如果没有则加入views
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId){
        View view = mViews.get(viewId);
        if (view == null){
            //view = mConvertView.findViewById(viewId);
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 设置文本
     * @param viewId
     * @param text
     * @return
     */
    public GVHolder setText(int viewId, String text){
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }

    /**
     * 设置tag
     * @param id
     * @param t
     */
    public <T extends Object> void setTag(int id, T t){
        getView(id).setTag(t);
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param drawableId
     * @return
     */
    public GVHolder setImageResource(int viewId, int drawableId){
        ImageView view = getView(viewId);
        view.setImageResource(drawableId);
        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param bm
     * @return
     */
    public GVHolder setImageBitmap(int viewId, Bitmap bm){
        ImageView view = getView(viewId);
        view.setImageBitmap(bm);
        return this;
    }
}