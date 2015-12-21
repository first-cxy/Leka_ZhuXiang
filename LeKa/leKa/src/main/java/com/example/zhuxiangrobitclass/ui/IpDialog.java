package com.example.zhuxiangrobitclass.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.example.zhuxiangrobitclass.mApplication;
import com.example.zhuxiangrobitclass1.R;

public class IpDialog extends Activity implements OnClickListener{

	private TextView textview_ip;
	private Button dialog_button_ok;
	private Button dialog_button_cancel;
	private final static String TAG = "IpDialog";
	private mApplication mApplication;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ipdialog);
		
		mApplication = (com.example.zhuxiangrobitclass.mApplication) getApplication();
		
		textview_ip = (TextView)findViewById(R.id.textview_ip);
		dialog_button_ok = (Button)findViewById(R.id.dialog_button_ok);
		dialog_button_ok.setOnClickListener(this);
		dialog_button_cancel = (Button) findViewById(R.id.dialog_button_cancel);
		dialog_button_cancel.setOnClickListener(this);
		
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.dialog_button_ok:
			 String trim = textview_ip.getText().toString().trim();
             Log.i(TAG+"trim", trim+"");
             if (trim != null) {
//                 if (mApplication.getBaseUrl() != null) {
//                     Log.e("BaseURLToString=", mApplication.getBaseUrl());
//                 }
             	if(trim.contains("/happyhomework")){
             		mApplication.setIsOpen("F");
             	} else{
             		mApplication.setIsOpen("T");
             	}
                 mApplication.setBaseUrl(trim);
             }
             finish();
			break;
		case R.id.dialog_button_cancel:
			finish();
			break;
		}
	}

	
	
	
}
