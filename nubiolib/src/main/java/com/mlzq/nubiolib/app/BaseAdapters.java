package com.mlzq.nubiolib.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Dev on 2017/8/26.
 */

public abstract class BaseAdapters<T> extends BaseAdapter {
    protected LayoutInflater mInflater;
    protected Context context;
    protected List<T> list;
    protected final int mItemLayoutId;


    public BaseAdapters(Context context, List<T> list, int itemLayoutId) {
          mInflater = LayoutInflater.from(context);
        this.context = context;
        this.list = list;
        this.mItemLayoutId=itemLayoutId;
    }

    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    @Override
    public T getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       final BaseViewHolder viewholder=getViewHolder(position,convertView,parent);
        convert(viewholder,getItem(position),position);
        return viewholder.getmConvertView();
    }

    /**
     * 对外公布了一个convert方法，并且还把viewHolder和本Item对于的Bean对象给传出去。通过ViewHolder把View找到，通过Item设置值
     * @param helper
     * @param item
     */


    public abstract void convert(BaseViewHolder helper, T item,int position);
    private BaseViewHolder getViewHolder(int postion, View convertView, ViewGroup parent){
        return BaseViewHolder.get(context,convertView,postion,mItemLayoutId,parent);
    }
}
