package com.example.zhuxiangrobitclass.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.example.zhuxiangrobitclass.mApplication;
import com.example.zhuxiangrobitclass.net.Action_Task;
import com.example.zhuxiangrobitclass.net.HttpClientHelper;
import com.example.zhuxiangrobitclass1.R;

public class ControlActivity extends Activity implements OnClickListener {

	private Action_Task action_Task;
	private Button button_back;
	private Button button_up;
	private Button button_left;
	private Button button_stop;
	private Button button_right;
	private Button button_down;

	private String act;
	private mApplication mApplication;
	private String baseUrl;
	private static String actionStop = "M1000";
	private static String actionUp = "M1001";
	private static String actionDown = "M1002";
	private static String actionLeft = "M1003";
	private static String actionRight = "M1004";

	private static final String TAG = "ControlActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_control);

		initView();
		
	}

	private void initView() {
		button_back = (Button) findViewById(R.id.button_back);
		button_back.setOnClickListener(this);
		button_up = (Button) findViewById(R.id.button_up);
		button_up.setOnClickListener(this);
		button_left = (Button) findViewById(R.id.button_left);
		button_left.setOnClickListener(this);
		button_stop = (Button) findViewById(R.id.button_stop);
		button_stop.setOnClickListener(this);
		button_right = (Button) findViewById(R.id.button_right);
		button_right.setOnClickListener(this);
		button_down = (Button) findViewById(R.id.button_down);
		button_down.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.button_back:
			this.finish();
			break;
		case R.id.button_up:
			act = actionUp;
			actionExecute(act);
			break;
		case R.id.button_left:
			act = actionLeft;
			actionExecute(act);
			break;
		case R.id.button_stop:
			act = actionStop;
			actionExecute(act);
			break;
		case R.id.button_right:
			act = actionRight;
			actionExecute(act);
			break;
		case R.id.button_down:
			act = actionDown;
			actionExecute(act);
			break;
		default:
			break;
		}
	}

	private void actionExecute(String act) {
		
//		HttpClientHelper httpClientHelper = new HttpClientHelper();
//        String actionUrl = "http://leka.maxtain.com/card_code/code";
////        Log.i(TAG, actionUrl + "?act="+act);
//        httpClientHelper.executeGet(actionUrl + "?act="+act);
//        Log.i(TAG, actionUrl + "?act="+act);
		action_Task = new Action_Task(this);
		action_Task.execute(act);
	}
}
