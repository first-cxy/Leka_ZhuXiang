package com.example.zhuxiangrobitclass.adpter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.zhuxiangrobitclass1.R;

public class mListviewAdapter extends BaseAdapter{
	public Context c;
	public ArrayList<String>arr=new ArrayList<String>();
	
	public mListviewAdapter(Context c,ArrayList<String>arr){
		this.c=c;
		this.arr=arr;
		
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
	View convertView = LayoutInflater.from(c).inflate
			    (R.layout.imageadpter,null);
	ImageView imageAdater = (ImageView) convertView.findViewById(R.id.imageAdater);
//	CARD-00:01, CARD-00:02
//		   if(arr.get(arg0).equals("CARD-00:01")){
//			   imageAdater.setBackgroundResource(R.drawable.ka1);
//		   }else if(arr.get(arg0).equals("CARD-00:02")){
//			   imageAdater.setBackgroundResource(R.drawable.ka2);
//		   }else if(arr.get(arg0).equals("CARD-00:03")){
//			   imageAdater.setBackgroundResource(R.drawable.ka3);
//		   }else if(arr.get(arg0).equals("CARD-00:04")){
//			   imageAdater.setBackgroundResource(R.drawable.ka4);
//		   }else if(arr.get(arg0).equals("CARD-00:05")){
//			   imageAdater.setBackgroundResource(R.drawable.ka5);
//		   }else if(arr.get(arg0).equals("CARD-00:06")){
//			   imageAdater.setBackgroundResource(R.drawable.ka2);  
//		   }
		return convertView;
	}
  
}
