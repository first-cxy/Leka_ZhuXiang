/**
 * 
 */
package com.example.zhuxiangrobitclass.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.zhuxiangrobitclass.mApplication;
import com.example.zhuxiangrobitclass.net.Listview_Task;
import com.example.zhuxiangrobitclass.ui.base.BaseActivity;
import com.example.zhuxiangrobitclass.util.Logger;
import com.example.zhuxiangrobitclass1.R;

/**
 * @author fengxb 2014Äê10ÔÂ11ÈÕ
 */
public class ClassCategory extends BaseActivity implements OnItemClickListener {
	public static String Tag;
	public ArrayList<HashMap<String, Object>> mGridViewDate = new ArrayList<HashMap<String, Object>>();
	public HashMap<String, Object> hashmap;
	public static ListView Subject_List;
	private int clazNumber;
	private Listview_Task listview_Task;

	private final static String TAG = "ClassCategory";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.example.zhuxiangrobitclass.ui.base.BaseActivity#onCreate(android.
	 * os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activty_classcategory_main);

		Tag = ClassCategory.class.getName();
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		clazNumber = extras.getInt("SubjectForInt");
		initView();

		initDateforNet();
		Subject_List.setOnItemClickListener(this);

	}

	/**
     * 
     */
	private void initView() {
		Subject_List = (ListView) findViewById(R.id.Subject_List_id);
	}

	/**
     * 
     */
	private void initDateforNet() {
		listview_Task = new Listview_Task(this);
		// gridview_Task.execute(baseUrl+"index.php/showPic_c/ios_course?kemu_id="+clazNumber);

		listview_Task.execute(clazNumber + "");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget
	 * .AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub

		Log.i(TAG, position + "");
		HashMap<String, Object> hashMap2 = listview_Task.getgridViewDate().get(
				position);
		Intent intent = null;
		int ClassId = (Integer) hashMap2.get("ClassId");
		if(ClassId==1){
			intent = new Intent(this, NumberOneClass.class);
		}else if(ClassId==2){
			intent = new Intent(this, NumberOneClassForDrLe.class);
		}else{
			intent = new Intent(this, ClassNumberCategory.class);
			intent.putExtra("KemuId", clazNumber);
			intent.putExtra("ClassId", ClassId);
		}
		startActivity(intent);
		this.overridePendingTransition(R.anim.slide_left, R.anim.left_out);
	}
}
