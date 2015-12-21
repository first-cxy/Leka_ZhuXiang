/**
 * 
 */
package com.example.zhuxiangrobitclass.net;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.example.zhuxiangrobitclass.mApplication;
import com.example.zhuxiangrobitclass.adpter.GridViewAdapter;
import com.example.zhuxiangrobitclass.ui.SubjectsCategory;

/**
 * @author fengxb 2014年10月11日
 */
public class Gridview_Task extends AsyncTask<String, Void, String> {
	public ArrayList<HashMap<String, Object>> gridViewDate = new ArrayList<HashMap<String, Object>>();
	private String executeGet;
	private Context mContext;
	private mApplication mApplication;
	private String TAG = "Gridview_Task";
	private String[] kemus;

	public Gridview_Task(Context c) {
		Log.i(TAG, "...............");
		this.mContext = c;
		mApplication = (com.example.zhuxiangrobitclass.mApplication) c
				.getApplicationContext();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#onPreExecute()
	 */
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
//		HashMap<String, Object> gridviewMap = new HashMap<String, Object>();
//		gridviewMap.put("gridviewId", 100);
//		gridviewMap.put("gridviewName", "公开课");
//		gridViewDate.add(gridviewMap);
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
		HttpClientHelper httpClientHelper = new HttpClientHelper();
		String baseUrl = mApplication.getBaseUrl();
		// if (baseUrl == null) {
		// baseUrl = "http://192.168.199.105/happyhomework/";
		// }else{
		// baseUrl="http://"+baseUrl+"/happyhomework/";
		// }
		Log.e(TAG, baseUrl + "index.php/showPic_c/ios_kemu");
		try {
			executeGet = httpClientHelper.getwithAuth(baseUrl
					+ "index.php/showPic_c/ios_kemu", "admin", "liujiwei");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		Log.i(TAG, "mApplication.getKemus():" + mApplication.getKemus());
		if (mApplication.getKemus().contains(",")&&!mApplication.getKemus().equals("")) {
			kemus = mApplication.getKemus().split(",");
		} else {
			kemus = null;
		}
		if (result != null) {
			mApplication.setServerConnected(true);
			Log.i(TAG, result);
			// String stringToJson = StringUtils.StringToJson(result);
			if (mApplication.getIsOpen().equals("T")) {

				if (mApplication.getIslogin().equals("T")) {
					try {
						JSONArray jsonObject = new JSONArray(result);
						if (mApplication.getRole().equals("D") && kemus != null) {
							for (int i = 0; i < jsonObject.length(); i++) {
								HashMap<String, Object> gridviewMap = new HashMap<String, Object>();
								JSONObject jsonArray = (JSONObject) jsonObject
										.get(i);
								int gridviewId = Integer
										.parseInt((String) jsonArray.get("id"));
								Log.i(TAG, "isInArray(gridviewId, kemus)"
										+ isInArray(gridviewId + "", kemus));
								if (isInArray(gridviewId + "", kemus)) {
									String gridviewUrl = (String) jsonArray
											.get("url");
									String gridviewName = (String) jsonArray
											.get("name");
									gridviewMap.put("gridviewId", gridviewId);
									gridviewMap.put("gridviewUrl", gridviewUrl);
									gridviewMap.put("gridviewName",
											gridviewName);
									gridViewDate.add(gridviewMap);
								}
							}
						} else if (mApplication.getRole().equals("A")) {
							for (int i = 0; i < jsonObject.length(); i++) {
								HashMap<String, Object> gridviewMap = new HashMap<String, Object>();
								JSONObject jsonArray = (JSONObject) jsonObject
										.get(i);
								int gridviewId = Integer
										.parseInt((String) jsonArray.get("id"));
								String gridviewUrl = (String) jsonArray
										.get("url");
								String gridviewName = (String) jsonArray
										.get("name");
								gridviewMap.put("gridviewId", gridviewId);
								gridviewMap.put("gridviewUrl", gridviewUrl);
								gridviewMap.put("gridviewName", gridviewName);
								gridViewDate.add(gridviewMap);
							}
						} else if (mApplication.getRole().equals("C")) {
							HashMap<String, Object> gridviewMap = new HashMap<String, Object>();
							for (int i = 0; i < jsonObject.length(); i++) {
								JSONObject jsonArray = (JSONObject) jsonObject
										.get(i);
								int gridviewId = Integer
										.parseInt((String) jsonArray.get("id"));
								if (gridviewId == 6) {
									String gridviewUrl = (String) jsonArray
											.get("url");
									String gridviewName = (String) jsonArray
											.get("name");
									gridviewMap.put("gridviewId", gridviewId);
									gridviewMap.put("gridviewUrl", gridviewUrl);
									gridviewMap.put("gridviewName",
											gridviewName);
								}
							}
							gridViewDate.add(gridviewMap);
						}

					} catch (JSONException e) {
						Log.i(TAG, "JSONException", e);
					} catch (Exception e) {
						Log.i(TAG, "Exception", e);
					}
				}
			} else if (mApplication.getIsOpen().equals("F")) {
				Log.i(TAG, "isopen"+mApplication.getIsOpen());
				try {
					JSONArray jsonObject = new JSONArray(result);
					for (int i = 0; i < jsonObject.length(); i++) {
					HashMap<String, Object> gridviewMap = new HashMap<String, Object>();
					JSONObject jsonArray = (JSONObject) jsonObject
							.get(i);
					int gridviewId = Integer
							.parseInt((String) jsonArray.get("id"));
					String gridviewUrl = (String) jsonArray
							.get("url");
					String gridviewName = (String) jsonArray
							.get("name");
					gridviewMap.put("gridviewId", gridviewId);
					gridviewMap.put("gridviewUrl", gridviewUrl);
					gridviewMap.put("gridviewName", gridviewName);
					gridViewDate.add(gridviewMap);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					Log.i(TAG, "JSONException", e);
				}
			}
		} else {
			mApplication.setServerConnected(false);
			Log.i(TAG, "服务器连接失败！");
		}
		initAdapter();
		super.onPostExecute(result);
	}

	private boolean isInArray(String e, String[] a) {
		for (int i = 0; i < a.length; i++) {
			if (e.equals(a[i])) {
				return true;
			}
		}
		return false;
	}

	Handler handler = new Handler();
	Runnable runnable = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub

		}
	};

	private void initAdapter() {
		// TODO Auto-generated method stub
		Log.i(TAG, "gridViewAdapter");
		GridViewAdapter gridViewAdapter = new GridViewAdapter(mContext,
				gridViewDate);
		SubjectsCategory.Subject_gridview_id.setAdapter(gridViewAdapter);

	}

	public ArrayList<HashMap<String, Object>> getgridViewDate() {
		return gridViewDate;
	}
}
