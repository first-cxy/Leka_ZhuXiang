/**
 * 
 */
package com.example.zhuxiangrobitclass.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.example.zhuxiangrobitclass1.R;
import com.example.zhuxiangrobitclass.mApplication;
import com.example.zhuxiangrobitclass.net.ClazListview_Task;
import com.example.zhuxiangrobitclass.net.HttpClientHelper;
import com.example.zhuxiangrobitclass.ui.base.BaseActivity;
import com.example.zhuxiangrobitclass.util.Logger;

/**
 * @author fengxb 2014Äê10ÔÂ11ÈÕ
 */
public class ClassNumberCategory extends BaseActivity implements OnItemClickListener {
    public final static String TAG = "ClassNumberCategory";
    public static ArrayList<HashMap<String, Object>> mGridViewDate = new ArrayList<HashMap<String, Object>>();
    public HashMap<String, Object> hashmap;
    public static ListView Subject_List;
    private int ClassId;
    private int KemuId;
    private ClazListview_Task classlistview_Task;
    private mApplication mApplication;
    
    
    /*
     * (non-Javadoc)
     * 
     * @see com.example.zhuxiangrobitclass.ui.base.BaseActivity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_classcategory_main);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        KemuId = extras.getInt("KemuId");
        ClassId = extras.getInt("ClassId");
        initView();
       
        initDateforNet();
        Subject_List.setOnItemClickListener(this);
        
    }

    /**
     * 
     */
    private void initView() {
        Subject_List = (ListView) findViewById(R.id.Subject_List_id);
        Button GoShaka_tbn_id = (Button) findViewById(R.id.GoShaka_tbn_id);
        GoShaka_tbn_id.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(ClassNumberCategory.this, LeShuaNumberOneClass.class);
                startActivity(intent);
                ClassNumberCategory.this.overridePendingTransition(R.anim.slide_left, R.anim.left_out);
            }
        });
    }

    /**
     * 
     */
    private void initDateforNet() {
        
        classlistview_Task = new ClazListview_Task(this);
        classlistview_Task.execute(ClassId+"", KemuId+"");
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View,
     * int, long)
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // TODO Auto-generated method stub
    	Log.i(TAG, position+"");
        Intent intent = new Intent(this, ProImagePagerActivity.class);
        HashMap<String, Object> hashMap2 = classlistview_Task.getgridViewDate().get(position);
        mGridViewDate = classlistview_Task.getgridViewDate();
        int size = mGridViewDate.size();
        String KemuId = (String) hashMap2.get("KemuId");
        String URL = (String) hashMap2.get("URL");
       
        intent.putExtra("size", size);
        intent.putExtra("KemuId", KemuId);
        intent.putExtra("ClassId", ClassId+"");
        intent.putExtra("urlForImage", URL);
        intent.putExtra("position", position);
        startActivity(intent);
        
        this.overridePendingTransition(R.anim.slide_left, R.anim.left_out);
    }
}
