package com.example.zhuxiangrobitclass.ui;

import com.example.zhuxiangrobitclass1.R;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;


public class WinClass extends Activity {
	private String mediapath = "/sdcard/own.wav";
	private Handler handler;
	private MediaPlayer mMediaPlayer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.own_view);
		LinearLayout error_layout = (LinearLayout) findViewById(R.id.own_Layout);
		error_layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				WinClass.this.finish();
				mMediaPlayer.stop();
				System.gc();
			}
		});
//		mediapath = Environment.getExternalStorageDirectory().getPath()
//				+ "/own.wav" ;
		mediapath = Environment.getDataDirectory().getPath()
				+ "/own.wav" ;
		try {
			mMediaPlayer = new MediaPlayer();
			mMediaPlayer.setDataSource(mediapath);
			mMediaPlayer.prepare();
			mMediaPlayer.start();
			Message msg = new Message();
			msg.what = 1;
			handler.sendEmptyMessageAtTime(msg.what, 3000);
		} catch (Exception e) {
			Log.e("Media", "error: " + e.getMessage(), e);
		}

	}
}
