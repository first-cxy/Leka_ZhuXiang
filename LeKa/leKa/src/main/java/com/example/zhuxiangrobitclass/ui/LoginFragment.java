package com.example.zhuxiangrobitclass.ui;


import org.json.JSONException;
import org.json.JSONObject;

import com.example.zhuxiangrobitclass.mApplication;
import com.example.zhuxiangrobitclass.net.Gridview_Task;
import com.example.zhuxiangrobitclass.net.HttpClientHelper;
import com.example.zhuxiangrobitclass1.R;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LoginFragment extends Fragment {
private TextView edittext_username;
private TextView edittext_password;
private TextView textview_userlog;
private String username;
private String password;
private Button button_login;
private Button button_logout;
private mApplication mApplication;
private LinearLayout layout_login;
private LinearLayout layout_logout;
private static final String TAG = "LoginFragment";
public static boolean flag = false;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		flag = false;
		View view = inflater.inflate(R.layout.fragment_login, container, false);
		
		mApplication = (com.example.zhuxiangrobitclass.mApplication) getActivity().getApplication();
		final String baseUrl = mApplication.getBaseUrl();
		edittext_username = (TextView) view.findViewById(R.id.edittext_username);
		edittext_password = (TextView)view.findViewById(R.id.edittext_password);
		layout_login = (LinearLayout)view.findViewById(R.id.layout_login);
		button_login = (Button)view.findViewById(R.id.button_login);
		
		layout_logout = (LinearLayout)view.findViewById(R.id.layout_logout);
		textview_userlog = (TextView) view.findViewById(R.id.textview_userlog);
		button_logout = (Button) view.findViewById(R.id.button_logout);
		
		button_login.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				button_login.setClickable(false);
				username = edittext_username.getText().toString();
				password = edittext_password.getText().toString();
				if(username==null||username.equals("")||password==null||password.equals("")){
					Toast.makeText(getActivity(), "用户名/密码不能为空", Toast.LENGTH_SHORT).show();
					return;
				}
				Log.i(TAG, username+" "+password);
				new UserManage_Task().execute("http://homeapp.lekaedu.com/index.php/user_manager/login?username="+username+"&password="+password);
				
			}
		});
		button_logout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				button_logout.setClickable(false);
				mApplication.setIslogin("F");
				mApplication.setUsername("");
				mApplication.setKemus("");
				Toast.makeText(getActivity(), "注销成功！",
						Toast.LENGTH_SHORT).show();
				showview();
			}
		});
		showview();
		
		// TODO Auto-generated method stub
		return view;
	}

	private void showview(){
		button_login.setClickable(true);
		button_logout.setClickable(true);
		layout_logout.setVisibility(View.INVISIBLE);
		layout_login.setVisibility(View.INVISIBLE);
		
		if (mApplication.getIslogin().equals("T")) {
			layout_logout.setVisibility(View.VISIBLE);
			layout_login.setVisibility(View.INVISIBLE);
			textview_userlog.setText("用户： "+mApplication.getUsername()+" 已登录");
		}else {
			layout_logout.setVisibility(View.INVISIBLE);
			layout_login.setVisibility(View.VISIBLE);
		}
	}
	
	private class UserManage_Task extends AsyncTask<String, Void, String> {

	    @Override
	    protected String doInBackground(String... params) {
	        // TODO Auto-generated method stub
	        HttpClientHelper httpClientHelper = new HttpClientHelper();
	        String baseUrl = mApplication.getBaseUrl();
	        Log.i(TAG, baseUrl+params[0]+"");
	        try {
				return httpClientHelper.getwithAuth(baseUrl + params[0],"admin","liujiwei");
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
					String role = json.getString("role");
					String kemus = json.getString("kemu_id");
					Log.i(TAG, message);
					if("ok".equals(status)){
						Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
						mApplication.setIslogin("T");
						mApplication.setUsername(username);
						mApplication.setRole(role);
						mApplication.setKemus(kemus);
						flag = true;
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
					Toast.makeText(getActivity(), "服务器异常，登录失败！",
							Toast.LENGTH_SHORT).show();
					showview();
				}
	        }
	    }
	}
}
