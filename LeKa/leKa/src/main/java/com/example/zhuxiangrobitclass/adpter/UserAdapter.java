package com.example.zhuxiangrobitclass.adpter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class UserAdapter extends PagerAdapter {

	private List<View> viewList;
	private Context context;
	
	private List<String> titleList = new ArrayList<String>();
	
	public UserAdapter(Context context, List<View> viewList ) {
		super();
		titleList.add("µÇÂ½");
		titleList.add("×¢²á");

		this.viewList = viewList;
		this.context = context;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		container.addView(viewList.get(position));
		
		return viewList.get(position);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return viewList.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0==arg1;
	}

	@Override
	public int getItemPosition(Object object) {
		// TODO Auto-generated method stub
		return super.getItemPosition(object);
	}

	@Override
	public CharSequence getPageTitle(int position) {
		// TODO Auto-generated method stub
		return titleList.get(position);
	}

}
