package gesoft.gapp.common;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public abstract class GRAdapter<T> extends RecyclerView.Adapter<GVHolder> {

    private final List<T> mList ;
    private final int mLayout;

    public GRAdapter(int layoutId, List<T> list) {
        mLayout = layoutId;
        mList = list==null?new ArrayList<T>():list;
    }

    /*public GRAdapter(int layoutId) {
        mLayout = layoutId;
        mList = new ArrayList<>();
    }*/

    /*public GRAdapter(List<T> list, int layoutId) {
        mList = list==null?new ArrayList<T>():list;
        mLayout = layoutId;
    }*/

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
        convert(holder, mList.get(position));
    }

    public abstract void convert(GVHolder holder, T t);

    @Override
    public int getItemCount() {
        return mList.size();
    }


}
