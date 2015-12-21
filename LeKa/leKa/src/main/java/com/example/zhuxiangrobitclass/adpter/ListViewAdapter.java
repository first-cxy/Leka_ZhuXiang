package com.example.zhuxiangrobitclass.adpter;

import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.zhuxiangrobitclass1.R;
import com.example.zhuxiangrobitclass.viewHolder.ListView_viewHolder;

/**
 * @author fengxb 2014Äê10ÔÂ11ÈÕ
 */
public class ListViewAdapter extends BaseAdapter {
    public Context mContext;
    private ListView_viewHolder listView_viewHolder;
    public static ArrayList<HashMap<String, Object>> mListViewDate = new ArrayList<HashMap<String, Object>>();

    public ListViewAdapter(Context mContext, ArrayList<HashMap<String, Object>> mGridViewDate) {
        this.mContext = mContext;
        this.mListViewDate = mGridViewDate;
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.widget.Adapter#getCount()
     */
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mListViewDate.size();
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.widget.Adapter#getItem(int)
     */
    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.widget.Adapter#getItemId(int)
     */
    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
     */
    @SuppressLint("NewApi")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListView_viewHolder listView_viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.listview_adapter_main, null);
            listView_viewHolder = new ListView_viewHolder();
            listView_viewHolder.text = (TextView) convertView.findViewById(R.id.Listview_ItemText);
            convertView.setTag(listView_viewHolder);
        } else {
            listView_viewHolder = (ListView_viewHolder) convertView.getTag();
        }
        HashMap<String, Object> hashMap = mListViewDate.get(position);
        String textForString = (String) hashMap.get("gridviewName");
        listView_viewHolder.text.setText(textForString);
        return convertView;
    }

}
