package com.ybase.common.listview;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class YLVHolder {
        private final SparseArray<View> mViews;
        private View mConvertView;
        private int mPosition;

        public YLVHolder(Context context, ViewGroup parent, int layoutId, int position){
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
        public YLVHolder get(Context context, View convertView, ViewGroup parent, int layoutId, int position){
            if (convertView == null){
                return new YLVHolder(context, parent, layoutId, position);
            }else{
                YLVHolder holder = (YLVHolder) convertView.getTag();
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
        public YLVHolder setText(int viewId, String text){
            TextView view = getView(viewId);
            view.setText(text);
            return this;
        }

        /**
         * 为View设置Tag对象值
         * @param viewId
         * @param value
         * @return
         */
        public YLVHolder setTag(int viewId, Object value ){
            getView(viewId).setTag(value);
            return this;
        }

        /**
         * 为ImageView设置图片
         *
         * @param viewId
         * @param drawableId
         * @return
         */
        public YLVHolder setImageResource(int viewId, int drawableId){
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
        public YLVHolder setImageBitmap(int viewId, Bitmap bm){
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