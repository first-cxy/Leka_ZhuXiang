package com.example.zhuxiangrobitclass.ui;

import com.example.zhuxiangrobitclass1.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class SceneActivity extends Activity implements OnClickListener {

	private Button button_back;
	private ImageView iv_scene01;
	private ImageView iv_scene02;
	private ImageView iv_scene03;
	private ImageView iv_scene04;
	private String scene = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scene);

		button_back = (Button) findViewById(R.id.button_back);
		button_back.setOnClickListener(this);
		iv_scene01 = (ImageView) findViewById(R.id.iv_scene01);
		iv_scene01.setOnClickListener(this);
		iv_scene02 = (ImageView) findViewById(R.id.iv_scene02);
		iv_scene02.setOnClickListener(this);
		iv_scene03 = (ImageView) findViewById(R.id.iv_scene03);
		iv_scene03.setOnClickListener(this);
		iv_scene04 = (ImageView) findViewById(R.id.iv_scene04);
		iv_scene04.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.button_back:
			finish();
			break;
		case R.id.iv_scene01:
			scene = "1";
			close();
			break;
		case R.id.iv_scene02:
			scene = "2";
			close();
			break;
		case R.id.iv_scene03:
			scene = "3";
			close();
			break;
		case R.id.iv_scene04:
			scene = "4";
			close();
			break;
		}
	}

	private void close() {
		Intent intent = getIntent();
		Bundle bundle = new Bundle();
		bundle.putString("scene", scene);
		intent.putExtras(bundle);
		SceneActivity.this.setResult(RESULT_OK, intent);
		this.finish();
	}

}
