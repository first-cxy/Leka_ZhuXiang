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
public class Action_Task extends AsyncTask<String, Void, String> {
	private Context mContext;
	private String flag = "";
    private String TAG = "Action_Task";
    
    public Action_Task(Context c) {
        this.mContext = c;
    }
    @Override
    protected String doInBackground(String... params) {
        // TODO Auto-generated method stub
        HttpClientHelper httpClientHelper = new HttpClientHelper();
        String actionUrl = "http://leka.maxtain.com/card_code/code";
//        flag = httpClientHelper.executeActionGet(actionUrl + "?act="+params[0]);
        try {
			flag = httpClientHelper.getwithAuth(actionUrl + "?act="+params[0],"admin","liujiwei");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Log.i(TAG, actionUrl + "?act="+params[0]+flag);
        return flag;
    }
}

