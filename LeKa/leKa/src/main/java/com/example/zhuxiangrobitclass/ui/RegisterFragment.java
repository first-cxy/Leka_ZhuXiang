package com.example.zhuxiangrobitclass.ui;


import org.json.JSONException;
import org.json.JSONObject;

import com.example.zhuxiangrobitclass.mApplication;
import com.example.zhuxiangrobitclass.net.HttpClientHelper;
import com.example.zhuxiangrobitclass1.R;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterFragment extends Fragment {
private TextView edittext_username;
private TextView edittext_password;
private TextView edittext_ensurepw;
private TextView edittext_vegcode;
private String username;
private String password;
private String ensurepw;
private String vegcode;
private Button button_register;
private mApplication mApplication;
private static final String TAG = "RegisterFragment";
public static boolean flag = false;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		flag = false;
		View view = inflater.inflate(R.layout.fragment_register, container,false);
		mApplication = (com.example.zhuxiangrobitclass.mApplication) getActivity().getApplication();
		edittext_username = (TextView) view.findViewById(R.id.edittext_username);
		edittext_password = (TextView)view.findViewById(R.id.edittext_password);
		edittext_ensurepw = (TextView)view.findViewById(R.id.edittext_ensurepw);
//		edittext_vegcode = (TextView)view.findViewById(R.id.edittext_vegcode);
		
		button_register = (Button)view.findViewById(R.id.button_register);
		button_register.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				button_register.setClickable(false);
				username = edittext_username.getText().toString();
				password = edittext_password.getText().toString();
				ensurepw = edittext_ensurepw.getText().toString();
				vegcode = edittext_vegcode.getText().toString();
				if(username==null||username.equals("")||password==null||password.equals("")){
					Toast.makeText(getActivity(), "用户名/密码不能为空", Toast.LENGTH_SHORT).show();
					return;
				}
				if(!password.equals(ensurepw)){
					Toast.makeText(getActivity(), "两次输入密码不一致", Toast.LENGTH_SHORT).show();
					return;
				}
				if(vegcode==null||vegcode.equals("")){
					Toast.makeText(getActivity(), "请输入认证码", Toast.LENGTH_SHORT).show();
					return;
				}
				
				new UserManage_Task().execute("index.php/user_manager/register?username="+username+"&password="+password+"&regcode="+vegcode);
				Log.i(TAG, username+" "+password+ " "+ vegcode);
				button_register.setClickable(true);
			}
		});
		// TODO Auto-generated method stub
		return view;
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
					String status = json.getString("status").trim();
					String message = json.getString("mesg");
					String role = json.getString("role");
					String kemus = json.getString("kemu_id");
					if("ok".equalsIgnoreCase(status)){
						Toast.makeText(getActivity(), message+"", Toast.LENGTH_SHORT).show();
						mApplication.setIslogin("T");
						mApplication.setUsername(username);
						mApplication.setRole(role);
						mApplication.setKemus(kemus);
						flag = true;
					}else{
						mApplication.setIslogin("F");
						mApplication.setUsername("");
						Toast.makeText(getActivity(), message+"", Toast.LENGTH_SHORT).show();
					}
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					Log.i(TAG, "JsonParseException", e);
					Toast.makeText(getActivity(), "服务器异常，注册失败！",
							Toast.LENGTH_SHORT).show();
				}
	        }
	    }
	}
	
}
