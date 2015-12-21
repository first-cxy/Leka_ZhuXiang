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
public class UserManage_Task extends AsyncTask<String, Void, String> {
    private String executeGet;
    private Context mContext;
    private mApplication mApplication;
    private String TAG = "UserManage_Task";
    
    public UserManage_Task(Context c) {
        this.mContext = c;
        mApplication = (com.example.zhuxiangrobitclass.mApplication) c.getApplicationContext();
    }

    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        // TODO Auto-generated method stub
        HttpClientHelper httpClientHelper = new HttpClientHelper();
        String baseUrl = mApplication.getBaseUrl();
        Log.i(TAG, baseUrl);
        try {
			executeGet = httpClientHelper.getwithAuth(baseUrl + params[0],"admin","liujiwei");
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
    	Log.i(TAG, result+"");
        if (result != null) {
        	mApplication.setServerConnected(true);
        }else{
        	mApplication.setServerConnected(false);
        }
    }
}
