package gesoft.gapp.common;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class GRAdapter<T> extends RecyclerView.Adapter<GVHolder> {

    private List<T> mList ;
    private final int mLayout;
    private GRAdapterListener.OnConvert<T> mOnConvertListener;
    private GRAdapterListener.OnItemClick mOnItemClickListener;
    private GRAdapterListener.OnItemLongClick mOnItemLongClickListener;
    private View mViewFooter;
    private View mViewHeader;
    private Context mContext;

    public void setOnItemClickListener( GRAdapterListener.OnItemClick onItemClickListener ){
        mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener( GRAdapterListener.OnItemLongClick onItemLongClickListener ){
        mOnItemLongClickListener = onItemLongClickListener;
    }

    private final static int ITEM_TYPE_FOOTER = 1000;
    private final static int ITEM_TYPE_HEADER = 1001;

    public GRAdapter(Context context, int layoutId, List<T> list, GRAdapterListener.OnConvert<T> onConvertListener) {
        mContext = context;
        mLayout = layoutId;
        mList = list==null?new ArrayList<T>():list;
        mOnConvertListener = onConvertListener;
    }

    public GRAdapter(Context context, int layoutId, GRAdapterListener.OnConvert<T> onConvertListener){
        mContext = context;
        mLayout = layoutId;
        mList = new ArrayList<>();
        mOnConvertListener = onConvertListener;
    }

    public GRAdapter setData(List<T> list ){
        mList = list;
        return this;
    }

    public GRAdapter addAll(List<T> list ){
        mList.addAll(list);
        return this;
    }

    public GRAdapter add(T t ){
        mList.add(t);
        return this;
    }

    public GRAdapter add(int idx , T t ){
        mList.add(idx, t);
        return this;
    }

    public GRAdapter clear(){
        mList.clear();
        return this;
    }

    public GRAdapter remove(int index){
        mList.remove(index);
        return this;
    }

    public T getItem( int index ){
        return mList.get(index);
    }

    public GRAdapter remove(T t ){
        mList.remove(t);
        return this;
    }

    public List<T> getData(){
        return mList;
    }

    @Override
    public GVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if( viewType==ITEM_TYPE_HEADER ){
            view = mViewHeader;
        }else if( viewType==ITEM_TYPE_FOOTER ){
            view = mViewFooter;
        }else{
            view = LayoutInflater.from( mContext ).inflate(mLayout, parent, false);
        }

        return new GVHolder(view);
    }

    @Override
    public void onBindViewHolder(final GVHolder holder, final int position) {

        if( getData().size()>position ){

            if( mOnConvertListener!=null ){
                mOnConvertListener.onConvert(holder, mList.get(position));
            }

            if( mOnItemClickListener!=null ){
                holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onItemClick(holder, mList.get(position));
                    }
                });
            }

            if( mOnItemLongClickListener!=null ){
                holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemLongClickListener.onItemLongClick(holder, mList.get(position));
                    }
                });
            }

        }





    }

    @Override
    public int getItemCount() {
        int count = mList.size();
        if( mViewHeader !=null ){
            count++;
        }
        if( mViewFooter !=null ){
            count++;
        }
        return count;
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
    public void onViewAttachedToWindow(GVHolder holder) {
        super.onViewAttachedToWindow(holder);
        if ( mViewFooter!=null && isFooter(holder.getLayoutPosition())) {
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
                    if ( isScrollFooter(position)) {
                        return gridManager.getSpanCount();
                    }
                    return 1;
                }
            });
        }
    }

    private boolean isScrollFooter( int position ){
        return position >= getItemCount()-1;
    }

    public void addFooterView( int resId ){
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT );
        mViewFooter = LayoutInflater.from(mContext).inflate(resId, null);
        mViewFooter.setLayoutParams( params );
    }

    private boolean isFooter( int position ){
        return mViewFooter !=null && position >= getItemCount()-1 ;
    }

    private boolean isHeader( int position ){
        return mViewHeader !=null && position ==0 ;
    }
}
