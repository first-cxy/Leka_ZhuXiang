/**
 * 
 */
package com.example.zhuxiangrobitclass.ui;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhuxiangrobitclass1.R;
import com.example.zhuxiangrobitclass.mApplication;
import com.example.zhuxiangrobitclass.adpter.GridViewAdapter;
import com.example.zhuxiangrobitclass.net.Gridview_Task;
import com.example.zhuxiangrobitclass.net.NetTest_Task;
import com.example.zhuxiangrobitclass.ui.base.BaseActivity;
import com.example.zhuxiangrobitclass.util.Logger;

/**
 * @author fengxb 2014年10月11日
 */
public class FragmentSubjectsCategory extends Fragment implements OnItemClickListener ,OnClickListener{
    public static String Tag;
    public HashMap<String, Object> hashmap;
    public static GridView Subject_gridview_id;
    private Gridview_Task gridview_Task;
    private NetTest_Task netTest_Task;
    private TextView textview_baseURL;
    private EditText editText;
	private TextView textview_tiaozhuan;
	private TextView textview_qianwang;
	private mApplication mApplication;
	private TextView textview_deviceType;
	private TextView textview_logreg;
	public static LinearLayout linearLayout_Subject;
	private static final String TAG = "SubjectsCategory";
	private Handler handler;
	 Handler netHandler = new Handler();
	 
	 
	 
    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
    	View view = inflater.inflate(R.layout.activty_subjectscategory_main, container , false);
    	   mApplication = (com.example.zhuxiangrobitclass.mApplication) getActivity().getApplication();
           Tag = FragmentSubjectsCategory.class.getName();
           
           textview_deviceType = (TextView) view.findViewById(R.id.textview_deviceType);
           textview_deviceType.setOnClickListener(this);
           Subject_gridview_id = (GridView) view.findViewById(R.id.Subject_gridview_ids);
           linearLayout_Subject  = (LinearLayout)view.findViewById(R.id.linearLayout_Subject);
           textview_tiaozhuan = (TextView) view.findViewById(R.id.textview_tiaozhuan);
           textview_tiaozhuan.setOnClickListener(this);
           textview_qianwang = (TextView) view.findViewById(R.id.textview_qianwang);
           textview_qianwang.setOnClickListener(this);
           textview_baseURL =  (TextView) view.findViewById(R.id.textview_baseURL);
           textview_baseURL.setOnClickListener(this);
           textview_logreg = (TextView)view.findViewById(R.id.textview_logreg);
           textview_logreg.setOnClickListener(this);
           
           netHandler.post(runnable);
    	
    	return view;
	}
	
   
	Runnable runnable = new Runnable() {
		@Override
		public void run() {
			Log.i(TAG, mApplication.isServerConnected()+"");
			if(mApplication.isServerConnected() != true){
				netTest_Task = new NetTest_Task(getActivity());
				netTest_Task.execute();
			}else{
				Toast.makeText(getActivity(), "网络连接成功！", Toast.LENGTH_SHORT).show();
				initDateforNet();
				netHandler.removeCallbacks(runnable);
				return;
			}
			netHandler.postDelayed(this, 1000);
		}
	};
	
    @Override
	public void onPause(){
    	 super.onPause();
    	 netHandler.removeCallbacks(runnable);
	}
    
    
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //相当于Fragment的onResume
        	
        	 gridview_Task = new Gridview_Task(getActivity());
             
             HashMap<String, Object> gridviewMap = new HashMap<String, Object>();
     		gridviewMap.put("gridviewId", 100);
     		gridviewMap.put("gridviewName", "公开课");
     		gridview_Task.getgridViewDate().add(gridviewMap);
     		
     		initAdapter();
             
             Subject_gridview_id.setOnItemClickListener(this);
             netHandler.post(runnable);
             
             if(mApplication.getIsOpen().equals("F")){
             	textview_logreg.setVisibility(View.GONE);
             }else if(mApplication.getIsOpen().equals("T")){
             	textview_logreg.setVisibility(View.VISIBLE);
             }
        } else {
            //相当于Fragment的onPause
        }
    }
    
  
    
    /**
     * 
     */
    private void initView() {
    	
        
       
    }

    private void initAdapter() {
		// TODO Auto-generated method stub
		Log.i(TAG, "gridViewAdapter");
		GridViewAdapter gridViewAdapter = new GridViewAdapter(getActivity(),
				gridview_Task.getgridViewDate());
		FragmentSubjectsCategory.Subject_gridview_id.setAdapter(gridViewAdapter);

	}

    /**
     * 
     */
    private void initDateforNet() {
        
        gridview_Task.execute();

    }

//    private void serverNetTest() {
//    	netTest_Task = new NetTest_Task(this);
//    	netTest_Task.execute();
//
//    }
    /*
     * (non-Javadoc)
     * 
     * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View,
     * int, long)
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // TODO Auto-generated method stub
        Logger.e("点击了弟", position + "");
        Intent intent = new Intent(getActivity(), ClassCategory.class);
        Bundle bundle = new Bundle();

        HashMap<String, Object> hashMap2 = gridview_Task.getgridViewDate().get(position);
        int gridviewId = (Integer) hashMap2.get("gridviewId");
        Log.i(TAG, ""+gridviewId);
        bundle.putInt("SubjectForInt", gridviewId);
        intent.putExtras(bundle);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_left, R.anim.left_out);
    }

   

    private int i = 0;
    
    /*
     * (non-Javadoc)
     * 
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.textview_deviceType:
        	Log.i(TAG+"case R.id.textview_deviceType",  R.id.textview_deviceType+"");
			if(mApplication.getDeviceType().equals("ev3")){
				textview_deviceType.setText("设备类型：nxt");
	    		mApplication.setDeviceType("nxt");
	    	}else{
	    		textview_deviceType.setText("设备类型：ev3");
	    		mApplication.setDeviceType("ev3");
	    	}
        	break;
        case R.id.textview_baseURL:
        	netHandler.removeCallbacks(runnable);
        	mApplication.setServerConnected(false);
        	Log.i(TAG+"case R.id.textview_baseURL",  "textview_baseURL");
        	
        	Intent intentip = new Intent(getActivity(),IpDialog.class);
        	startActivity(intentip);
        	
        	
//            editText = new EditText(this);
//            editText.setId(0X112);
//            editText.setHint("格式如：192.168.1.1");
//            new AlertDialog.Builder(this).setTitle("请输入").setIcon(android.R.drawable.ic_dialog_info).setView(editText)
//                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            // TODO Auto-generated method stub
//                            String trim = editText.getText().toString().trim();
//                            Log.i(TAG+"trim", trim+"");
//                            if (trim != null) {
////                                if (mApplication.getBaseUrl() != null) {
////                                    Log.e("BaseURLToString=", mApplication.getBaseUrl());
////                                }
//                            	if(trim.contains("/happyhomework")){
//                            		mApplication.setIsOpen("F");
//                            	} else{
//                            		mApplication.setIsOpen("T");
//                            	}
//                                mApplication.setBaseUrl(trim);
//                                
//                                netHandler.post(runnable);
//                                
//                                dialog.cancel();
//                            }
//                        }
//                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
//
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            // TODO Auto-generated method stub
//                            dialog.cancel();
//                        }
//                    }).show();

            break;
        case R.id.textview_tiaozhuan:
        	if(textview_tiaozhuan.getText().equals("程序演示")){
        		textview_tiaozhuan.setText("场景演示");
        	}else{
        		textview_tiaozhuan.setText("程序演示");
        	}
//        	Log.i(TAG+"case R.id.textview_tiaozhuan",  "textview_tiaozhuan");
//        	Intent intent0 = new Intent(this,LeShuaNumberOneClass.class);
//        	startActivity(intent0);
        	break;
        case R.id.textview_qianwang:
        	Log.i(TAG+"case R.id.textview_tiaozhuan",  "textview_tiaozhuan");
        	Intent intent ;
        	if(textview_tiaozhuan.getText().equals("程序演示")){
        		intent = new Intent(getActivity(),LeShuaNumberOneClass.class);
        		startActivity(intent);
        	}else{
        		intent = new Intent(getActivity(),LeShuaNumberTwoClass.class);
            	startActivity(intent);
        	}
        	break;
        case R.id.textview_logreg:
        	Intent intent1 = new Intent(getActivity(),LoginActivity.class);
        	startActivity(intent1);
        	break;
        default:
            break;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.content.DialogInterface.OnClickListener#onClick(android.content.DialogInterface, int)
     */
}
