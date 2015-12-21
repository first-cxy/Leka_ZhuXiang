/**
 * 
 */
package com.example.zhuxiangrobitclass.ui.base;

import com.example.zhuxiangrobitclass.mApplication;
import com.example.zhuxiangrobitclass.util.IsHasNetDialog;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;

/**
 * @author fengxb 2014年10月11日
 */
public class BaseActivity extends Activity {

    private Context mContext;
//private mApplication mApplication;
    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	//错误日志管理
    	
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
//        mApplication = (mApplication)getApplication();
        ImageLoader.getInstance().init(mApplication.config); // 初始化
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onStart()
     */
    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onRestart()
     */
    @Override
    protected void onRestart() {
        // TODO Auto-generated method stub
        super.onRestart();
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onPause()
     */
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }

    
    
    @Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	/*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onStop()
     */
    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#addContentView(android.view.View, android.view.ViewGroup.LayoutParams)
     */
    @Override
    public void addContentView(View view, LayoutParams params) {
        // TODO Auto-generated method stub
        super.addContentView(view, params);
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        return super.onKeyDown(keyCode, event);
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#finish()
     */
    @Override
    public void finish() {
        // TODO Auto-generated method stub
        super.finish();
    }

    protected void show(String content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }

    public boolean isConnection() {
        IsHasNetDialog isHasNetDialog = new IsHasNetDialog(this);
        return isHasNetDialog.CheckNetworkState();
    }
}
