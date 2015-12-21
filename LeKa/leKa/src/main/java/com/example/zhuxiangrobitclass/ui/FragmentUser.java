package com.example.zhuxiangrobitclass.ui;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract.CommonDataKinds.Event;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhuxiangrobitclass.mApplication;
import com.example.zhuxiangrobitclass.adpter.UserAdapter;
import com.example.zhuxiangrobitclass.factory.LogFragmentFactory;
import com.example.zhuxiangrobitclass.net.HttpClientHelper;
import com.example.zhuxiangrobitclass1.R;


public class FragmentUser extends Fragment {
	private ViewPager viewPager;
	private List<View> viewList = new ArrayList<View>();
	private TextView login_edittext_username;
	private TextView login_edittext_password;
	private TextView login_textview_userlog;
	private String login_username;
	private String login_password;
	private Button login_button_login;
	private Button login_button_logout;
	private mApplication mApplication;
	private LinearLayout layout_login;
	private LinearLayout layout_logout;
	
	private TextView edittext_username;
	private TextView edittext_password;
	private TextView edittext_ensurepw;
	private String username;
	private String password;
	private String ensurepw;
	private Button button_register;

	private Button login_button_turn_register;
	private Button registr_button_turn_login;
	
	private static final String TAG = "FragmentUser";
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_user, container , false);
		
		viewPager = (ViewPager) view.findViewById(R.id.viewPager);
		
		View view_login = inflater.inflate(R.layout.fragment_login, null);
		View view_register = inflater.inflate(R.layout.fragment_register, null);
		
		viewList.add(view_login);
		viewList.add(view_register);
		
		viewPager.setAdapter(new UserAdapter(getActivity(), viewList));

		mApplication = (com.example.zhuxiangrobitclass.mApplication) getActivity().getApplication();
		final String baseUrl = mApplication.getBaseUrl();
		login_edittext_username = (TextView) view_login.findViewById(R.id.edittext_username);
		login_edittext_password = (TextView)view_login.findViewById(R.id.edittext_password);
		layout_login = (LinearLayout)view_login.findViewById(R.id.layout_login);
		login_button_login = (Button)view_login.findViewById(R.id.button_login);
		
		layout_logout = (LinearLayout)view_login.findViewById(R.id.layout_logout);
		login_textview_userlog = (TextView) view_login.findViewById(R.id.textview_userlog);
		login_button_logout = (Button) view_login.findViewById(R.id.button_logout);

		login_button_turn_register = (Button) view_login.findViewById(R.id.button_turn_register);
		registr_button_turn_login = (Button) view_register.findViewById(R.id.button_turn_login);

		login_button_turn_register.setOnClickListener(new View.OnClickListener(){
			@Override
			public  void onClick(View v){
				viewPager.setCurrentItem(1,true);
			}
		});

		registr_button_turn_login.setOnClickListener(new View.OnClickListener(){
			@Override
			public  void onClick(View v){
				viewPager.setCurrentItem(0,true);
			}
		});

		login_button_login.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				login_username = login_edittext_username.getText().toString();
				login_password = login_edittext_password.getText().toString();
				if(login_username==null||login_username.equals("")||login_password==null||login_password.equals("")){
					Toast.makeText(getActivity(), "�û���/���벻��Ϊ��", Toast.LENGTH_SHORT).show();
					return;
				}
				Log.i(TAG, login_username+" "+login_password);
				new Login_Task().execute("http://homeapp.lekaedu.com/index.php/user_manager/login?username="+login_username+"&password="+login_password);
				
			}
		});
		login_button_logout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				login_button_logout.setClickable(false);
				mApplication.setIslogin("F");
				mApplication.setUsername("");
				Toast.makeText(getActivity(), "ע��ɹ���",
						Toast.LENGTH_SHORT).show();
				showview();
			}
		});
		showview();
		
		edittext_username = (TextView) view_register.findViewById(R.id.edittext_username);
		edittext_password = (TextView) view_register.findViewById(R.id.edittext_password);
		edittext_ensurepw = (TextView) view_register.findViewById(R.id.edittext_ensurepw);
		
		button_register = (Button)view_register.findViewById(R.id.button_register);
		button_register.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				username = edittext_username.getText().toString();
				password = edittext_password.getText().toString();
				ensurepw = edittext_ensurepw.getText().toString();
				if(username==null||username.equals("")||password==null||password.equals("")){
					Toast.makeText(getActivity(), "�û���/���벻��Ϊ��", Toast.LENGTH_SHORT).show();
					return;
				}
				if(!password.equals(ensurepw)){
					Toast.makeText(getActivity(), "�����������벻һ��", Toast.LENGTH_SHORT).show();
					return;
				}
				
				new Register_Task().execute("http://homeapp.lekaedu.com/index.php/user_manager/register?username="+username+"&password="+password);
				Log.i(TAG, username+" "+password+ " ");
			}
		});
		
		return view;
	}

	private void showview(){
		login_button_login.setClickable(true);
		login_button_logout.setClickable(true);
		layout_logout.setVisibility(View.INVISIBLE);
		layout_login.setVisibility(View.INVISIBLE);
		
		if (mApplication.getIslogin().equals("T")) {
			layout_logout.setVisibility(View.VISIBLE);
			layout_login.setVisibility(View.INVISIBLE);
			login_textview_userlog.setText("�û��� "+mApplication.getUsername()+" �ѵ�¼");
		}else {
			layout_logout.setVisibility(View.INVISIBLE);
			layout_login.setVisibility(View.VISIBLE);
		}
	}
	
	private class Login_Task extends AsyncTask<String, Void, String> {

	    @Override
	    protected String doInBackground(String... params) {
	        // TODO Auto-generated method stub
//	        String baseUrl = mApplication.getBaseUrl();
//	        Log.i(TAG, baseUrl+params[0]+"");
	        try {
				return HttpClientHelper.downloadUrl(params[0]);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
	    }
	    
	    @Override
	    protected void onPostExecute(String result) {
	    	Log.i(TAG, result+"");
	        if (result != null) {
				try {
					JSONObject json = new JSONObject(result);
					String status = json.getString("status");
					String message = json.getString("mesg");
					Log.i(TAG, message);
					if("ok".equals(status)){
						Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
						mApplication.setIslogin("T");
						mApplication.setUsername(login_username);
					}else{
						mApplication.setIslogin("F");
						mApplication.setUsername("");
						Toast.makeText(getActivity(), message+"",
								Toast.LENGTH_SHORT).show();
					}
					showview();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					Log.i(TAG, "JsonParseException", e);
					Toast.makeText(getActivity(), "�������쳣����¼ʧ�ܣ�",
							Toast.LENGTH_SHORT).show();
					showview();
				}
	        }
	    }
	}
	
	private class Register_Task extends AsyncTask<String, Void, String> {

	    @Override
	    protected String doInBackground(String... params) {
	        // TODO Auto-generated method stub
//	        String baseUrl = mApplication.getBaseUrl();
//	        Log.i(TAG, baseUrl+params[0]+"");
	        try {
				return HttpClientHelper.downloadUrl( params[0]);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
	     
	    }
	    
	    @Override
	    protected void onPostExecute(String result) {
	    	Log.i(TAG, result+"");
	        if (result != null) {
				try {
					JSONObject json = new JSONObject(result);
					String status = json.getString("status").trim();
					String message = json.getString("mesg");
					if("ok".equalsIgnoreCase(status)){
						Toast.makeText(getActivity(), message+"", Toast.LENGTH_SHORT).show();
						mApplication.setIslogin("T");
						mApplication.setUsername(username);
					}else{
						mApplication.setIslogin("F");
						mApplication.setUsername("");
						Toast.makeText(getActivity(), message+"", Toast.LENGTH_SHORT).show();
					}
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					Log.i(TAG, "JsonParseException", e);
					Toast.makeText(getActivity(), "�������쳣��ע��ʧ�ܣ�",
							Toast.LENGTH_SHORT).show();
				}
	        }
	    }
	}
	
	
}
