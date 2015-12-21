package com.example.zhuxiangrobitclass.adpter;

import java.util.ArrayList;

import com.example.zhuxiangrobitclass1.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class GListviewAdapterForDrLe extends BaseAdapter {
	public Context c;
	public ArrayList<String> arr = new ArrayList<String>();

	public GListviewAdapterForDrLe(Context c, ArrayList<String> arr) {
		this.c = c;
		this.arr = arr;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arr.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		View convertView = LayoutInflater.from(c).inflate(R.layout.imageadpter, null);
		ImageView imageAdater = (ImageView) convertView.findViewById(R.id.imageAdater);

		String str = arr.get(arg0);
		
		if(str.contains("L0101")){
			imageAdater.setBackgroundResource(R.drawable.l0101);
		}else if(str.contains("L0102")){
			imageAdater.setBackgroundResource(R.drawable.l0102);
		}else if(str.contains("L0103")){
			imageAdater.setBackgroundResource(R.drawable.l0103);
		}else if(str.contains("L0104")){
			imageAdater.setBackgroundResource(R.drawable.l0104);
		}else if(str.contains("L0105")){
			imageAdater.setBackgroundResource(R.drawable.l0105);
		}else if(str.contains("L0106")){
			imageAdater.setBackgroundResource(R.drawable.l0106);
		}else{
			imageAdater.setBackgroundResource(R.drawable.empty);
		}
		return convertView;
	}

}
