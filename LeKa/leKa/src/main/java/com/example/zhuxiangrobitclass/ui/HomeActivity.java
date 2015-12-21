package com.example.zhuxiangrobitclass.ui;

import com.example.zhuxiangrobitclass.factory.HomeFragmentFactory;
import com.example.zhuxiangrobitclass1.R;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;

public class HomeActivity extends Activity implements OnTouchListener{
	private FragmentManager manager;
	private Button btn_model;
	private Button btn_code;
	private Button btn_share;
	private Button btn_user;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		btn_model = (Button)findViewById(R.id.btn_model);
		btn_model.setOnTouchListener(this);
		
		btn_code = (Button)findViewById(R.id.btn_code);
		btn_code.setOnTouchListener(this);
		
		btn_share = (Button)findViewById(R.id.btn_share);
		btn_share.setOnTouchListener(this);
		
		btn_user = (Button)findViewById(R.id.btn_user);
		btn_user.setOnTouchListener(this);
		
		manager = getFragmentManager();
		change(R.id.btn_model);
		
		
		
		
		
	}

	
	private void change(int id){
		FragmentTransaction transaction = manager.beginTransaction();
		Fragment fragment = (Fragment) HomeFragmentFactory.getInstanceByIndex(id);
		
		transaction.replace(R.id.content, fragment);
		transaction.commitAllowingStateLoss();
	}


	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if(event.getAction()==MotionEvent.ACTION_DOWN){
			switch (v.getId()){
			case R.id.btn_model:
				change(R.id.btn_model);
				break;
			case R.id.btn_code:
				change(R.id.btn_code);
				break;
			case R.id.btn_share:
				change(R.id.btn_share);
				break;
			case R.id.btn_user:
				change(R.id.btn_user);
				break;
			default:
				break;
			}
		}
		
		return true;
	}
	
}
