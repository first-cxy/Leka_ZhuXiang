package com.example.zhuxiangrobitclass.ui;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhuxiangrobitclass.mApplication;
import com.example.zhuxiangrobitclass1.R;

public class QuestionDialog extends Activity implements OnClickListener{

	private TextView tv_question;
	private EditText tv_answer;
	private Button dialog_button_ok;
	private Button dialog_button_cancel;
	private final static String TAG = "QuestionDialog";
	private mApplication mApplication;
	private String question = "";
	private String answer = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_questiondialog);
		
		Intent intent = getIntent();
		String queans = intent.getStringExtra("queans");
		try {
			JSONObject jo = new JSONObject(queans);
			question = jo.get("question").toString();
			answer = jo.get("answer").toString();
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.i(TAG, "JSONException", e);
		}
		
		
		mApplication = (com.example.zhuxiangrobitclass.mApplication) getApplication();
		
		tv_question = (TextView)findViewById(R.id.tv_question);
		tv_question.setText("请问："+question);
		tv_answer = (EditText)findViewById(R.id.tv_answer);
		
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
			 String trim = tv_answer.getText().toString().trim();
             Log.i(TAG+"trim", trim+"");
             if (trim != null) {
//                 if (mApplication.getBaseUrl() != null) {
//                     Log.e("BaseURLToString=", mApplication.getBaseUrl());
//                 }
             	if(trim.contains(answer)){
             		Toast.makeText(getBaseContext(), "回答正确，真棒！！！", Toast.LENGTH_SHORT).show();
             		 finish();
             	} else{
             		Toast.makeText(getBaseContext(), "回答错误，再试一次吧！", Toast.LENGTH_SHORT).show();
             	}
             }
			break;
		case R.id.dialog_button_cancel:
			finish();
			break;
		}
	}

	
	
	
}
