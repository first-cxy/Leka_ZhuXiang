package com.example.zhuxiangrobitclass.ui;

import com.example.zhuxiangrobitclass1.R;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.MediaController;
import android.widget.MediaController.MediaPlayerControl;
import android.widget.VideoView;

public class videoplayer extends Activity {
	private VideoView mVideoPlay;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.video);
		Intent intent = getIntent();
		String url = intent.getStringExtra("url");
		Uri uri = Uri.parse(url);

		mVideoPlay = (VideoView) findViewById(R.id.movieview);
		mVideoPlay.setVideoURI(uri);
		// mVideoPlay.setVideoPath(videopath+url);
		mVideoPlay.setMediaController(new MediaController(this));
		mVideoPlay.requestFocus();
		mVideoPlay
				.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
					@Override
					public void onCompletion(MediaPlayer mp) {
						videoplayer.this.finish();
					}
				});

		mVideoPlay.setOnErrorListener(new MediaPlayer.OnErrorListener() {
			@Override
			public boolean onError(MediaPlayer mp, int what, int extra) {
				mVideoPlay.stopPlayback();
				return false;
			}
		});
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		this.finish();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(mVideoPlay!=null){
			mVideoPlay.stopPlayback();
		}
	}
	
}
