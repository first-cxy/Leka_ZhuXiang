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
 * @author fengxb 2014��10��11��
 */
public class Rtc_Task extends AsyncTask<String, Void, String> {
    private String executeGet;
    private Context mContext;
    private mApplication mApplication;
    private String TAG = "Rtc_Task";
    String baseUrl = "";
  
    
    public Rtc_Task(Context c) {
        this.mContext = c;
        mApplication = (com.example.zhuxiangrobitclass.mApplication) c.getApplicationContext();
        baseUrl = mApplication.getBaseUrl();
    }


    @Override
    protected String doInBackground(String... params) {
        // TODO Auto-generated method stub
        int i ;
        for(i = 0;i<params.length;i++){
        try {
        	Log.i(TAG, baseUrl + "felony/cmd.php?act=rtc&rtc=" + params[i]);
			executeGet = HttpClientHelper.getwithAuth(baseUrl + "felony/cmd.php?act=rtc&rtc="+params[i],"admin","liujiwei");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        }
        return executeGet;
    }

    @Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		Log.i(TAG, result+"");
		if (result != null) {
			mApplication.setMode(result.trim());
			
		} else {
			mApplication.setMode("cancel");
		}

	}
   
}
