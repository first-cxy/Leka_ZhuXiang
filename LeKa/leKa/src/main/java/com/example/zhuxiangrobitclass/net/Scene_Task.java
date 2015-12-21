/**
 * 
 */
package com.example.zhuxiangrobitclass.net;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.zhuxiangrobitclass.mApplication;
import com.example.zhuxiangrobitclass.adpter.GridViewAdapter;
import com.example.zhuxiangrobitclass.ui.SubjectsCategory;

/**
 * @author fengxb 2014Äê10ÔÂ11ÈÕ
 */
public class Scene_Task extends AsyncTask<String, Void, String> {
	private String executeGet;
	private Context mContext;
	private mApplication mApplication;
	private String TAG = "Scene_Task";
	private Card_Task card_Task;
	String baseUrl = "";

	public Scene_Task(Context c) {
		this.mContext = c;
		mApplication = (com.example.zhuxiangrobitclass.mApplication) c
				.getApplicationContext();
		baseUrl = mApplication.getBaseUrl();
	}

	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub

		try {
			Log.i(TAG, baseUrl + "felony/cmd.php?act=card&card=" + params[0]);
			executeGet = HttpClientHelper.getwithAuth(baseUrl
					+ "felony/cmd.php?act=card&card=" + params[0], "admin",
					"liujiwei");
		} catch (Exception e) {
			Log.i(TAG, "Exception", e);
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
		Log.i(TAG, result+"");
		if (result.contains("ok")) {
			Log.i(TAG, "card_task running");
			card_Task = new Card_Task(mContext);
			card_Task.execute("cancel","card");
		}
	}
}
