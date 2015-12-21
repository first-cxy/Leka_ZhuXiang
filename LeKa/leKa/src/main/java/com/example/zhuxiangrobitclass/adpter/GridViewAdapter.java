/**
 * 
 */
package com.example.zhuxiangrobitclass.adpter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhuxiangrobitclass1.R;
import com.example.zhuxiangrobitclass.mApplication;
import com.example.zhuxiangrobitclass.net.SyncImageLoader;
import com.example.zhuxiangrobitclass.net.SyncImageLoader.OnImageLoadListener;
import com.example.zhuxiangrobitclass.ui.ProImagePagerActivity;
import com.example.zhuxiangrobitclass.ui.SubjectsCategory;
import com.example.zhuxiangrobitclass.util.ImageLoadConfog;
import com.example.zhuxiangrobitclass.viewHolder.GridView_viewHolder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * @author fengxb 2014Äê10ÔÂ11ÈÕ
 */
public class GridViewAdapter extends BaseAdapter {
	public Context mContext;
	public SyncImageLoader.OnImageLoadListener imageLoadListener;
	private GridView_viewHolder gridView_viewHolder;
	public static ArrayList<HashMap<String, Object>> mGridViewDate = new ArrayList<HashMap<String, Object>>();
	private SyncImageLoader asyncImageLoader;
	private mApplication mapplication;
	// private ImageLoader imageLoader;
	// private final Options decodingOptions;
	private String TAG = "GridViewAdapter";

	public GridViewAdapter(Context mContext,
			ArrayList<HashMap<String, Object>> mGridViewDate) {
		this.mContext = mContext;
		this.mGridViewDate = mGridViewDate;
		mapplication = (com.example.zhuxiangrobitclass.mApplication) mContext
				.getApplicationContext();
		// this.imageLoader = ImageLoader.getInstance();
		ImageLoader.getInstance().init(mApplication.config);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mGridViewDate.size();
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
	 * @see android.widget.Adapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
	public static View v;

	@SuppressLint("NewApi")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		GridView_viewHolder gv_vHolder;
		asyncImageLoader = new SyncImageLoader();
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.gridview_adapter_main, null);
			gridView_viewHolder = new GridView_viewHolder();
			gridView_viewHolder.text = (TextView) convertView
					.findViewById(R.id.Gridview_ItemText);
			gridView_viewHolder.image = (ImageView) convertView
					.findViewById(R.id.Gridview_ItemImage);
			convertView.setTag(gridView_viewHolder);
			v = convertView;
		} else {
			gridView_viewHolder = (GridView_viewHolder) convertView.getTag();
		}
		HashMap<String, Object> hashMap = mGridViewDate.get(position);
		String textForString = (String) hashMap.get("gridviewName");
		gridView_viewHolder.text.setText(textForString);
		int gridViewId = (Integer) hashMap.get("gridviewId");
		if (gridViewId > 99) {
			gridView_viewHolder.image.setImageDrawable(mContext.getResources()
					.getDrawable(R.drawable.icon_class0));
		} else {

			String gridviewUrl = (String) hashMap.get("gridviewUrl");
			gridView_viewHolder.image.setTag("position");
			// ImageLoader.getInstance().displayImage(baseurl + gridviewUrl,
			// gridView_viewHolder.image);

			// Log.e("BaseURLToString=", mApplication.getBaseUrl());
			String baseUrl = mapplication.getBaseUrl();
			// ImageLoader.getInstance().displayImage( baseUrl + gridviewUrl,
			// gridView_viewHolder.image);
			Log.i("GridViewAdapter", baseUrl + gridviewUrl);

			ImageLoader.getInstance().displayImage(baseUrl + gridviewUrl,
					gridView_viewHolder.image,
					ImageLoadConfog.geteDisPlayImageOption());
			// asyncImageLoader.loadImage(position, baseUrl + gridviewUrl,
			// new OnImageLoadListener() {
			//
			// @Override
			// public void onImageLoad(Integer t, Drawable drawable) {
			// // TODO Auto-generated method stub
			// // View view =
			// // SubjectsCategory.Subject_gridview_id.findViewWithTag(t);
			// // if (t == gridView_viewHolder.ge) {
			// // ImageView iv = (ImageView) v
			// // .findViewById(R.id.Gridview_ItemImage);
			// if (drawable != null) {
			// gridView_viewHolder.image.setBackgroundDrawable(drawable);
			// } else {
			// gridView_viewHolder.image.setBackground(mApplication.mmaApplication.getResources()
			// .getDrawable(R.drawable.ic_launcher));
			// }
			// }
			//
			// @Override
			// public void onError(Integer t) {
			// // TODO Auto-generated method stub
			//
			// }
			//
			// @Override
			// public void onGetImageView() {
			// // TODO Auto-generated method stub
			//
			// }
			// }, textForString);
		}
		return convertView;
	}

	public void loadImage() {
		int start = SubjectsCategory.Subject_gridview_id
				.getFirstVisiblePosition();
		int end = SubjectsCategory.Subject_gridview_id.getLastVisiblePosition();
		if (end >= getCount()) {
			end = getCount() - 1;
		}
		asyncImageLoader.setLoadLimit(start, end);
		asyncImageLoader.unlock();
	}

	AbsListView.OnScrollListener onScrollListener = new AbsListView.OnScrollListener() {

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			switch (scrollState) {
			case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
				asyncImageLoader.lock();
				break;
			case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
				loadImage();
				break;
			case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
				asyncImageLoader.lock();
				break;

			default:
				break;
			}

		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			// TODO Auto-generated method stub

		}
	};

}
