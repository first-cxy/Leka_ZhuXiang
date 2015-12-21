package com.example.zhuxiangrobitclass.ui;

import com.example.zhuxiangrobitclass1.R;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;


public class ErrorClass extends Activity {
	private String StorgePath ;
	private String mediapath = "/sdcard/error1.wav";
	private String mediapath2 = "/sdcard/error2.wav";
	private String mediapath3 = "/sdcard/error3.wav";
	private Handler handler;
	private MediaPlayer mMediaPlayer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.error);
		LinearLayout error_layout = (LinearLayout) findViewById(R.id.error_layout);
		error_layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ErrorClass.this.finish();
				mMediaPlayer.stop();
				System.gc();
			}
		});
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		int int1 = extras.getInt("errornum");
//		StorgePath = Environment.getExternalStorageDirectory().getPath();
		StorgePath = Environment.getDataDirectory().getPath();
		mediapath = StorgePath + "/error1.wav" ;
		mediapath2 = StorgePath + "/error2.wav" ;
		mediapath3 = StorgePath + "/error3.wav" ;
		try {
			mMediaPlayer = new MediaPlayer();
			if (int1 == 1) {
				mMediaPlayer.setDataSource(mediapath);
			} else if (int1 == 2) {
				mMediaPlayer.setDataSource(mediapath2);
			} else {
				mMediaPlayer.setDataSource(mediapath3);
			}
			mMediaPlayer.prepare();
			mMediaPlayer.start();
		} catch (Exception e) {
			Log.e("Media", "error: " + e.getMessage(), e);
		}
	}
}
