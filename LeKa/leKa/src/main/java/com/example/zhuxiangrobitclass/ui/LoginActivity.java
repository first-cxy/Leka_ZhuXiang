package com.example.zhuxiangrobitclass.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract.CommonDataKinds.Event;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zhuxiangrobitclass.factory.LogFragmentFactory;
import com.example.zhuxiangrobitclass1.R;


public class LoginActivity extends Activity implements OnTouchListener{
	private FragmentManager manager;
	private TextView textview_login;
	private TextView textview_register;
	private Handler handler = new Handler();
	private LinearLayout layout_root;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		RegisterFragment.flag = false;
		manager = getFragmentManager();
		
//		layout_root = (LinearLayout) findViewById(R.id.layout_root);
//		layout_root.setLayoutParams(new LayoutParams(500,LayoutParams.MATCH_PARENT));
		textview_login = (TextView) findViewById(R.id.textview_login);
		textview_login.setOnTouchListener(this);
		textview_register = (TextView)findViewById(R.id.textview_register);
		textview_register.setOnTouchListener(this);
		change(R.id.textview_login);
		handler.post(runnable);
	}

	private void change(int id){
		clear();
		((TextView)findViewById(id)).setTextColor(Color.BLACK);
		FragmentTransaction transaction = manager.beginTransaction();

		Fragment fragment = LogFragmentFactory
				.getInstanceByIndex(id);
		
		transaction.replace(R.id.login_content, fragment);
		transaction.commitAllowingStateLoss();
	}

	Runnable runnable = new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			if(RegisterFragment.flag||LoginFragment.flag){
				finish();
			}
			handler.post(runnable);
		}
	};
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		handler.removeCallbacks(runnable);
		super.onDestroy();
	}

	private void clear(){
		textview_login.setTextColor(Color.DKGRAY);
		textview_register.setTextColor(Color.DKGRAY);
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		
		if(event.getAction()==MotionEvent.ACTION_DOWN){
			switch(v.getId()){
			case R.id.textview_login:
				change(R.id.textview_login);
				break;
			case R.id.textview_register:
				change(R.id.textview_register);
				break;
			}
		}
		
		return true;
	}
	
}
