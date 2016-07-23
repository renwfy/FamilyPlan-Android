package com.lib.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/***
 *
 * @param <T>
 */
public abstract class SimpleAdapter<T> extends BaseAdapter {

    protected Context context;
    protected List<T> list = null;

    public SimpleAdapter(Context context) {
        this.context = context;
    }

    public SimpleAdapter(Context context, List<T> data) {
        this.context = context;
        list = data;
    }


    public void setData(List<T> list){
        this.list = list;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list == null ? null : list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return list == null ? 0 : position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        T data = list.get(position);
        return getView(position, data, convertView);
    }

    public abstract View getView(int position, T data, View convertView);
}
