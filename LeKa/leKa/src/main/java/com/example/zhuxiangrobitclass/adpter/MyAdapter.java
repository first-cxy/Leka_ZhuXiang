package com.example.zhuxiangrobitclass.adpter;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.View;

	class MyAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return Integer.MAX_VALUE;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public int getItemPosition(Object object) {
			return super.getItemPosition(object);
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			// ((ViewPager) arg0).removeView(list.get(arg1));
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			try {
				// 设置循环
				// ((ViewPager) arg0).addView(list.get(arg1%list.size()),0);
//				((ViewPager) arg0).addView(list.get(arg1));
			} catch (Exception e) {
			}
			// return list.get(arg1%list.size());
//			if (arg1 > list.size() - 1) {
//				NumberOneClass.this.finish();
//				Util.showToast(NumberOneClass.this, "亲 第一个课时结束了");
//				overridePendingTransition(R.anim.slide_left, R.anim.hold);
//				return null;
//			} else {
//
				return arg0;
//			}

		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {

		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {

		}

		@Override
		public void finishUpdate(View arg0) {

		}
	}

