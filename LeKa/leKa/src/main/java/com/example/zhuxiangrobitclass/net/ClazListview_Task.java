/**
 * 
 */
package com.example.zhuxiangrobitclass.net;

import java.text.Format;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.drawable;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.zhuxiangrobitclass.mApplication;
import com.example.zhuxiangrobitclass.adpter.ClassListViewAdapter;
import com.example.zhuxiangrobitclass.ui.ClassNumberCategory;

/**
 * @author fengxb 2014Äê10ÔÂ11ÈÕ
 */
public class ClazListview_Task extends AsyncTask<String, Void, String> {
	public ArrayList<HashMap<String, Object>> gridViewDate = new ArrayList<HashMap<String, Object>>();
	private String executeGet;
	private Context mContext;
	private mApplication mApplication;
	private String baseUrl = "";
	private String TAG = "ClazListview_Task";
	private String KemuId;

	public ClazListview_Task(Context c) {
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
		String ClazId = params[0];
		KemuId = params[1];
		if (Integer.parseInt(KemuId) > 99) {
			JSONArray ja = new JSONArray();
			for (int i = 1; i <= 51; i++) {

				// int id = mContext.getResources().getIdentifier("p" + i,
				// "drawable", mContext.getPackageName());
				JSONObject jo = new JSONObject();
				try {
					jo.put("id", i + "");
					jo.put("url", "p" + i);
					jo.put("name", String.format("%03d", i) + ".PNG");
					ja.put(jo);

				} catch (JSONException e) {
					Log.i(TAG, "JSONException", e);
				}
			}
			executeGet = ja.toString();

		} else {
			HttpClientHelper httpClientHelper = new HttpClientHelper();
			// executeGet = httpClientHelper.executeGet(params[0]);
			Log.i(TAG, baseUrl + "index.php/showPic_c/ios_picture?course_id="
					+ params[0]);
			try {
				executeGet = httpClientHelper.getwithAuth(baseUrl
						+ "index.php/showPic_c/ios_picture?course_id="
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
		Log.i("ClazListview_Task", result);
		if (result != null) {
			// String stringToJson = StringUtils.StringToJson(result);
			try {
				JSONArray jsonObject = new JSONArray(result);
				for (int i = 0; i < jsonObject.length(); i++) {
					HashMap<String, Object> gridviewMap = new HashMap<String, Object>();
					JSONObject jsonArray = (JSONObject) jsonObject.get(i);
					int gridviewId = Integer.parseInt((String) jsonArray
							.get("id"));
					String url = (String) jsonArray.get("url");

					if (jsonArray.has("act_form")) {
						String act_form = (String) jsonArray
								.get("act_form");
						if (act_form == null||"".equals(act_form)) {
					
						}else {
							gridviewMap.put("act_form", act_form);
						}
					}

					if (jsonArray.has("act_name")) {
						String act_name = (String) jsonArray
								.get("act_name");
						if (act_name == null||"".equals(act_name)) {
					
						}else {
							gridviewMap.put("act_name", act_name);
						}
					}
					
					String gridviewName = (String) jsonArray.get("name");
					gridviewMap.put("ClassId", gridviewId);
					gridviewMap.put("KemuId", KemuId);
					gridviewMap.put("URL", url);
					gridviewMap.put("gridviewName", gridviewName);
					gridViewDate.add(gridviewMap);
				}
				initAdapter();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Log.i("ClazListview_Task", "JSONException", e);
			}

		}

		super.onPostExecute(result);
	}

	private void initAdapter() {
		// TODO Auto-generated method stub
		ClassListViewAdapter gridViewAdapter = new ClassListViewAdapter(
				mContext, gridViewDate);
		ClassNumberCategory.Subject_List.setAdapter(gridViewAdapter);

	}

	public ArrayList<HashMap<String, Object>> getgridViewDate() {
		return gridViewDate;
	}
}
