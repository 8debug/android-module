package com.ybase.common.listview;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public class YLAdapter<T> extends BaseAdapter {

    //private Context mContext;
    private List<T> mList = new ArrayList<>();
    private final int mItemLayoutId;
    private YLAdapterListener.OnConvert<T> mOnConvertListener;

    public void setOnConverListener( YLAdapterListener.OnConvert onConverListener ){
        mOnConvertListener = onConverListener;
    }

    public YLAdapter(int itemLayoutId, List<T> list, YLAdapterListener.OnConvert<T> onConvertListener ){
        this.mItemLayoutId = itemLayoutId;
        this.mList = list;
        this.mOnConvertListener = onConvertListener;
    }

    public YLAdapter addAll(List<T> list ){
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
    
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

        YLVHolder viewHolder;
        if( convertView==null ){
            viewHolder = new YLVHolder(parent.getContext(), parent, mItemLayoutId, position);
        }else{
            viewHolder = (YLVHolder) convertView.getTag();
            viewHolder.setPosition(position);
        }


        if( mOnConvertListener!=null ){
            mOnConvertListener.onConvert(viewHolder, getItem(position));
        }

        return viewHolder.getConvertView();  
	}
	
}
