package gesoft.gapp.common;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class GRAdapter<T> extends RecyclerView.Adapter<GVHolder> {

    private final List<T> mList ;
    private final int mLayout;
    private GRAdapterListener.OnConvert<T> mOnConvertListener;

    /*public void setOnConvertListener( GRAdapterListener.OnConvert onConvert ){
        mOnConvertListener = onConvert;
    }*/

    /*private GRAdapterListener.OnItemClick mOnItemClickListener;
    private GRAdapterListener.OnItemLongClick mOnItemLongClickListener;

    public void setOnItemClickListener( GRAdapterListener.OnItemClick onItemClick ){
        mOnItemClickListener = onItemClick;
    }

    public void setOnItemLongClickListener( GRAdapterListener.OnItemLongClick onItemLongClick ){
        mOnItemLongClickListener = onItemLongClick;
    }*/

    public GRAdapter(int layoutId, List<T> list, GRAdapterListener.OnConvert<T> onConvertListener) {
        mLayout = layoutId;
        mList = list==null?new ArrayList<T>():list;
        mOnConvertListener = onConvertListener;
    }

    public GRAdapter(int layoutId, GRAdapterListener.OnConvert<T> onConvertListener){
        mLayout = layoutId;
        mList = new ArrayList<>();
        mOnConvertListener = onConvertListener;
    }



    public GRAdapter addAll(List<T> list ){
        mList.addAll(list);
        return this;
    }

    public GRAdapter add(T t ){
        mList.add(t);
        return this;
    }

    public GRAdapter add(T t, int idx ){
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
        View view = LayoutInflater.from(parent.getContext()).inflate(mLayout, parent, false);
        return new GVHolder(view);
    }

    @Override
    public void onBindViewHolder(final GVHolder holder, int position) {

        if( mOnConvertListener!=null ){
            mOnConvertListener.onConvert(holder, mList.get(position));
        }

    }

    //public abstract void convert(GVHolder holder, T t);

    @Override
    public int getItemCount() {
        return mList.size();
    }


}
