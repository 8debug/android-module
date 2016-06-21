package gesoft.gapp.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

@Deprecated
public class GAdapterViewHolder {
	private final SparseArray<View> mViews;  
    private View mConvertView;  
    private int mPosition;
  
    private GAdapterViewHolder(Context context, ViewGroup parent, int layoutId, int position){
    	this.mPosition = position;
        mViews = new SparseArray<View>();
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);  
        mConvertView.setTag(this);
    }  
  
    /** 
     * 拿到一个ViewHolder对象
     * @param context 
     * @param convertView 
     * @param parent 
     * @param layoutId 
     * @param position 
     * @return 
     */  
    public static GAdapterViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId, int position){
        if (convertView == null){  
            return new GAdapterViewHolder(context, parent, layoutId, position);
        }else{
            GAdapterViewHolder holder = (GAdapterViewHolder) convertView.getTag();
            holder.setPosition(position);
        	return holder;
        }
    }  
  
  
    /** 
     * 通过控件的Id获取对于的控件，如果没有则加入views 
     * @param viewId 
     * @return 
     */  
    public <T extends View> T getView(int viewId){
        View view = mViews.get(viewId);  
        if (view == null){
            view = mConvertView.findViewById(viewId);  
            mViews.put(viewId, view);  
        }  
        return (T) view;
    }  
  
    public View getConvertView(){  
        return mConvertView;  
    }
    
    
    /** 
     * 为TextView设置字符串 
     *  
     * @param viewId 
     * @param text 
     * @return 
     */  
    public GAdapterViewHolder setText(int viewId, String text){
        TextView view = getView(viewId);  
        view.setText(text);  
        return this;  
    }  
  
    /** 
     * 为ImageView设置图片 
     *  
     * @param viewId 
     * @param drawableId 
     * @return 
     */  
    public GAdapterViewHolder setImageResource(int viewId, int drawableId){
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
    public GAdapterViewHolder setImageBitmap(int viewId, Bitmap bm){
        ImageView view = getView(viewId);  
        view.setImageBitmap(bm);  
        return this;  
    }
  
    public int getPosition(){  
        return mPosition;  
    }

    public void setPosition(int position){
        mPosition = position;
    }
}
