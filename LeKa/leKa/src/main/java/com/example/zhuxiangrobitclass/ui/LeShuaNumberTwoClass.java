package com.example.zhuxiangrobitclass.ui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.os.Vibrator;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhuxiangrobitclass.mApplication;
import com.example.zhuxiangrobitclass.adpter.ShuaKaListviewAdapter;
import com.example.zhuxiangrobitclass.net.Card_Task;
import com.example.zhuxiangrobitclass.net.HttpClientHelper;
import com.example.zhuxiangrobitclass.net.Rtc_Task;
import com.example.zhuxiangrobitclass.net.Scene_Task;
import com.example.zhuxiangrobitclass.net.Status_Task;
import com.example.zhuxiangrobitclass.ui.base.BaseActivity;
import com.example.zhuxiangrobitclass.util.ScreenObserver;
import com.example.zhuxiangrobitclass.util.Utils;
import com.example.zhuxiangrobitclass.util.ScreenObserver.ScreenStateListener;
import com.example.zhuxiangrobitclass.zxing.BTCommunicator;
import com.example.zhuxiangrobitclass.zxing.BTConnectable;
import com.example.zhuxiangrobitclass.zxing.DeviceListActivity;
import com.example.zhuxiangrobitclass.zxing.activity.CaptureActivity;
import com.example.zhuxiangrobitclass1.R;

//import com.example.zhuxiangrobitclass.zxing.LCPMessage;

public class LeShuaNumberTwoClass extends BaseActivity implements
		OnTouchListener, OnClickListener, BTConnectable {
	public ViewPager mPager;
	private static int c_id = 0;
	private ArrayList<View> list;
	private View[] vs;
	private LayoutInflater inflater;
	public ArrayList<Integer> arrayList = new ArrayList<Integer>();
	private static final int REQUEST_CONNECT_DEVICE = 1000;
	private static final int REQUEST_ENABLE_BT = 2000;
	private static final int REQUEST_ENABLE_QR = 3000;
	private static final int REQUEST_SCENE = 4000;
	private String scene = "1";
	double dx;
	double dy;
	int index;
	public static int currindex = 0;
	public ArrayList<String> arr = new ArrayList<String>();
	private String name;
	private String subSequence;
	public byte[] infoMessages;
	private mApplication mApplication;
	private static final String TAG = "LeShuaNumberOneClass";
	private Scene_Task scene_Task;
	private Map<String, Integer> sceneMap = new HashMap<String, Integer>();

	private String baseUrl = "";
	private Card_Task card_Task;
	private Rtc_Task rtc_Task;
	private ScreenObserver screenObserver;  
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		sceneMap.put("1", R.drawable.fengshan1);
		sceneMap.put("2", R.drawable.youlechang4);
		sceneMap.put("3", R.drawable.shenshou2);
		sceneMap.put("4", R.drawable.jiguan3);
		// act=cmd&cmd=M1000

		mApplication = (com.example.zhuxiangrobitclass.mApplication) getApplication();
		baseUrl = mApplication.getBaseUrl();
		// baseUrl = "leka.5zixi.cn";
		SimpleAdapter adapter = new SimpleAdapter(this, all,
				R.layout.layout_item, new String[] { "image" },
				new int[] { R.id.imageView });
		inflater = getLayoutInflater();
		InitTextView();
		initViewSize();
		dis = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dis);
		// show("屏幕分辨率为:" + dis.widthPixels + " * " + dis.heightPixels);
		Display mDisplay = getWindowManager().getDefaultDisplay();
		dx = mDisplay.getWidth() / 100.0;
		dy = mDisplay.getHeight() / 100.0;
		index = 0;
		handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				name = (String) msg.obj;
				subSequence = name.subSequence(0, 3).toString();
				if (mApplication.getDeviceType().equals("ev3")) {
					Log.i("handler", "_____startev3startProgram__" + name);
					ev3startProgram(1, subSequence, name + ".rbf");
				} else {
					Log.i("handler", "startProgram" + name + ".rxe");
					// nxtstartProgram(1, subSequence, name + ".rxe");
					startProgram(name + ".rxe");
				}

			}
		};
		 screenObserver = new ScreenObserver(this);  
	        screenObserver.requestScreenStateUpdate(new ScreenStateListener() {  
	            @Override  
	            public void onScreenOn() {  
	                doSomethingOnScreenOn();  
	            }  
	  
	            @Override  
	            public void onScreenOff() {  
	                doSomethingOnScreenOff();  
	            }  
	        });  
	        
		Intent intent = new Intent(LeShuaNumberTwoClass.this,
				SceneActivity.class);
		startActivityForResult(intent, REQUEST_SCENE);

	}

	private void doSomethingOnScreenOn() {  
        Log.i(TAG, "Screen is on");  
    }  
  
    private void doSomethingOnScreenOff() {  
        Log.i(TAG, "Screen is off"); 
        destroyBTCommunicator();
    }  
	private void initViewSize() {

		vs = new View[list.size()];
		ViewGroup main = (ViewGroup) inflater.inflate(
				R.layout.classnumberone_main, null);

		ViewGroup group = (ViewGroup) main.findViewById(R.id.viewGroup);
		rlLevel3 = (RelativeLayout) main.findViewById(R.id.rl_level3);
		rlLevel2 = (RelativeLayout) main.findViewById(R.id.rl_level2);
		rlLevel32 = (RelativeLayout) main.findViewById(R.id.rl_level1);
		viewPager = (ViewPager) main.findViewById(R.id.vPager);
		for (int i = 0; i < list.size(); i++) {
			View v = new View(LeShuaNumberTwoClass.this);
			v.setLayoutParams(new LayoutParams(12, 12));
			vs[i] = v;
			// if (i == 0) {
			//
			// imageViews[i].setBackgroundResource(R.drawable.guide_dot_white);
			// } else {
			// imageViews[i].setBackgroundResource(R.drawable.guide_dot_black);
			group.addView(v);
		}
		setContentView(main);
		viewPager.setAdapter(new MyAdapter());
		viewPager.setCurrentItem(300);
		viewPager.setCurrentItem(0);
	}

	private void InitTextView() {
		createBTCommunicator();
		LayoutInflater inflater = getLayoutInflater();
		list = new ArrayList<View>();
		inflate21 = inflater.inflate(R.layout.layout212, null);
		initlayout21();
		list.add(inflate21);
	}

	@Override
	protected void onResume() {
		adapter2.notifyDataSetChanged();
		listView_show.setAdapter(adapter2);
		handler5.post(runnable5);
		handler6.post(runnable6);
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		handler5.removeCallbacks(runnable5);
		handler6.removeCallbacks(runnable6);
		destroyBTCommunicator();
		screenObserver.stopScreenStateUpdate();  
	}

	private ImageView iv_scene;
	private Button btn_left;
	private Button btn_right;
	private Button btn_rtc;
	private boolean open = false;
	private TextView tv_state;

	private void initlayout21() {
		inflate21.setOnTouchListener(this);
		Button btn0 = (Button) inflate21.findViewById(R.id.btn0);
		Button btn1 = (Button) inflate21.findViewById(R.id.btn1);
		Button btn2 = (Button) inflate21.findViewById(R.id.btn2);
		Button btn3 = (Button) inflate21.findViewById(R.id.btn3);

		tv_state = (TextView) inflate21.findViewById(R.id.tv_state);

		handler5.post(runnable5);
		handler6.post(runnable6);

		btn_left = (Button) inflate21.findViewById(R.id.btn_left);
		btn_right = (Button) inflate21.findViewById(R.id.btn_right);
		btn_rtc = (Button) inflate21.findViewById(R.id.btn_rtc);

		iv_scene = (ImageView) inflate21.findViewById(R.id.iv_scene);

		listView_show = (ListView) inflate21.findViewById(R.id.listView_show);
		adapter2 = new ShuaKaListviewAdapter(LeShuaNumberTwoClass.this, arr);
		// check();
		handler4.post(runnable);
		btn0.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(LeShuaNumberTwoClass.this,
						SceneActivity.class);
				startActivityForResult(intent, REQUEST_SCENE);

			}
		});

		btn1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// ev3startProgram(0,"Project","A0101.rpf");
				// song();
				Intent openCameraIntent = new Intent(LeShuaNumberTwoClass.this,
						CaptureActivity.class);
				startActivityForResult(openCameraIntent, REQUEST_ENABLE_QR);
			}
		});
		btn2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (arr.size() > 0) {
					currindex = arr.size() - 1;
					arr.remove(currindex);

					adapter2.notifyDataSetChanged();
				}
			}
		});
		btn3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// demo2();
				String json = "[";

				for (i = 0; i < arr.size(); i++) {
					JSONObject json1;
					try {
						json1 = new JSONObject(arr.get(i))
								.getJSONObject("leka");
						String name = json1.getString("name");
						json += "{\"file\":\"" + name
								+ ".py\",\"auto\":\"True\"}";
						if (i + 1 < arr.size()) {
							json += ",";
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				json += "]";

				scene_Task = new Scene_Task(getBaseContext());
				try {
					scene_Task.execute(URLEncoder.encode(json, "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});

		btn_left.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					rtc_Task = new Rtc_Task(getBaseContext());
					rtc_Task.execute("M" + scene + "00" + "1");
					break;
				case MotionEvent.ACTION_UP:
					rtc_Task = new Rtc_Task(getBaseContext());
					rtc_Task.execute("M" + scene + "00" + "0");
					break;

				}
				return true;
			}
		});

		btn_right.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					rtc_Task = new Rtc_Task(getBaseContext());
					rtc_Task.execute("M" + scene + "00" + "2");
					break;
				case MotionEvent.ACTION_UP:
					rtc_Task = new Rtc_Task(getBaseContext());
					rtc_Task.execute("M" + scene + "00" + "0");
					break;

				}
				return true;
			}
		});

		btn_rtc.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				btn_rtc.setClickable(false);
				// if
				// (mApplication.getMode().equals("cancel")||mApplication.getMode().equals("card"))
				// {
				if (mApplication.getStatus().equals("rtc_running")) {
					card_Task = new Card_Task(getBaseContext());// 开启
					card_Task.execute("cancel", "rtc");
					handler4.post(runnable);
				} else {
					card_Task = new Card_Task(getBaseContext());
					card_Task.execute("cancel");
					handler4.post(runnable);
				}

			}

		});
	}

	Handler handler6 = new Handler();
	Runnable runnable6 = new Runnable() {
		public void run() {
			new Status_Task(getBaseContext()).execute();
			handler6.postDelayed(runnable6, 1000);
		}
	};

	Handler handler5 = new Handler();
	Runnable runnable5 = new Runnable() {
		public void run() {
			Log.i("runnable5", mApplication.getStatus() + "");
			tv_state.setText("当前状态：" + mApplication.getStatus());
			handler5.postDelayed(runnable5, 500);
		}
	};

	Handler handler4 = new Handler();
	Runnable runnable = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			// if (Card_Task.done) {
			// btn_rtc.setClickable(true);
			// if (mApplication.getMode().equals("cancel")) {
			if (!mApplication.getStatus().equals("rtc_running")) {
				btn_left.setClickable(false);
				btn_right.setClickable(false);
				btn_rtc.setText("开启实时模式");
			} else {
				btn_left.setClickable(true);
				btn_right.setClickable(true);
				btn_rtc.setText("关闭实时模式");
			}
			// } else {
			handler4.post(runnable);
			// }
		}
	};

	public void check() {
		if (open) {
			btn_left.setClickable(true);
			btn_right.setClickable(true);
			btn_rtc.setText("关闭实时模式");
		} else {
			btn_left.setClickable(false);
			btn_right.setClickable(false);
			btn_rtc.setText("开启实时模式");

		}
	}

	public static int i = 0;

	private void RunLekaImage() throws Exception {
		if (arr.size() <= 0) {

			return;
		}
		if (i >= arr.size()) {
			i = 0;
		}
		currentTimeMillis = System.currentTimeMillis();
		JSONObject json = new JSONObject(arr.get(i).toString())
				.getJSONObject("leka");
		String name = json.getString("name");
		subSequence = name.subSequence(0, 3).toString();
		Message msg = new Message();
		msg.obj = name;
		handler.sendMessageAtTime(msg, 1000);
		Log.i("RunLekaImage", "运行图片第" + i + "张" + " name:" + name);

		i++;

		// while (i<arr.size()) {
		// JSONObject json = new JSONObject(arr.get(i).toString())
		// .getJSONObject("leka");
		// String name = json.getString("name");
		// subSequence = name.subSequence(0, 3).toString();
		// ev3startProgram(1, subSequence, name + ".rbf");
		// i++;
		// }

	}

	public class ontouch implements OnTouchListener {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				x = event.getX();
				y = event.getY();
				if (event.getRawX() < 1450 && event.getRawX() < 0) {
					for (int i = 0; i < arrayList.size(); i++) {
						if (arrayList.get(i) == v.getId()) {
							arrayList.remove(i);
							// show("删除了" + v.getId());
							// show("删除了" + v.getId());
						}
					}
				}
			case MotionEvent.ACTION_MOVE:
				mx = (int) (event.getRawX() - x);
				my = (int) (event.getRawY() - 50 - y);

				v.layout(mx, my, mx + v.getWidth(), my + v.getHeight());
				if (event.getRawX() > 1450 && event.getRawX() < 1700
						&& event.getRawY() > 250 && event.getRawY() < 800) {
					arrayList.add(v.getId());
					// show("添加了" + v.getId());
				}
				break;
			}
			return true;
		}

	}

	public int onclickNumber = 0;
	private ArrayList<Map<String, Integer>> all;
	private View inflate21;

	public class MyOnClickListener implements View.OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		@Override
		public void onClick(View v) {
			mPager.setCurrentItem(index);
		}
	};

	class MyAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return 1;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public int getItemPosition(Object object) {
			return super.getItemPosition(object);
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			// ((ViewPager) arg0).removeView(list.get(arg1));
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			try {
				// 设置循环
				// ((ViewPager) arg0).addView(list.get(arg1%list.size()),0);
				((ViewPager) arg0).addView(list.get(arg1));
			} catch (Exception e) {
			}
			// return list.get(arg1%list.size());
			// if (arg1 > list.size() - 1) {
			// NumberOneClass.this.finish();
			// Util.showToast(NumberOneClass.this, "亲 第一个课时结束了");
			// overridePendingTransition(R.anim.slide_left, R.anim.hold);
			// return null;
			// } else {

			return list.get(0);
			// }

		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {

		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {

		}

		@Override
		public void finishUpdate(View arg0) {

		}
	}

	class MyListener implements OnPageChangeListener {

		// 当滑动状态改变时调用
		@Override
		public void onPageScrollStateChanged(int arg0) {
			// arg0=arg0%list.size();
			Log.e("onPageScrollStateChanged", "onPageScrollStateChanged");

		}

		// 当当前页面被滑动时调用
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			Log.e("onPageScrolled", "onPageScrolled");
			// if (arg0 == 23) {
			// nxt1();
			// }
			// if (arg0 == 24) {
			// nxt3();
			// }
		}

		// 当新的页面被选中时调用
		@Override
		public void onPageSelected(int arg0) {
			Log.e("onPageSelected", "onPageSelected");
			if (arg0 > 2) {
				arg0 = arg0 % list.size();
			}
			c_id = arg0;
			for (int i = 0; i < vs.length; i++) {
				vs[arg0].setBackgroundResource(R.drawable.page);
				if (arg0 != i) {
					vs[i].setBackgroundResource(R.drawable.page_now);
				}
			}
			// if (arg0 == 23) {
			// nxt1();
			// handler.postDelayed(runnable, 500);
			// }
			// if (arg0 == 24) {
			// handler.postDelayed(runnable, 500);
			// nxt3();
			// }

			Log.e("-------------", "当前是第" + c_id + "页");
		}

	}

	private float x, y;
	private int mx, my;
	private int screenWidth;
	private int screenHeight;
	private int lastX, lastY;

	@Override
	public boolean onTouch(View v, MotionEvent event) {

		int ea = event.getAction();
		Log.i("TAG", "Touch:" + ea);

		// Toast.makeText(DraftTest.this, "位置："+x+","+y,
		// Toast.LENGTH_SHORT).show();

		switch (ea) {
		case MotionEvent.ACTION_DOWN:
			lastX = (int) event.getRawX();
			lastY = (int) event.getRawY();
			if (event.getRawX() > 1050 && event.getRawX() < 1700
					&& event.getRawY() > 250 && event.getRawY() < 1000) {
				for (int i = 0; i < arrayList.size(); i++) {
					if (arrayList.get(i) == v.getId()) {
						arrayList.remove(i);
						show("删除了" + v.getId());
						show("删除了" + v.getId());
					}
				}
			}

			// getX()是表示Widget相对于自身左上角的x坐标,而getRawX()是表示相对于屏幕左上角的x坐标值(注意:这个屏幕左上角是手机屏幕左上角,不管activity是否有titleBar或是否全屏幕),getY(),getRawY()一样的道理
			// if (index==3)
			// if ((event.getX() > sx(20))&&(event.getX() < sx(70))) {
			// if (isConnected())
			// {
			// show("小U，你会唱小星星嘛？");
			// song();
			// }
			// else
			// {
			// show("小伙伴不在");
			// }
			// }

			if (event.getX() < sx(15)) {
				if (event.getY() < sy(15)) {
					// 搜索蓝牙
					selectNXT();
				} else {
					if (index > 1) {
						index--;
						setBitmap();
					}
				}
			}
			if (event.getX() > sx(85)) {
				if (event.getY() < sy(15)) {
					// 扫描蓝牙
					Intent openCameraIntent = new Intent(this,
							CaptureActivity.class);
					startActivityForResult(openCameraIntent, REQUEST_ENABLE_QR);
				} else {
					if (index < 30) {
						index++;
						setBitmap();
					}
				}
			}
			break;
		/**
		 * layout(l,t,r,b) l Left position, relative to parent t Top position,
		 * relative to parent r Right position, relative to parent b Bottom
		 * position, relative to parent
		 * */
		case MotionEvent.ACTION_MOVE:
			int dx = (int) event.getRawX() - lastX;
			int dy = (int) event.getRawY() - lastY;

			int left = v.getLeft() + dx;
			int top = v.getTop() + dy;
			int right = v.getRight() + dx;
			int bottom = v.getBottom() + dy;

			if (left < 0) {
				left = 0;
				right = left + v.getWidth();
			}

			if (right > screenWidth) {
				right = screenWidth;
				left = right - v.getWidth();
			}

			if (top < 0) {
				top = 0;
				bottom = top + v.getHeight();
			}

			if (bottom > screenHeight) {
				bottom = screenHeight;
				top = bottom - v.getHeight();
			}

			v.layout(left, top, right, bottom);

			Log.i("", "position：" + left + ", " + top + ", " + right + ", "
					+ bottom);

			lastX = (int) event.getRawX();
			lastY = (int) event.getRawY();
			if (event.getRawX() > 1050 && event.getRawX() < 1700
					&& event.getRawY() > 250 && event.getRawY() < 1000) {
				arrayList.add(v.getId());
				show("添加了" + v.getId());
			}
			break;
		case MotionEvent.ACTION_UP:
			break;
		}
		return false;
	}

	private RelativeLayout rlLevel3;
	private RelativeLayout rlLevel2;
	private RelativeLayout rlLevel32;
	private boolean isDisplayMenu = false; // 菜单的显示状态, 默认为不显示
	private boolean isDisplayLevel2 = false; // 二级菜单的显示状态, 默认为不显示
	private boolean isDisplayLevel3 = false; // 三级菜单的显示状态, 默认为不显示

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ib_home:

			if (isDisplayLevel2) {

				long startOffset = 0;
				if (isDisplayLevel3) {
					Utils.startOutRotateAnimation(rlLevel3, startOffset);
					isDisplayLevel3 = !isDisplayLevel3;
					startOffset = 200;
				}

				// 隐藏二级菜单
				Utils.startOutRotateAnimation(rlLevel2, startOffset);
			} else {
				rlLevel2.setVisibility(View.VISIBLE);
				// 显示二级菜单
				Utils.startInRotateAnimation(rlLevel2, 0);
			}
			isDisplayLevel2 = !isDisplayLevel2;
			break;
		case R.id.ib_menu:
			if (isDisplayLevel3) {
				// 隐藏三级菜单
				Utils.startOutRotateAnimation(rlLevel3, 0);
			} else {
				rlLevel3.setVisibility(View.VISIBLE);
				// 显示三级菜单
				Utils.startInRotateAnimation(rlLevel3, 0);
			}
			isDisplayLevel3 = !isDisplayLevel3;
			break;
		case R.id.PageViewNumberChange:
			findViewById(R.id.showSearchPageView).setVisibility(View.VISIBLE);
			break;
		case R.id.pageNumberSearchBtn:
//			EditText pageNumberSearch = (EditText) findViewById(R.id.pageNumberSearch);
//			String number = pageNumberSearch.getText().toString().trim();
//			int Inumber = Integer.parseInt(number);
//			if (Inumber > 0 && Inumber < list.size()) {
//				int currentItem = viewPager.getCurrentItem();
//				if (currentItem < Inumber - 1) {
//					viewPager.setCurrentItem(Inumber - 1);
//				}
//				findViewById(R.id.showSearchPageView).setVisibility(View.GONE);
//			}
			break;
		case R.id.Connection_Robit:
			selectNXT();
			if (index > 1) {
				index--;
				setBitmap();
			}
			break;
		case R.id.shaomiaoLanya:
			// 扫描蓝牙
			Intent openCameraIntent = new Intent(this, CaptureActivity.class);
			startActivityForResult(openCameraIntent, REQUEST_ENABLE_QR);
			if (index < 30) {
				index++;
				setBitmap();
			}
			break;
		default:
			break;
		}

	}

	@Override
	public boolean isPairing() {
		return false;
	}

	/**
	 * Gets the current connection status.
	 * 
	 * @return the current connection status to the robot.
	 */
	public boolean isConnected() {
		return connected;
	}

	private boolean pairing;

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case REQUEST_SCENE:
			if (resultCode == RESULT_OK) {
				Bundle bundle = data.getExtras();
				scene = bundle.getString("scene");
			} else {
				scene = "1";
			}
			arr.clear();
			iv_scene.setImageDrawable(getResources().getDrawable(
					sceneMap.get(scene)));
			break;
		case REQUEST_CONNECT_DEVICE:

			// When DeviceListActivity returns with a device to connect
			if (resultCode == Activity.RESULT_OK) {
				// Get the device MAC address and start a new bt communicator
				// thread
				String address = data.getExtras().getString(
						DeviceListActivity.EXTRA_DEVICE_ADDRESS);
				pairing = data.getExtras().getBoolean(
						DeviceListActivity.PAIRING);
				startBTCommunicator(address);
			} else if (resultCode == Activity.RESULT_CANCELED) {
				destroyBTCommunicator();
			}

			break;

		case REQUEST_ENABLE_BT:

			// When the request to enable Bluetooth returns
			switch (resultCode) {
			case Activity.RESULT_OK:
				// btOnByUs = true;
				// selectNXT();
				break;
			case Activity.RESULT_CANCELED:
				// showToast("bt_needs_to_be_enabled", Toast.LENGTH_SHORT);
				finish();
				break;
			default:
				// showToast("problem_at_connecting", Toast.LENGTH_SHORT);
				finish();
				break;
			}

			break;
		case REQUEST_ENABLE_QR:
			if (resultCode == RESULT_OK) {
				Bundle bundle = data.getExtras();
				scanResult = bundle.getString("result");
				// ((TextView)
				// findViewById(R.id.fullscreen_content)).setText(scanResult);
				// showToast(scanResult, Toast.LENGTH_SHORT);
				if ("NXT-01:16:53:0F:64:73".equals(scanResult)) {
					startBTCommunicator("00:16:53:10:50:d9");
				} else if ("NXT-0F:64:73".equals(scanResult)) {
					startBTCommunicator("00:16:53:0e:e5:23");
				} else {
					try {
						JSONObject json = new JSONObject(scanResult)
								.getJSONObject("leka");
						String lvname = json.getString("name");
						Log.i(TAG, lvname + "");
						if (lvname.equals("CTRL")) {
							// 跳转到
							Intent intent = new Intent(getBaseContext(),
									ControlActivity.class);
							startActivity(intent);
						} else {
							Log.i(TAG, scanResult + "");
							arr.add(scanResult);
						}
					} catch (JSONException e) {
						Log.i(TAG, "JSONExceptionCTRL", e);
					}
				}
			}
			break;
		}
	}

	private ProgressDialog connectingProgressDialog;
	private BTCommunicator myBTCommunicator = null;
	private boolean connected = false;
	private Handler btcHandler;
	private Toast reusableToast;

	private void startBTCommunicator(String mac_address) {
		connected = false;
		connectingProgressDialog = ProgressDialog.show(this, "", "和小伙伴交流中",
				true);
		if (myBTCommunicator != null) {
			try {
				myBTCommunicator.destroyNXTconnection();
			} catch (IOException e) {
			}
		}
		createBTCommunicator();
		myBTCommunicator.setMACAddress(mac_address);
		myBTCommunicator.start();
	}

	/**
	 * Creates a new object for communication to the NXT robot via bluetooth and
	 * fetches the corresponding handler.
	 */
	private void createBTCommunicator() {
		// interestingly BT adapter needs to be obtained by the UI thread - so
		// we pass it in in the constructor
		myBTCommunicator = new BTCommunicator(this, myHandler,
				BluetoothAdapter.getDefaultAdapter(), getResources());
		btcHandler = myBTCommunicator.getHandler();
	}

	/**
	 * Receive messages from the BTCommunicator
	 */
	final Handler myHandler = new Handler() {

		@Override
		public void handleMessage(Message myMessage) {
			Log.i("switch", ":" + myMessage.getData().getInt("message"));
			switch (myMessage.getData().getInt("message")) {
			case BTCommunicator.DISPLAY_TOAST:
				Log.i("BTCommunicator.DISPLAY_TOAST", ":"
						+ myMessage.getData().getInt("message"));
				show(myMessage.getData().getString("toastText"));
				break;
			case BTCommunicator.STATE_CONNECTED:
				Log.i("BTCommunicator.STATE_CONNECTED", ":"
						+ myMessage.getData().getInt("message"));
				connectingProgressDialog.dismiss();
				reusableToast.makeText(getApplicationContext(), "你可以和小伙伴互动啦",
						Toast.LENGTH_LONG).show();
				connected = true;
				break;
			case BTCommunicator.PROGRAM_NAME:
				Log.i("BTCommunicator.PROGRAM_NAME", ":"
						+ myMessage.getData().getInt("message"));
				if (myBTCommunicator != null) {
					byte[] returnMessage = myBTCommunicator.getReturnMessage();
					startRXEprogram(returnMessage[2]);
				}

				break;
			case BTCommunicator.GET_VALUE:
				Log.i("BTCommunicator.GET_VALUE", ":"
						+ myMessage.getData().getInt("message"));
				byte[] sensorMessage = myBTCommunicator.getReturnMessage();
				int sensorValue = 0;
				if (sensorMessage[10] > 0)
					sensorValue = sensorMessage[10] + sensorMessage[11] * 256;
				else
					sensorValue = sensorMessage[10] + sensorMessage[11] * 256
							+ 256;
				// nxtValue(sensorValue / 10);
				break;
			case BTCommunicator.EV3_INFOPROGRAM:
				Log.i("BTCommunicator.EV3_INFOPROGRAM", ":"
						+ myMessage.getData().getInt("message"));
				infoMessages = myBTCommunicator.getReturnMessage();
				if (infoMessages[3] == 0) {
					handler2.removeCallbacks(runnable2);
					// if (mApplication.getDeviceType().equals("ev3")) {
					do3_ev3();
					// }else {
					// do3_nxt();
					// }
					String name;
					if (i < arr.size()) {
						try {
							JSONObject json = new JSONObject(arr.get(i)
									.toString()).getJSONObject("leka");
							name = json.getString("name");
							subSequence = name.subSequence(0, 3).toString();
							Message msg = new Message();
							msg.obj = name;
							handler.sendMessageAtTime(msg, 2000);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						i++;
					}
				} else if (infoMessages[3] == 1) {

				}

				break;

			case BTCommunicator.MOTOR_STATE:
				Log.i("BTCommunicator.MOTOR_STATE", ":"
						+ myMessage.getData().getInt("message"));
				if (myBTCommunicator != null) {
					byte[] motorMessage = myBTCommunicator.getReturnMessage();
					int position = byteToInt(motorMessage[21])
							+ (byteToInt(motorMessage[22]) << 8)
							+ (byteToInt(motorMessage[23]) << 16)
							+ (byteToInt(motorMessage[24]) << 24);
					show("Current position: " + position);
				}

				break;
			/*
			 * case BTCommunicator.BATTERY_STATE: if(myBTCommunicator != null){
			 * 
			 * byte[] batteryMessage = myBTCommunicator.getReturnMessage();
			 * mBatteryLevel = byteToInt(batteryMessage[4]); mBatteryLevel =
			 * (mBatteryLevel<<8) + byteToInt(batteryMessage[3]); float Btmp =
			 * ((int)mBatteryLevel/100); mTextBattery.setText("电池电压："+
			 * String.valueOf(Btmp/10) +" V");
			 * 
			 * } break;
			 */
			case BTCommunicator.STATE_CONNECTERROR_PAIRING:
				connectingProgressDialog.dismiss();
				reusableToast.makeText(getApplicationContext(),
						"STATE_CONNECTERROR_PAIRING", Toast.LENGTH_LONG).show();
				destroyBTCommunicator();
				break;

			case BTCommunicator.STATE_CONNECTERROR:
				connectingProgressDialog.dismiss();
				reusableToast.makeText(getApplicationContext(), "小伙伴不理你",
						Toast.LENGTH_LONG).show();
				break;
			case BTCommunicator.STATE_RECEIVEERROR:
			case BTCommunicator.STATE_SENDERROR:
				reusableToast.makeText(getApplicationContext(), "小伙伴离开了",
						Toast.LENGTH_LONG).show();
				destroyBTCommunicator();

				break;

			case BTCommunicator.VIBRATE_PHONE:
				if (myBTCommunicator != null) {
					byte[] vibrateMessage = myBTCommunicator.getReturnMessage();
					Vibrator myVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
					myVibrator.vibrate(vibrateMessage[2] * 10);
				}

				break;
			default:
				break;
			}
		}
	};
	private DisplayMetrics dis;

	public void startRXEprogram(byte status) {
		if (programToStart == null) {
			if (status == 0x00) {

			} else {
				handler3.removeCallbacks(runnable3);
			}
			return;

		} else {
			if (status == 0x00) {
				sendBTCmessage(BTCommunicator.NO_DELAY,
						BTCommunicator.STOP_PROGRAM, 0, 0);
				sendBTCmessage(200, BTCommunicator.START_PROGRAM,
						programToStart);
			} else {

				sendBTCmessage(BTCommunicator.NO_DELAY,
						BTCommunicator.START_PROGRAM, programToStart);
			}
		}
	}

	private int byteToInt(byte byteValue) {
		int intValue = (byteValue & (byte) 0x7f);

		if ((byteValue & (byte) 0x80) != 0)
			intValue |= 0x80;
		return intValue;
	}

	/**
	 * Sends a message for disconnecting to the communcation thread.
	 */
	public void destroyBTCommunicator() {
		Log.i(TAG, "destroyBTCommunicator");
		if (myBTCommunicator != null) {
			sendBTCmessage(BTCommunicator.NO_DELAY, BTCommunicator.DISCONNECT,
					0, 0);
			myBTCommunicator = null;
		}

		connected = false;
	}

	void sendBTCmessage(int delay, int message, int value1, int value2) {
		Bundle myBundle = new Bundle();
		myBundle.putInt("message", message);
		myBundle.putInt("value1", value1);
		myBundle.putInt("value2", value2);
		Message myMessage = myHandler.obtainMessage();
		myMessage.setData(myBundle);

		if (delay == 0)
			btcHandler.sendMessage(myMessage);

		else
			btcHandler.sendMessageDelayed(myMessage, delay);
	}

	String programToStart;
	private View inflate22;

	// for .rxe programs: get program name, eventually stop this and start
	public void startProgram(String name) {
		// the new one delayed
		// is handled in startRXEprogram()
		if (name.endsWith(".rxe")) {
			programToStart = name;
			sendBTCmessage(BTCommunicator.NO_DELAY,
					BTCommunicator.GET_PROGRAM_NAME, 0, 0);
			return;
		}

		// for .nxj programs: stop bluetooth communication after starting the
		// program
		if (name.endsWith(".nxj")) {
			sendBTCmessage(BTCommunicator.NO_DELAY,
					BTCommunicator.START_PROGRAM, name);
			destroyBTCommunicator();
			return;
		}

		// for all other programs: just start the program
		sendBTCmessage(BTCommunicator.NO_DELAY, BTCommunicator.START_PROGRAM,
				name);
	}

	/**
	 * Sends the message via the BTCommuncator to the robot.
	 * 
	 * @param delay
	 *            time to wait before sending the message.
	 * @param message
	 *            the message type (as defined in BTCommucator)
	 * @param value1
	 *            first parameter
	 * @param value2
	 *            second parameter
	 */

	public void sendBTCmessage(int delay, int message, String name) {
		Bundle myBundle = new Bundle();
		myBundle.putInt("message", message);
		myBundle.putString("name", name);
		Message myMessage = myHandler.obtainMessage();
		myMessage.setData(myBundle);

		if (delay == 0)
			btcHandler.sendMessage(myMessage);
		else
			btcHandler.sendMessageDelayed(myMessage, delay);
	}

	void act3() {
		// Toast.makeText(NumberOneClass.this, "前进", Toast.LENGTH_SHORT).show();
		startProgram("demo003.rxe");
	}

	void do1() {
		// Toast.makeText(NumberOneClass.this, "前进", Toast.LENGTH_SHORT).show();
		sendBTCmessage(BTCommunicator.NO_DELAY, BTCommunicator.MOTOR_B, 75, 0);
		sendBTCmessage(10, BTCommunicator.MOTOR_C, 75, 0);
	}

	void do2() {
		// Toast.makeText(NumberOneClass.this, "左转", Toast.LENGTH_SHORT).show();
		sendBTCmessage(BTCommunicator.NO_DELAY, BTCommunicator.MOTOR_B, 75, 0);
		sendBTCmessage(10, BTCommunicator.MOTOR_C, 0, 0);
	}

	void do4() {
		// Toast.makeText(NumberOneClass.this, "右转", Toast.LENGTH_SHORT).show();
		sendBTCmessage(BTCommunicator.NO_DELAY, BTCommunicator.MOTOR_C, 75, 0);
		sendBTCmessage(10, BTCommunicator.MOTOR_B, 0, 0);
	}

	void do3() {
		// Toast.makeText(NumberOneClass.this, "停止", Toast.LENGTH_SHORT).show();
		sendBTCmessage(BTCommunicator.NO_DELAY, BTCommunicator.MOTOR_B, 0, 0);
		sendBTCmessage(10, BTCommunicator.MOTOR_C, 0, 0);
	}

	public static int num = 0;

	void demo1() {
		// Toast.makeText(NumberOneClass.this, "随机排序",
		// Toast.LENGTH_SHORT).show();
		// 执行动作一
		// 等2秒
		// 执行动作二
		// 等2秒
		// 。。。
	}

	public static int savaIndex = 0;
	public Thread t;

	void demo2() {
		Toast.makeText(LeShuaNumberTwoClass.this, "执行卡程序", Toast.LENGTH_SHORT)
				.show();
		if (arr.size() != 0) {
			for (int i = 0; i < arr.size(); i++) {
				if (arr.get(i).equals("CARD-00:01")) {

				} else if (arr.get(i).equals("CARD-00:02")) {

				} else if (arr.get(i).equals("CARD-00:03")) {

				} else if (arr.get(i).equals("CARD-00:04")) {
					savaIndex = i;
					currentTimeMillis = System.currentTimeMillis();

					// t = new Thread(new Runnable() {
					//
					// @Override
					// public void run() {
					while (System.currentTimeMillis() - currentTimeMillis <= 500) {

						try {

							if (arr.get(savaIndex - 1).equals("CARD-00:01")) {
								Log.e("LGONUM", num++ + "");
								do1();
							} else if (arr.get(savaIndex - 1).equals(
									"CARD-00:02")) {
								Log.e("LGONUM", num++ + "");
								do2();
							} else {
								do4();
							}
							Thread.sleep(1);
						} catch (InterruptedException e) {
							e.printStackTrace();
						} finally {

						}

					}
					do3();
					// }
					// });
					// t.start();
				} else if (arr.get(i).equals("CARD-00:05")) {

					savaIndex = i;
					currentTimeMillis = System.currentTimeMillis();

					// t = new Thread(new Runnable() {

					// @Override
					// public void run() {
					while (System.currentTimeMillis() - currentTimeMillis <= 1000) {
						try {
							if (arr.get(savaIndex - 1).equals("CARD-00:01")) {
								Log.e("LGONUM", num++ + "");
								do1();
							} else if (arr.get(savaIndex - 1).equals(
									"CARD-00:02")) {
								Log.e("LGONUM", num++ + "");
								do2();
							} else {
								do4();
							}
							Thread.sleep(1);
						} catch (InterruptedException e) {
							e.printStackTrace();
						} finally {

						}

					}
					do3();

					// }
					// });
					// t.start();
				} else if (arr.get(i).equals("CARD-00:06")) {
					do3();
				}
			}
		}
	}

	private ViewPager viewPager;
	private String scanResult;
	private ListView listView_show;
	private ShuaKaListviewAdapter adapter2;
	private long currentTimeMillis;
	private Handler handler;

	int sx(int x) {
		return (int) (dx * x);
	}

	int sy(int y) {
		return (int) (dy * y);
	}

	void selectNXT() {
		Intent serverIntent = new Intent(LeShuaNumberTwoClass.this,
				DeviceListActivity.class);
		startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
	}

	public void setBitmap() {
		String fn = String.format(Environment.getExternalStorageDirectory()
				.getPath() + "/zhuxiang/t%02d.jpg", index);
		// imageMain.setImageBitmap(getLoacalBitmap("/sdcard/zhuxiang/t02.jpg"));
	}

	public static Bitmap getLoacalBitmap(String url) {
		try {
			FileInputStream fis = new FileInputStream(url);
			return BitmapFactory.decodeStream(fis); // /把流转化为Bitmap图片

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void song2() {
		// 中1DO 523 中2RE 578 中3MI 659 中4FA 698
		// 中5SO 784 中6LA 880 中7SI 988 100 1/4 400 -1
		sendBTCmessage(BTCommunicator.NO_DELAY, BTCommunicator.DO_BEEP, 523,
				200);
		sendBTCmessage(300, BTCommunicator.DO_BEEP, 523, 200);
		sendBTCmessage(600, BTCommunicator.DO_BEEP, 784, 200);
		sendBTCmessage(900, BTCommunicator.DO_BEEP, 784, 200);
		sendBTCmessage(1200, BTCommunicator.DO_BEEP, 880, 200);
		sendBTCmessage(1500, BTCommunicator.DO_BEEP, 880, 200);
		sendBTCmessage(1800, BTCommunicator.DO_BEEP, 784, 400);

		sendBTCmessage(2300, BTCommunicator.DO_BEEP, 698, 200);
		sendBTCmessage(2600, BTCommunicator.DO_BEEP, 698, 200);
		sendBTCmessage(2900, BTCommunicator.DO_BEEP, 659, 200);
		sendBTCmessage(3200, BTCommunicator.DO_BEEP, 659, 200);
		sendBTCmessage(3500, BTCommunicator.DO_BEEP, 578, 200);
		sendBTCmessage(3800, BTCommunicator.DO_BEEP, 578, 200);
		sendBTCmessage(4100, BTCommunicator.DO_BEEP, 523, 400);
	}

	public void song() {
		// 中1DO 523 中2RE 578 中3MI 659 中4FA 698
		// 中5SO 784 中6LA 880 中7SI 988 100 1/4 400 -1
		sendBTCmessage(BTCommunicator.NO_DELAY, BTCommunicator.EV3_BEEP, 523,
				200);
		sendBTCmessage(300, BTCommunicator.EV3_BEEP, 523, 200);
		sendBTCmessage(600, BTCommunicator.EV3_BEEP, 784, 200);
		sendBTCmessage(900, BTCommunicator.EV3_BEEP, 784, 200);
		sendBTCmessage(1200, BTCommunicator.EV3_BEEP, 880, 200);
		sendBTCmessage(1500, BTCommunicator.EV3_BEEP, 880, 200);
		sendBTCmessage(1800, BTCommunicator.EV3_BEEP, 784, 400);

		sendBTCmessage(2300, BTCommunicator.EV3_BEEP, 698, 200);
		sendBTCmessage(2600, BTCommunicator.EV3_BEEP, 698, 200);
		sendBTCmessage(2900, BTCommunicator.EV3_BEEP, 659, 200);
		sendBTCmessage(3200, BTCommunicator.EV3_BEEP, 659, 200);
		sendBTCmessage(3500, BTCommunicator.EV3_BEEP, 578, 200);
		sendBTCmessage(3800, BTCommunicator.EV3_BEEP, 578, 200);
		sendBTCmessage(4100, BTCommunicator.EV3_BEEP, 523, 400);
		Log.e("唱歌", "经过这块");

	}

	void ev3act1() {
		// showToast("机身", Toast.LENGTH_SHORT);
		ev3startProgram(0, "BrkProg_SAVE", "a0101.rpf");
	}

	void ev3act2() {
		// showToast("存储卡", Toast.LENGTH_SHORT);
		ev3startProgram(1, "music", "a0101.rbf");
	}

	void ev3Action(String action) {
		startProgram(action);
	}

	Handler handler2 = new Handler();
	Runnable runnable2 = new Runnable() {
		@Override
		public void run() {
			isRun();
			handler2.postDelayed(this, 500);
		}
	};

	Handler handler3 = new Handler();
	Runnable runnable3 = new Runnable() {
		@Override
		public void run() {
			nxtRun();
			handler3.postDelayed(this, 500);
		}
	};

	public void nxtRun() {
		programToStart = null;
		// infoProgram();
		sendBTCmessage(BTCommunicator.NO_DELAY,
				BTCommunicator.GET_PROGRAM_NAME, 0, 0);
	}

	void isRun() {
		// if(mApplication.getDeviceType().equals("ev3")){
		infoProgram();
		// }else{
		// nxtinfoProgram();
		// }
	}

	// public void ev3infoProgram() {
	// if (!isConnected())
	// return;
	// Bundle myBundle = new Bundle();
	// myBundle.putInt("message", BTCommunicator.EV3_INFOPROGRAM);
	// Message myMessage = myHandler.obtainMessage();
	// myMessage.setData(myBundle);
	// btcHandler.sendMessage(myMessage);
	// }
	public void infoProgram() {
		if (!isConnected())
			return;
		Bundle myBundle = new Bundle();
		myBundle.putInt("message", BTCommunicator.EV3_INFOPROGRAM);
		Message myMessage = myHandler.obtainMessage();
		myMessage.setData(myBundle);
		btcHandler.sendMessage(myMessage);
	}

	/* pos：0机身，1存储卡；path文件夹；name文件名（含扩展名） */
	public void ev3startProgram(int pos, String path, String name) {
		Log.i("ev3startProgram", "start");
		if (!isConnected()) {
			Log.i("ev3startProgram", "ev3未连接");
			return;
		}
		handler2.postDelayed(runnable2, 500);
		Bundle myBundle = new Bundle();
		myBundle.putInt("message", BTCommunicator.EV3_STARTPROGRAM);
		myBundle.putInt("pos", pos);
		myBundle.putString("path", path);
		myBundle.putString("name", name);
		Message myMessage = myHandler.obtainMessage();
		myMessage.setData(myBundle);
		btcHandler.sendMessage(myMessage);
		Log.i("ev3startProgram", "end");
	}

	/* pos：0机身，1存储卡；path文件夹；name文件名（含扩展名） */
	// public void nxtstartProgram(int pos, String path, String name) {
	// Log.i("nxtstartProgram", "____position1__");
	// if (!isConnected())
	// return;
	// Log.i("nxtstartProgram", "____position2__");
	// handler3.postDelayed(runnable3, 500);
	// Bundle myBundle = new Bundle();
	// myBundle.putInt("message", BTCommunicator.EV3_STARTPROGRAM);
	// myBundle.putInt("pos", pos);
	// myBundle.putString("path", path);
	// myBundle.putString("name", name);
	// Message myMessage = myHandler.obtainMessage();
	// myMessage.setData(myBundle);
	// btcHandler.sendMessage(myMessage);
	// Log.i("nxtstartProgram", "____position3__");
	// }

	void do3_ev3() {
		Log.i("do3_ev3", "do3_ev3");
		sendBTCmessage(BTCommunicator.NO_DELAY, BTCommunicator.EV3_MOTOR,
				BTCommunicator.EV3_MOTOR_B, 0);
		sendBTCmessage(10, BTCommunicator.EV3_MOTOR,
				BTCommunicator.EV3_MOTOR_C, 0);
	}
	// void do3_nxt() {
	// Log.i("do3_nxt,name", "____do3_nxt"+name);
	// startProgram(name+".rxe");
	// }
}
