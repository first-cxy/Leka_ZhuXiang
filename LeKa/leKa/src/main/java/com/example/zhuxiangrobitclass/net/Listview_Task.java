/**
 * 
 */
package com.example.zhuxiangrobitclass.net;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.zhuxiangrobitclass.mApplication;
import com.example.zhuxiangrobitclass.adpter.GridViewAdapter;
import com.example.zhuxiangrobitclass.adpter.ListViewAdapter;
import com.example.zhuxiangrobitclass.bean.CourseClassModle;
import com.example.zhuxiangrobitclass.ui.ClassCategory;
import com.example.zhuxiangrobitclass.ui.SubjectsCategory;

/**
 * @author fengxb 2014年10月11日
 */
public class Listview_Task extends AsyncTask<String, Void, String> {
	public ArrayList<HashMap<String, Object>> gridViewDate = new ArrayList<HashMap<String, Object>>();
	private String executeGet;
	private Context mContext;
	private String TAG = "Listview_Task";
	private mApplication mApplication;
	private String baseUrl = "";
	private int kemu_id;

	public Listview_Task(Context c) {
		this.mContext = c;
		mApplication = (com.example.zhuxiangrobitclass.mApplication) c
				.getApplicationContext();
		baseUrl = mApplication.getBaseUrl();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#onPreExecute()
	 */
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}

	/*
	 * (non-Javadoc)l
	 * 
	 * @see android.os.AsyncTask#doInBackground(java.lang.Object[])
	 */
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		kemu_id = Integer.parseInt(params[0]);
		if (kemu_id > 99) {

			JSONArray ja = new JSONArray();
			JSONObject jo1 = new JSONObject();
			JSONObject jo2 = new JSONObject();
			JSONObject drLeLesson = new JSONObject();
			try {
				jo1.put("id", 0+"");
				jo1.put("kemu_id", kemu_id+"");
				jo1.put("name", "乐卡入门1");
				ja.put(jo1);
				jo2.put("id", 1+"");
				jo2.put("kemu_id", kemu_id+"");
				jo2.put("name", "乐卡入门2");
				ja.put(jo2);
				drLeLesson.put("id", 2+"");
				drLeLesson.put("kemu_id", kemu_id+"");
				drLeLesson.put("name", "乐卡入门3");
				ja.put(drLeLesson);
				executeGet = ja.toString();
			} catch (JSONException e) {
				Log.i(TAG, "JSONException", e);
			}

		} else {
			HttpClientHelper httpClientHelper = new HttpClientHelper();
			Log.i(TAG, baseUrl + "index.php/showPic_c/ios_course?kemu_id=" + params[0] );
			try {
				executeGet = httpClientHelper.getwithAuth(
						baseUrl + "index.php/showPic_c/ios_course?kemu_id="
								+ params[0], "admin", "liujiwei");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return executeGet;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(String result) {
		if (result != null) {
			Log.i(TAG, result);
			// String stringToJson = StringUtils.StringToJson(result);
			try {
				JSONArray jsonObject = new JSONArray(result);
				int length = jsonObject.length();
				if(mApplication.getRole().equals("D") && length>5){
					length = 5;
				}
				for (int i = 0; i < length; i++) {
					HashMap<String, Object> gridviewMap = new HashMap<String, Object>();
					JSONObject jsonArray = (JSONObject) jsonObject.get(i);
					int gridviewId = Integer.parseInt((String) jsonArray
							.get("id"));
					int kemu_id = Integer.parseInt((String) jsonArray
							.get("kemu_id"));

					String gridviewName = (String) jsonArray.get("name");
					gridviewMap.put("ClassId", gridviewId);
					gridviewMap.put("kemuId", kemu_id);
					gridviewMap.put("gridviewName", gridviewName);
					gridViewDate.add(gridviewMap);

				}
				
				initAdapter();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		super.onPostExecute(result);
	}

	private void initAdapter() {
		// TODO Auto-generated method stub
		ListViewAdapter gridViewAdapter = new ListViewAdapter(mContext,
				gridViewDate);
		ClassCategory.Subject_List.setAdapter(gridViewAdapter);

	}

	public ArrayList<HashMap<String, Object>> getgridViewDate() {
		return gridViewDate;
	}
}
