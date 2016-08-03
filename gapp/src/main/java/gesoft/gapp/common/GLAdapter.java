package gesoft.gapp.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public abstract class GLAdapter<T> extends BaseAdapter {

    //private Context mContext;
    private List<T> mList = new ArrayList<>();
    private final int mItemLayoutId;

    public GLAdapter(int itemLayoutId){
        this.mItemLayoutId = itemLayoutId;
    }

    /*public GListViewAdapter(Context context, int itemLayoutId){
        this.mContext = context;
        this.mItemLayoutId = itemLayoutId;
    }
  
    public GListViewAdapter(Context context, List<JSONObject> list, int itemLayoutId){
        this.mContext = context;
        this.mList = list != null? list : new ArrayList<JSONObject>();
        this.mItemLayoutId = itemLayoutId;

    }*/

    public GLAdapter addAll(List<T> list ){
        mList.addAll(list);
        return this;
    }

    public void setListData(List<T> list ){
        clear();
        addAll(list);
    }

    public void add( T t ){
        mList.add(t);
    }

    public void add( T t, int idx ){
        mList.add(idx, t);
    }

    public void clear(){
        mList.clear();
    }

    public void remove( int index){
        mList.remove(index);
    }

    public void remove( T j ){
        mList.remove(j);
    }

    public List<T> getData(){
        return mList;
    }
  
    @Override  
    public int getCount()  
    {  
        return mList.size();
    }  
  
    @Override  
    public T getItem(int position){
        return mList.get(position);
    }  
  
    @Override  
    public long getItemId(int position)  
    {  
        return position;  
    }

    /*public abstract void convert(GAdapterViewHolder helper, JSONObject json);*/

    public abstract void convert(GViewHolder helper, T t);
    
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		/*GAdapterViewHolder viewHolder = GAdapterViewHolder.get(mContext, convertView, parent, mItemLayoutId, position);
        convert(viewHolder, getItem(position));*/
        GViewHolder viewHolder;
        if( convertView==null ){
            viewHolder = new GViewHolder(parent.getContext(), parent, mItemLayoutId, position);
        }else{
            viewHolder = (GViewHolder) convertView.getTag();
            viewHolder.setPosition(position);
        }
        convert(viewHolder, getItem(position));
        return viewHolder.getConvertView();  
	}





    public class GViewHolder {
        private final SparseArray<View> mViews;
        private View mConvertView;
        private int mPosition;

        private GViewHolder(Context context, ViewGroup parent, int layoutId, int position){
            this.mPosition = position;
            mViews = new SparseArray<>();
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
        public GViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId, int position){
            if (convertView == null){
                return new GViewHolder(context, parent, layoutId, position);
            }else{
                GViewHolder holder = (GViewHolder) convertView.getTag();
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
        public GViewHolder setText(int viewId, String text){
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
        public GViewHolder setImageResource(int viewId, int drawableId){
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
        public GViewHolder setImageBitmap(int viewId, Bitmap bm){
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
	
}
