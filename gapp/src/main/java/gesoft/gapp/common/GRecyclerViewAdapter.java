package gesoft.gapp.common;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 已废弃，使用GRAdapter代替
 * @deprecated
 */
public class GRecyclerViewAdapter extends RecyclerView.Adapter<GRecyclerViewAdapter.ViewHolder> {

    private final List<JSONObject> mList ;
    private final int mLayout;

    public GRecyclerViewAdapter(int layoutId, List<JSONObject> list) {
        mLayout = layoutId;
        mList = list;
    }

    public GRecyclerViewAdapter(int layoutId) {
        mLayout = layoutId;
        mList = new ArrayList<>();
    }

    public GRecyclerViewAdapter(List<JSONObject> listJson, int layoutId) {
        mList = listJson==null?new ArrayList<JSONObject>():listJson;
        mLayout = layoutId;
    }

    public GRecyclerViewAdapter addAll( List<JSONObject> list ){
        mList.addAll(list);
        return this;
    }

    public GRecyclerViewAdapter add( JSONObject json ){
        mList.add(json);
        return this;
    }

    public GRecyclerViewAdapter add( JSONObject json, int idx ){
        mList.add(idx, json);
        return this;
    }

    public GRecyclerViewAdapter clear(){
        mList.clear();
        return this;
    }

    public GRecyclerViewAdapter remove( int index){
        mList.remove(index);
        return this;
    }

    public JSONObject getItem( int index ){
        return mList.get(index);
    }

    public GRecyclerViewAdapter remove( JSONObject j ){
        mList.remove(j);
        return this;
    }

    public List<JSONObject> getData(){
        return mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mLayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        convert(holder, mList.get(position));
    }

    public void convert(ViewHolder holder, JSONObject jsonItem){

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final SparseArray<View> mViews;
        private final View mView;
        private int mPosition;

        public ViewHolder(View view) {
            super(view);
            mViews = new SparseArray<>();
            mView = view;
        }

        /**
         * 通过控件的Id获取对于的控件，如果没有则加入views
         * @param viewId
         * @return
         */
        public <T extends View> T getView(int viewId){
            View view = mViews.get(viewId);
            if (view == null){
                view = mView.findViewById(viewId);
                mViews.put(viewId, view);
            }
            return (T) view;
        }
        public ViewHolder setText(int viewId, String text){
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
        public ViewHolder setImageResource(int viewId, int drawableId){
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
        public ViewHolder setImageBitmap(int viewId, Bitmap bm){
            ImageView view = getView(viewId);
            view.setImageBitmap(bm);
            return this;
        }
    }
}
