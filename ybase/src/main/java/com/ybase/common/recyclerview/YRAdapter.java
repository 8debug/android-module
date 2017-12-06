package com.ybase.common.recyclerview;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class YRAdapter<T> extends RecyclerView.Adapter<YRVHolder> {

    private List<T> mList ;
    private final int mLayout;
    private YRAdapterListener.OnConvert<T> mOnConvertListener;
    //private YRAdapterListener.OnItemClick mOnItemClickListener;
    //private YRAdapterListener.OnItemLongClick mOnItemLongClickListener;
    /*private View mViewFooter;
    private View mViewHeader;*/
    private List<View> mListFooter = new ArrayList<>();
    private List<View> mListHeader = new ArrayList<>();
    private Context mContext;

    public void setOnConverListener( YRAdapterListener.OnConvert onConverListener ){
        mOnConvertListener = onConverListener;
    }

    /*public void setOnItemClickListener( YRAdapterListener.OnItemClick onItemClickListener ){
        mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener( YRAdapterListener.OnItemLongClick onItemLongClickListener ){
        mOnItemLongClickListener = onItemLongClickListener;
    }*/

    private final static int ITEM_TYPE_FOOTER = 1000;
    private final static int ITEM_TYPE_HEADER = 1001;

    public YRAdapter(Context context, int layoutId, List<T> list) {
        mContext = context;
        mLayout = layoutId;
        mList = list==null?new ArrayList<T>():list;
    }

    public YRAdapter(Context context, int layoutId, List<T> list, YRAdapterListener.OnConvert<T> onConvertListener) {
        mContext = context;
        mLayout = layoutId;
        mList = list==null?new ArrayList<T>():list;
        mOnConvertListener = onConvertListener;
    }

    /*public YRAdapter( int layoutId, List<T> list, YRAdapterListener.OnConvert<T> onConvertListener) {
        mLayout = layoutId;
        mList = list==null?new ArrayList<T>():list;
        mOnConvertListener = onConvertListener;
    }*/

    /*public YRAdapter(Context context, int layoutId, YRAdapterListener.OnConvert<T> onConvertListener){
        mContext = context;
        mLayout = layoutId;
        mList = new ArrayList<>();
        mOnConvertListener = onConvertListener;
    }*/

    /*public YRAdapter( int layoutId, YRAdapterListener.OnConvert<T> onConvertListener){
        mLayout = layoutId;
        mList = new ArrayList<>();
        mOnConvertListener = onConvertListener;
    }*/

    public YRAdapter setData(List<T> list ){
        mList = list;
        return this;
    }

    public YRAdapter addAll(List<T> list ){
        mList.addAll(list);
        return this;
    }

    public YRAdapter add(T item ){
        mList.add(item);
        return this;
    }

    public YRAdapter add(int idx , T item ){
        mList.add(idx, item);
        return this;
    }

    public YRAdapter clear(){
        mList.clear();
        return this;
    }

    public YRAdapter remove(int index){
        mList.remove(index);
        return this;
    }

    public T getItem( int index ){
        return mList.get(index);
    }

    public YRAdapter remove(T item ){
        mList.remove(item);
        return this;
    }

    public List<T> getData(){
        return mList;
    }

    @Override
    public YRVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if( viewType==ITEM_TYPE_HEADER ){
            view = mListHeader.get(0);
        }else if( viewType==ITEM_TYPE_FOOTER ){
            view = mListFooter.get(0);
        }else{
            //view = LayoutInflater.from( parent.getContext() ).inflate(mLayout, parent, false);
            view = LayoutInflater.from( mContext ).inflate(mLayout, parent, false);
        }

        return new YRVHolder(view);
    }

    @Override
    public void onBindViewHolder(final YRVHolder holder, int position) {

        if( !isHeader(position) && !isFooter(position) ){

            final int index = position - mListHeader.size();

            if( mOnConvertListener!=null ){
                mOnConvertListener.onConvert(holder, mList.get(index));
            }

            /*holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if( mOnItemClickListener!=null ){
                        mOnItemClickListener.onItemClick( v, holder, mList.get(index));
                    }
                }
            });

            holder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if( mOnItemLongClickListener!=null ){
                        mOnItemLongClickListener.onItemLongClick( v, holder, mList.get(index));
                    }
                    return true;
                }
            });*/

        }

    }

    @Override
    public int getItemCount() {
        int count = mList.size();
        if( mListHeader.size() >0 ){
            count += mList.size();
        }
        if( mListFooter.size() >0 ){
            count += mListFooter.size();
        }
        return count;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        if( isHeader(position) ){
            return ITEM_TYPE_HEADER;
        }else if( isFooter(position) ){
            return ITEM_TYPE_FOOTER;
        }else{
            return super.getItemViewType(position);
        }
    }

    @Override
    public void onViewAttachedToWindow(YRVHolder holder) {
        super.onViewAttachedToWindow(holder);
        if ( isFooter(holder.getLayoutPosition()) ) {
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
            if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
                p.setFullSpan(true);
            }
        }
    }

    /**
     * 判断此RecyclerView的LayoutManager类型
     * @param recyclerView
     */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) layoutManager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if ( isFooter(position) ) {
                        return gridManager.getSpanCount();
                    }
                    return 1;
                }
            });
        }
    }

    /*private boolean isScrollFooter( int position ){
        return position >= getItemCount()-1;
    }*/

    /**
     * 获取尾部
     * @return
     */
    public View getFooterView(){
        if( mListFooter.size()>0 ){
            return mListFooter.get(0);
        }
        return null;
    }

    public void showFooterView(){
        if( mListFooter.size()>0 ){
            mListFooter.get(0).setVisibility(View.VISIBLE);
        }
    }

    public void hideFooterView(){
        if( mListFooter.size()>0 ){
            mListFooter.get(0).setVisibility(View.GONE);
        }
    }

    public void addFooterView( int resId ){
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT );
        View viewFooter = LayoutInflater.from(mContext).inflate(resId, null);
        viewFooter.setLayoutParams( params );
        mListFooter.clear();
        mListFooter.add(viewFooter);
    }

    public void addHeaderView( int resId ){
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT );
        View viewHeader = LayoutInflater.from(mContext).inflate(resId, null);
        viewHeader.setLayoutParams( params );
        mListHeader.clear();
        mListHeader.add(viewHeader);
    }

    private boolean isFooter( int position ){
        return position >= mListHeader.size() + mList.size();
    }

    private boolean isHeader( int position ){
        return position < mListHeader.size() ;
    }
}
