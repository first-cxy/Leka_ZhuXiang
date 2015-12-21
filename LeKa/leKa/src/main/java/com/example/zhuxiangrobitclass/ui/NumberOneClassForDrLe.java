package com.example.zhuxiangrobitclass.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhuxiangrobitclass.adpter.GListviewAdapterForDrLe;
import com.example.zhuxiangrobitclass.ui.base.BaseActivity;
import com.example.zhuxiangrobitclass.zxing.BTCommunicator;
import com.example.zhuxiangrobitclass.zxing.BTConnectable;
import com.example.zhuxiangrobitclass.zxing.DeviceListActivity;
import com.example.zhuxiangrobitclass.zxing.LeboMessage;
import com.example.zhuxiangrobitclass.zxing.activity.CaptureActivity;
import com.example.zhuxiangrobitclass1.R;

@SuppressLint("HandlerLeak")
public class NumberOneClassForDrLe extends BaseActivity implements OnTouchListener, OnClickListener, BTConnectable {
	public ViewPager mPager;
	private static int c_id = 0;
	private ArrayList<View> pageList;
	private View[] naviViews;

	private LinearLayout layout12;
	public ArrayList<Integer> arrayList = new ArrayList<Integer>();
	private static final int REQUEST_CONNECT_DEVICE = 1000;
	private static final int REQUEST_ENABLE_BT = 2000;
	private static final int REQUEST_ENABLE_QR = 3000;
	// double dx;
	// double dy;
	// int index;
	public static int currindex = 0;
	public ArrayList<String> arr = new ArrayList<String>();
	private Button btn_robots;
	private RelativeLayout layout_title;
	private static final String TAG = "NumberOneClassForDrLe";

	public int onclickNumber = 0;

	// private View gallery_main;
	// private ArrayList<Map<String, Integer>> all;
	// private ImageSwitcher imageSwitcher;

	private View inflate2;
	private View inflate3_0;
	private View inflate3;
	private View inflate4;
	private View inflate5;
	private View inflate7;
	private View inflate8;
	private View inflate9;
	private View inflate10;
	private View inflate11;
	private View infalte12;
	private View inflate13;
	private View inflate14;
	private View inflate15;
	private View inflate16;
	private View inflate17;
	private View inflate18;
	private View inflate19;
	private View inflate20;
	private View inflate21;
	private View inflate22;
	private View inflate24;
	private View inflate25;
	private View inflate26;
	private View layout28;
	private View inflate31;

	String programToStart;

	private ProgressDialog connectingProgressDialog;
	private BTCommunicator myBTCommunicator = null;
	private boolean connected = false;
	private Handler btcHandler;
	private Toast reusableToast;

	private Button computer;
	private Button shua_ka;
	private Button one_over;
	private ViewPager viewPager;

	private String scanResult;
	private ListView listView_show;
	private GListviewAdapterForDrLe adapter2;
	private TimerTask ts;
	private Timer timer;
	private LinearLayout layout_comput;
	private ImageView com_img;
	private Button showDate;

	// private Timer timer2;
	private long currentTimeMillis;
	private boolean hide = true;

	private float x, y;
	private int mx, my;
	private int screenWidth;
	private int screenHeight;
	private int lastX, lastY;

	public boolean isStartMedio = false;

	private int clickCount = 0;
	private long firstClick = 0;
	private long lastClick = 0;
	private int CLICK_DELAY = 1000;

	private boolean pairing;
	private Timer taskTimer = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// adapter = new SimpleAdapter(this, all, R.layout.layout_item, new
		// String[] { "image" }, new int[] { R.id.imageView });

		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);

		screenWidth = dm.widthPixels;
		screenHeight = dm.heightPixels;

		createBTCommunicator();

		initTextView();
		initViewSize();
		// DisplayMetrics dis = new DisplayMetrics();
		// getWindowManager().getDefaultDisplay().getMetrics(dis);
		// show("屏幕分辨率为:" + dis.widthPixels + " * " + dis.heightPixels);
		// Display mDisplay = getWindowManager().getDefaultDisplay();
		// dx = mDisplay.getWidth() / 100.0;
		// dy = mDisplay.getHeight() / 100.0;
		// index = 0;

	}

	/**
	 * init the viewpager and navigator
	 */
	private void initViewSize() {
		LayoutInflater inflater = getLayoutInflater();
		naviViews = new View[pageList.size()];
		ViewGroup main = (ViewGroup) inflater.inflate(R.layout.numberone_main, null);
		ViewGroup group = (ViewGroup) main.findViewById(R.id.viewGroup);

		viewPager = (ViewPager) main.findViewById(R.id.vPager);
		for (int i = 0; i < pageList.size(); i++) {
			View v = new View(NumberOneClassForDrLe.this);
			v.setLayoutParams(new LayoutParams(12, 12));
			// imageView.setPadding(10, 0, 10, 0);
			naviViews[i] = v;
			final int index = i;
			v.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					viewPager.setCurrentItem(index);
				}
			});
			group.addView(v);
		}
		setContentView(main);
		viewPager.setAdapter(new MyAdapter());
		viewPager.setOnPageChangeListener(new MyListener());
		// viewPager.setCurrentItem(300);
		viewPager.setCurrentItem(0);
		viewPager.setOffscreenPageLimit(3);

	}

	/*
	 * show or hide the bluetooth connection button
	 */
	private void reverse() {
		if (hide) {
			layout_title.setVisibility(View.VISIBLE);
		} else {
			layout_title.setVisibility(View.GONE);
		}
		hide = !hide;
	}

	@Override
	protected void onResume() {
		adapter2.notifyDataSetChanged();
		listView_show.setAdapter(adapter2);
		super.onResume();
	}

	/**
	 * back press down
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		super.onKeyDown(keyCode, event);
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			viewPager.destroyDrawingCache();
			if (taskTimer != null) {
				taskTimer.cancel();
				taskTimer = null;
			}
			return true;
		}
		return false;
	}

	/**
	 * init all the pages
	 */
	private void initTextView() {

		LayoutInflater inflater = getLayoutInflater();
		pageList = new ArrayList<View>();

		// page#2
		inflate2 = inflater.inflate(R.layout.lay1, null);
		pageList.add(inflate2);
		initview1();

		// page#3
		inflate3_0 = inflater.inflate(R.layout.lay2, null);
		pageList.add(inflate3_0);
		initview2();

		// page#4
		inflate3 = inflater.inflate(R.layout.lay3, null);
		pageList.add(inflate3);
		initview3();

		// page#5
		inflate4 = inflater.inflate(R.layout.lay4, null);
		pageList.add(inflate4);
		initview4();

		// page#6
		// inflate5 = inflater.inflate(R.layout.lay5, null);
		// list.add(inflate5);
		// initview5();
		// page#7
		inflate7 = inflater.inflate(R.layout.lay7, null);
		pageList.add(inflate7);
		initview7();

		// page#8
		inflate8 = inflater.inflate(R.layout.lay8, null);
		pageList.add(inflate8);
		initview8();

		// page#9 ?????
		inflate9 = inflater.inflate(R.layout.lay9, null);
		pageList.add(inflate9);
		initInfaate();

		// page#10
		inflate10 = inflater.inflate(R.layout.lay10, null);
		pageList.add(inflate10);

		// page#11
		inflate11 = inflater.inflate(R.layout.lay11, null);
		pageList.add(inflate11);

		// page#12
		infalte12 = inflater.inflate(R.layout.lay12, null);
		pageList.add(infalte12);
		galleryInitToOnclick();

		// page#13
		inflate13 = inflater.inflate(R.layout.lay13, null);
		pageList.add(inflate13);
		initview13();

		// page#14
		inflate14 = inflater.inflate(R.layout.lay14, null);
		pageList.add(inflate14);

		// page#15
		inflate15 = inflater.inflate(R.layout.lay15, null);
		pageList.add(inflate15);

		// page#16
		inflate16 = inflater.inflate(R.layout.lay16, null);
		pageList.add(inflate16);
		initlayout16();

		// page#17
		inflate17 = inflater.inflate(R.layout.lay17_1, null);
		initlayout17();
		pageList.add(inflate17);

		// inflate31 = inflater.inflate(R.layout.layout31, null);
		// initlayout31();
		// pageList.add(inflate31);

		// page#18
		inflate18 = inflater.inflate(R.layout.lay18, null);
		pageList.add(inflate18);

		// page#19
		inflate19 = inflater.inflate(R.layout.lay19, null);
		pageList.add(inflate19);

		// page#20
		inflate20 = inflater.inflate(R.layout.lay20, null);
		initlayout20();
		pageList.add(inflate20);

		// page#21
		inflate21 = inflater.inflate(R.layout.lay21, null);// 刷卡
		initlayout21();
		pageList.add(inflate21);

		// page#22
		inflate22 = inflater.inflate(R.layout.lay22, null);
		pageList.add(inflate22);

		// page#23
		View layout23 = inflater.inflate(R.layout.lay23, null);
		layout23.setBackgroundResource(R.drawable.a_24);
		pageList.add(layout23);

		// page#24
		inflate24 = inflater.inflate(R.layout.lay24, null);
		pageList.add(inflate24);

		// page#25
		inflate25 = inflater.inflate(R.layout.lay25_1, null);
		initlayout25();
		pageList.add(inflate25);

		// page#26
		inflate26 = inflater.inflate(R.layout.lay26, null);
		initlayout26();
		pageList.add(inflate26);

		// page#27
		View layout27 = inflater.inflate(R.layout.lay27, null);
		layout27.setBackgroundResource(R.drawable.a_28);
		pageList.add(layout27);

		// page#28
		layout28 = inflater.inflate(R.layout.lay28, null);
		layout28.setBackgroundResource(R.drawable.a_29);
		pageList.add(layout28);

		// page#29
		View layout29 = inflater.inflate(R.layout.lay29, null);
		layout29.setBackgroundResource(R.drawable.a_30);
		pageList.add(layout29);

		// page#30 thanks
		View layout30 = inflater.inflate(R.layout.lay30, null);
		pageList.add(layout30);
	}

	/**
	 * page NO#2
	 */

	private void initview1() {
		layout12 = (LinearLayout) inflate2.findViewById(R.id.layout1_one);
		layout_title = (RelativeLayout) inflate2.findViewById(R.id.layout_title);
		layout_title.setVisibility(View.GONE);
		btn_robots = (Button) inflate2.findViewById(R.id.btn_robots);
		btn_robots.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				selectNXT();
			}
		});
		// Button tbn_robit = (Button) inflate2.findViewById(R.id.tbn_robit);
		layout12.setOnTouchListener(this);
		layout12.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				onclickNumber++;
				if (onclickNumber == 1) {
					TextView konew_text1 = (TextView) layout12.findViewById(R.id.konew_text1);
					konew_text1.setVisibility(View.VISIBLE);
					return;
				} else if (onclickNumber == 2) {
					TextView konew_text2 = (TextView) layout12.findViewById(R.id.konew_text2);
					konew_text2.setVisibility(View.VISIBLE);
					return;
				} else {
					return;
				}
			}
		});
	}

	/**
	 * page NO#3
	 */
	private void initview2() {

		inflate3_0.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getX() > 200 && event.getX() < 600 && event.getY() > 430 && event.getY() < 640) {
					playSound();
				}
				return false;
			}
		});
	}

	/**
	 * page NO#4
	 */
	private long firstClickTime = 0;

	private void initview3() {
		LinearLayout LinearLayout_id = (LinearLayout) inflate3.findViewById(R.id.LinearLayout_id);
		LinearLayout_id.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					if (System.currentTimeMillis() - firstClickTime < 500) {
						if (event.getX() > 100 * screenWidth / 960 && event.getX() < 470 * screenWidth / 960 && event.getY() > 200 * screenHeight / 540 && event.getY() < 400 * screenHeight / 540) {
							String url = Environment.getExternalStorageDirectory().getPath() + "/m01.mp4";
							Intent intent = new Intent(NumberOneClassForDrLe.this, videoplayer.class);
							// intent.setDataAndType(uri, "video/mp4");
							intent.putExtra("url", url);
							startActivity(intent);

						} else if (event.getX() > 537 * screenWidth / 960 && event.getX() < 904 * screenWidth / 960 && event.getY() > 200 * screenHeight / 540 && event.getY() < 400 * screenHeight / 540) {
							String url = Environment.getExternalStorageDirectory().getPath() + "/m02.mp4";
							// intent.setDataAndType(uri, "video/mp4");
							Intent intent = new Intent(NumberOneClassForDrLe.this, videoplayer.class);
							intent.putExtra("url", url);
							startActivity(intent);
						}
					}
					firstClickTime = System.currentTimeMillis();
				}
				return false;
			}
		});
	}

	/**
	 * page NO#5
	 */

	private void initview4() {
		inflate4.setOnTouchListener(new JudgelistenerReverse());
	}

	/**
	 * 
	 * 选右（对）选左（错）
	 * 
	 */

	private class JudgelistenerReverse implements OnTouchListener {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getX() > 600 * screenWidth / 960 && event.getX() < 700 * screenWidth / 960 && event.getY() > 370 * screenHeight / 540 && event.getY() < 450 * screenHeight / 540) {
				Intent intent = new Intent(NumberOneClassForDrLe.this, ErrorClass.class);
				intent.putExtra("errornum", 1);
				startActivity(intent);
				overridePendingTransition(R.anim.right_out, R.anim.hold);
			} else if (event.getX() > 795 * screenWidth / 960 && event.getX() < 876 * screenWidth / 960 && event.getY() > 370 * screenHeight / 540 && event.getY() < 450 * screenHeight / 540) {

				startActivity(new Intent(NumberOneClassForDrLe.this, WinClass.class));
				overridePendingTransition(R.anim.right_out, R.anim.hold);
			}
			return false;
		}
	}

	/**
	 * 
	 * 选左（对）选右（错）
	 * 
	 */
	private class Judgelistener implements OnTouchListener {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getX() > 600 * screenWidth / 960 && event.getX() < 700 * screenWidth / 960 && event.getY() > 370 * screenHeight / 540 && event.getY() < 450 * screenHeight / 540) {
				startActivity(new Intent(NumberOneClassForDrLe.this, WinClass.class));
				overridePendingTransition(R.anim.right_out, R.anim.hold);
			} else if (event.getX() > 795 * screenWidth / 960 && event.getX() < 876 * screenWidth / 960 && event.getY() > 370 * screenHeight / 540 && event.getY() < 450 * screenHeight / 540) {
				Intent intent = new Intent(NumberOneClassForDrLe.this, ErrorClass.class);
				intent.putExtra("errornum", 1);
				startActivity(intent);
				overridePendingTransition(R.anim.right_out, R.anim.hold);
			}
			return false;
		}
	}

	/**
	 * page NO#6
	 */
	private void initview5() {
		inflate5.setOnTouchListener(new Judgelistener());
	}

	/**
	 * page NO#7
	 */
	private void initview7() {
		inflate7.setOnTouchListener(new Judgelistener());
	}

	/**
	 * page NO#8
	 */
	private void initview8() {
		inflate8.setOnTouchListener(new Judgelistener());
	}

	/**
	 * page NO#9
	 */
	private void initInfaate() {
		inflate9.setOnTouchListener(this);
		Button hello_btn = (Button) inflate9.findViewById(R.id.hello_btn);
		hello_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				sayHello();
			}
		});
	}

	/**
	 * page NO#12
	 */
	private void startGiles(int showMode) {
		try {
			Intent intent = new Intent();
			intent.putExtra("showMode", showMode);
			intent.setComponent(new ComponentName("org.giles.ui", "org.giles.ui.MainActivity"));
			startActivity(intent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void galleryInitToOnclick() {
		infalte12.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startGiles(4);
			}
		});
	}

	/**
	 * page NO#13
	 */
	private void initview13() {
		inflate13.setOnTouchListener(new JudgelistenerReverse());
	}

	/**
	 * page NO#16
	 */
	private void initlayout16() {
		inflate16.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startGiles(5);
			}
		});

	}

	/**
	 * page NO#17
	 */

	private boolean hasTouchDown = false;
	private long touchDownStart = 0;
	private Button handButton;
	private Button xiaolianButton;
	private Button changgeButton;
	private Button qianjinButton;

	public class OnTouchMove implements OnTouchListener {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				x = event.getX();
				y = event.getY();
				hasTouchDown = true;
				touchDownStart = System.currentTimeMillis();
			case MotionEvent.ACTION_MOVE:
				mx = (int) (event.getRawX() - x);
				my = (int) (event.getRawY() - y);

				if (mx < 5 && my < 5) {
					break;
				}
				v.layout(mx, my, mx + v.getWidth(), my + v.getHeight());
				break;
			case MotionEvent.ACTION_UP:
				hasTouchDown = false;
				if (System.currentTimeMillis() - touchDownStart < 500) {
					if (v == handButton) {
						ArrayList<String> seq = new ArrayList<String>();
						seq.add((String) handButton.getTag());
						playSequenceAction(seq);
					} else if (v == xiaolianButton) {
						ArrayList<String> seq = new ArrayList<String>();
						seq.add((String) xiaolianButton.getTag());
						playSequenceAction(seq);
					} else if (v == changgeButton) {
						ArrayList<String> seq = new ArrayList<String>();
						seq.add((String) changgeButton.getTag());
						playSequenceAction(seq);
					} else if (v == qianjinButton) {
						ArrayList<String> seq = new ArrayList<String>();
						seq.add((String) qianjinButton.getTag());
						playSequenceAction(seq);
					}
				}
				break;
			}
			return true;
		}

	}

	private int[] buttonPosition(Button btn) {
		int left = btn.getLeft() + btn.getWidth() / 2;
		int top = btn.getTop() + btn.getHeight() / 2;

		if (left > 1000 && left < 1200 && top > 240 && top < 640) {
			return new int[] { left, top };
		} else {
			return new int[] { 0, 0 };
		}
	}

	private void initlayout17() {
		inflate17.setOnTouchListener(this);
		OnTouchMove onTouchMove = new OnTouchMove();
		handButton = (Button) inflate17.findViewById(R.id.hand_btn);
		handButton.setOnTouchListener(onTouchMove);
		handButton.setTag(SEQ_BACK);

		xiaolianButton = (Button) inflate17.findViewById(R.id.xiaolian_btn);
		xiaolianButton.setOnTouchListener(onTouchMove);
		xiaolianButton.setTag(SEQ_SMILE);

		changgeButton = (Button) inflate17.findViewById(R.id.changge_btn);
		changgeButton.setOnTouchListener(onTouchMove);
		changgeButton.setTag(SEQ_SONG);

		qianjinButton = (Button) inflate17.findViewById(R.id.qianjin_btn);
		qianjinButton.setOnTouchListener(onTouchMove);
		qianjinButton.setTag(SEQ_FORWARD);

		// ListView show_Group_action = (ListView)
		// inflate17.findViewById(R.id.show_Group_action);
		inflate17.findViewById(R.id.group_action).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Button handButton = (Button) inflate17.findViewById(R.id.hand_btn);
				Button xiaolianButton = (Button) inflate17.findViewById(R.id.xiaolian_btn);
				Button changgeButton = (Button) inflate17.findViewById(R.id.changge_btn);
				Button qianjinButton = (Button) inflate17.findViewById(R.id.qianjin_btn);

				int[] handButtonPos = buttonPosition(handButton);
				int[] xiaolianButtonPos = buttonPosition(xiaolianButton);
				int[] changgeButtonPos = buttonPosition(changgeButton);
				int[] qianjinButtonPos = buttonPosition(qianjinButton);

				ArrayList<Button> arrayList = new ArrayList<Button>();
				if (handButtonPos[0] != 0) {
					arrayList.add(handButton);
				}
				if (xiaolianButtonPos[0] != 0) {
					arrayList.add(xiaolianButton);
				}
				if (changgeButtonPos[0] != 0) {
					arrayList.add(changgeButton);
				}
				if (qianjinButtonPos[0] != 0) {
					arrayList.add(qianjinButton);
				}

				Collections.sort(arrayList, new Comparator<Button>() {
					public int compare(Button o1, Button o2) {
						Button s1 = (Button) o1;
						Button s2 = (Button) o2;
						if (s1.getTop() > s2.getTop())
							return 1;
						else if (s1.getTop() == s2.getTop()) {
							return 0;
						}
						return -1;
					}
				});

				ArrayList<String> seq = new ArrayList<String>();
				for (Button btn : arrayList) {
					seq.add((String) btn.getTag());
					Log.e("button ", btn.getTag().toString());
				}

				playSequenceAction(seq);

			}
		});

	}

	/**
	 * page NO#20
	 */
	private void initlayout20() {
		inflate20.setOnTouchListener(this);
		layout_comput = (LinearLayout) inflate20.findViewById(R.id.layout_comput);
		com_img = (ImageView) inflate20.findViewById(R.id.com_img);
		com_img.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				com_img.setVisibility(View.GONE);
				layout_comput.setVisibility(View.VISIBLE);
			}
		});
		computer = (Button) inflate20.findViewById(R.id.computer);
		shua_ka = (Button) inflate20.findViewById(R.id.shua_ka);
		one_over = (Button) inflate20.findViewById(R.id.one_over);
		computer.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				layout_comput.setVisibility(View.GONE);
				com_img.setVisibility(View.VISIBLE);
			}
		});
		shua_ka.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				viewPager.setCurrentItem(18);
			}
		});
		one_over.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ArrayList<String> allStep = new ArrayList<String>();
				allStep.add(STEP_1);
				allStep.add(STEP_2);
				allStep.add(STEP_3);
				playSequenceAction(allStep);
			}
		});
	}

	/**
	 * page NO#21
	 */
	private void initlayout21() {
		inflate21.setOnTouchListener(this);
		Button btn1 = (Button) inflate21.findViewById(R.id.btn1);
		Button btn2 = (Button) inflate21.findViewById(R.id.btn2);
		Button btn3 = (Button) inflate21.findViewById(R.id.btn3);
		listView_show = (ListView) inflate21.findViewById(R.id.listView_show);
		adapter2 = new GListviewAdapterForDrLe(NumberOneClassForDrLe.this, arr);
		btn1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent openCameraIntent = new Intent(NumberOneClassForDrLe.this, CaptureActivity.class);
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
				ArrayList<String> seq = new ArrayList<String>();
				for (String str : arr) {
					if (str == null) {
						continue;
					}
					if (str.contains("L0101")) {
						seq.add(QR_L0101);
					} else if (str.contains("L0102")) {
						seq.add(QR_L0102);
					} else if (str.contains("L0103")) {
						seq.add(QR_L0103);
					} else if (str.contains("L0104")) {
						seq.add(QR_L0104);
					} else if (str.contains("L0105")) {
						seq.add(QR_L0105);
					} else if (str.contains("L0106")) {
						seq.add(QR_L0106);
					}
				}

				playSequenceAction(seq);
				// JSONObject json = new
				// JSONObject(arr.get(i).toString()).getJSONObject("leka");
				// String name = json.getString("name");
				// subSequence = name.subSequence(0, 3).toString();
				// Message msg = new Message();
				// msg.obj = name;

			}
		});
	}

	/**
	 * page NO#25
	 */
	private Handler uiHandler = new Handler() {
		public void handleMessage(Message msg) {
			TextView textView = (TextView) inflate25.findViewById(R.id.layout25_textView);
			if (textView != null) {
				int line = (Integer) textView.getTag();
				if (line > 12) {
					textView.setText("机器人笑笑 电机A ：转速  10rpm \n");
					textView.setTag(1);
				} else if (line == 12) {
					textView.append("机器人笑笑 电机B ：转速   5rpm \n");
					textView.setTag(line + 1);
				} else if (line == 11) {
					textView.append("机器人笑笑 电机C ：转速   0rpm \n");
					textView.setTag(line + 1);
				} else if (line == 10) {
					textView.append("机器人贝贝 电机A ：温度 30 C \n");
					textView.setTag(line + 1);
				} else if (line == 9) {
					textView.append("机器人贝贝 电机A ：温度 30 C 转速10 rpm \n");
					textView.setTag(line + 1);
				} else if (line == 8) {
					textView.append("机器人贝贝 电机A ：温度 30 C 转速10 rpm \n");
					textView.setTag(line + 1);
				} else if (line == 7) {
					textView.append("机器人贝贝 电机A ：温度 30 C 转速10 rpm \n");
					textView.setTag(line + 1);
				} else if (line == 8) {
					textView.append("机器人猫咪 电机B ：转速10 rpm \n");
					textView.setTag(line + 1);
				} else if (line == 4) {
					textView.append("机器人猫咪 电机B  ：转速10 rpm \n");
					textView.setTag(line + 1);
				} else if (line == 5) {
					textView.append("机器人猫咪 电机B ：转速10 rpm \n");
					textView.setTag(line + 1);
				} else if (line == 6) {
					textView.append("机器人猫咪 电机A ：转速10 rpm \n");
					textView.setTag(line + 1);
				} else if (line == 1) {
					textView.append("机器人猫咪 电机C ：转速10 rpm \n");
					textView.setTag(line + 1);
				} else {
					textView.append("机器人猫咪 电机C ：转速10 rpm \n");
					textView.setTag(line + 1);
				}

			}
		};
	};

	private void initlayout25() {
		// showDate = (Button) inflate25.findViewById(R.id.showDate);
		TextView textView = (TextView) inflate25.findViewById(R.id.layout25_textView);
		textView.setTag(0);
		textView.setOnClickListener(new OnClickListener() {
			private TimerTask ts;

			@Override
			public void onClick(View v) {
				taskTimer = new Timer(true);
				ts = new TimerTask() {

					@Override
					public void run() {
						uiHandler.sendEmptyMessage(10);
					}
				};
				taskTimer.schedule(ts, 0, 1000);
			}
		});

	}

	/**
	 * page NO#26
	 */
	private void initlayout26() {
		inflate26.findViewById(R.id.yijianwancheng).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.e("ontoch~~~~~~~", "act6");
			}
		});
	}

	/*
	 * view page adapter class
	 */
	class MyAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			// return Integer.MAX_VALUE;
			return pageList.size();
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
			((ViewPager) arg0).removeView((View) arg2);
			arg2 = null;
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			try {
				// 设置循环
				// ((ViewPager) arg0).addView(list.get(arg1%list.size()),0);
				((ViewPager) arg0).addView(pageList.get(arg1));
			} catch (Exception e) {
			}
			// return list.get(arg1%list.size());
			if (arg1 == pageList.size()) {
				NumberOneClassForDrLe.this.finish();
				viewPager.destroyDrawingCache();
				Toast.makeText(getBaseContext(), "亲 第一个课时结束了", Toast.LENGTH_LONG).show();
				overridePendingTransition(R.anim.slide_left, R.anim.hold);
				return null;
				// }else if (arg1 > list.size()+1) {
				//
				// return null;
			} else {
				return pageList.get(arg1);
			}

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

	/**
	 * view page listener
	 * 
	 */
	class MyListener implements OnPageChangeListener {

		// 当滑动状态改变时调用
		@Override
		public void onPageScrollStateChanged(int arg0) {
			// arg0=arg0%list.size();
			// Log.e("onPageScrollStateChanged", "onPageScrollStateChanged");

		}

		// 当当前页面被滑动时调用
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// Log.e("onPageScrolled", "onPageScrolled");
			// if (arg0 == 23) {
			// nxt1();
			// }
			// if (arg0 == 24) {
			// nxt3();
			// }
			if (arg0 == 23 || arg0 == 25) {
				if (taskTimer != null) {
					taskTimer.cancel();
					taskTimer = null;
				}
			}
		}

		// 当新的页面被选中时调用
		@Override
		public void onPageSelected(int arg0) {
			// Log.e("onPageSelected", "onPageSelected");
			if (arg0 > 2) {
				arg0 = arg0 % pageList.size();
			}
			c_id = arg0;
			for (int i = 0; i < naviViews.length; i++) {
				naviViews[arg0].setBackgroundResource(R.drawable.page);
				if (arg0 != i) {
					naviViews[i].setBackgroundResource(R.drawable.page_now);
				}
			}
			if (arg0 == 23) {
				// nxt1();
				handler.postDelayed(runnable, 500);
			}
			if (arg0 == 24) {
				handler.postDelayed(runnable, 500);
				// nxt3();
			}
			Log.e("-------------", "当前是第" + c_id + "页");
		}

	}

	// public boolean onTouch(View v, MotionEvent event) {
	// switch (event.getAction()) {
	// case MotionEvent.ACTION_DOWN:
	// x = event.getX();
	// y = event.getY();
	// if (event.getRawX() > 1050 && event.getRawX() < 1200
	// && event.getRawY() > 150&& event.getRawY() <350) {
	// for(int i=0;i<arrayList.size();i++){
	// if(arrayList.get(i)==v.getId()){
	// arrayList.remove(i);
	// show("删除了"+v.getId());show("删除了"+v.getId());
	// }
	// }
	// }
	// case MotionEvent.ACTION_MOVE:
	// mx = (int) (event.getRawX() - x);
	// my = (int) (event.getRawY() - 50 - y);
	//
	// v.layout(mx, my, mx + v.getWidth(), my + v.getHeight());
	// if (event.getRawX() > 1050 && event.getRawX() < 1200
	// && event.getRawY() > 150&& event.getRawY() <350) {
	// arrayList.add(v.getId());
	// show("添加了"+v.getId());
	// }
	// break;
	// }
	// return true;
	// }

	@Override
	public boolean onTouch(View v, MotionEvent event) {

		if (MotionEvent.ACTION_DOWN == event.getAction()) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				// 如果第二次点击 距离第一次点击时间过长 那么将第二次点击看为第一次点击
				Log.i(TAG, "clickCount:" + clickCount);
				if (firstClick != 0 && System.currentTimeMillis() - firstClick > 500) {
					clickCount = 0;
				}
				clickCount++;
				if (clickCount == 1) {
					firstClick = System.currentTimeMillis();
				} else if (clickCount == 2) {
					lastClick = System.currentTimeMillis();
					// 两次点击小于500ms 也就是连续点击
					if (lastClick - firstClick < 500) {
						Log.v("Double", "Double");
						if (v == layout12) {
							reverse();
						} else {
							selectNXT();
						}

					}
				}
			}
		}
		return false;
	}

	// -------------action-----------------
	private static final String SEQ_BACK = "070401040705"; // ("0904010A");//
															// 后退2秒
	private static final String SEQ_SMILE = "0901010A";// 5s笑脸
	private static final String SEQ_FORWARD = "070101040705";// ("0701010A");//
																// 前进2秒
	private static final String SEQ_SONG = "0A02010A0A10";// 第二首歌 ，5秒
	private static final String QR_L0101 = "0701";
	private static final String QR_L0102 = "0702";
	private static final String QR_L0103 = "0703";
	private static final String QR_L0104 = "0101"; // 0.5s
	private static final String QR_L0105 = "0102"; // 2s
	private static final String QR_L0106 = "0A01"; // 第一首歌
	private static final String STEP_1 = "07010104";
	private static final String STEP_2 = "0702010107010103";
	private static final String STEP_3 = "07030101070101030705";

	private void playSound() {
		if (isConnected()) {
			sendDrLeMessage("0A01010A0A10");// 第一首歌 5s
			//show("第一首歌曲 5s ");
		} else {
			show("小伙伴不在");
		}
	}

	private void sayHello() {
		if (isConnected()) {
			sendDrLeMessage("0701010407050A03010A0A10");// 第三首歌 ，5秒
			//show("前进2秒，停止， 第三首歌 5秒");
		} else {
			show("小伙伴不在");
		}
	}

	private void playSequenceAction(List<String> actions) {
		StringBuffer sb = new StringBuffer();
		for (String str : actions) {
			sb.append(str + " ");
		}
		if (isConnected()) {
			sendDrLeMessage(actions);
			//show("命令序列－－－－" + sb.toString());
		} else {
			show("小伙伴不在");
		}
	}

	// -----------end action-------------------------

	/**
	 * Gets the current connection status.
	 * 
	 * @return the current connection status to the robot.
	 */
	public boolean isConnected() {
		return connected;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case REQUEST_CONNECT_DEVICE:
			if (resultCode == Activity.RESULT_OK) {
				String address = data.getExtras().getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
				pairing = data.getExtras().getBoolean(DeviceListActivity.PAIRING);
				startBTCommunicator(address);
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
				arr.add(scanResult);

			}
			break;
		}
	}

	/**
	 * Creates a new object for communication to the NXT robot via bluetooth and
	 * fetches the corresponding handler.
	 */
	private void createBTCommunicator() {
		// interestingly BT adapter needs to be obtained by the UI thread - so
		// we pass it in in the constructor
		myBTCommunicator = new BTCommunicator(this, myHandler, BluetoothAdapter.getDefaultAdapter(), getResources());
		btcHandler = myBTCommunicator.getHandler();
	}

	private void startBTCommunicator(String mac_address) {
		connected = false;
		connectingProgressDialog = ProgressDialog.show(this, "", "和小伙伴交流中", true);
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
	 * Receive messages from the BTCommunicator
	 */
	final Handler myHandler = new Handler() {
		@Override
		public void handleMessage(Message myMessage) {
			switch (myMessage.getData().getInt("message")) {
			case BTCommunicator.DISPLAY_TOAST:
				Toast.makeText(getBaseContext(), "" + myMessage.getData().getString("toastText"), Toast.LENGTH_SHORT).show();
				break;
			case BTCommunicator.STATE_CONNECTED:
				connectingProgressDialog.dismiss();
				reusableToast.makeText(getApplicationContext(), "你可以和小伙伴互动啦", Toast.LENGTH_LONG).show();
				connected = true;
				break;
			case BTCommunicator.STATE_CONNECTERROR_PAIRING:
				connectingProgressDialog.dismiss();
				reusableToast.makeText(getApplicationContext(), "STATE_CONNECTERROR_PAIRING", Toast.LENGTH_LONG).show();
				// destroyBTCommunicator();
				connected = false;
				break;

			case BTCommunicator.STATE_CONNECTERROR:
				connectingProgressDialog.dismiss();
				reusableToast.makeText(getApplicationContext(), "小伙伴不理你", Toast.LENGTH_LONG).show();
				connected = false;
				break;
			case BTCommunicator.STATE_RECEIVEERROR:
			case BTCommunicator.STATE_SENDERROR:
				reusableToast.makeText(getApplicationContext(), "小伙伴离开了", Toast.LENGTH_LONG).show();
				// destroyBTCommunicator();
				connected = false;
				break;
			default:
				break;
			}
		}
	};

	private void sendDrLeMessage(List<String> seq) {
		byte[] message = LeboMessage.getProgramMessage(1, seq);
		try {
			myBTCommunicator.sendDrLeMessage(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void sendDrLeMessage(String msg) {
		byte[] message = LeboMessage.getProgramMessage(1, msg);
		try {
			myBTCommunicator.sendDrLeMessage(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static int savaIndex = 0;
	public Thread t;

	Handler handler = new Handler();
	Runnable runnable = new Runnable() {
		@Override
		public void run() {
			// nxt2();
			handler.postDelayed(this, 500);
		}
	};

	void selectNXT() {
		Intent serverIntent = new Intent(NumberOneClassForDrLe.this, DeviceListActivity.class);
		startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
	}

	@Override
	public boolean isPairing() {
		// TODO Auto-generated method stub
		return false;
	}

	// /////////////////////////////////////////////////////////////////////////////////

	// int sx(int x) {
	// return (int) (dx * x);
	// }
	//
	// int sy(int y) {
	// return (int) (dy * y);
	// }

	// public void setBitmap() {
	// String fn =
	// String.format(Environment.getExternalStorageDirectory().getPath() +
	// "/zhuxiang/t%02d.jpg", index);
	// ImageView robit_img = (ImageView) layout12.findViewById(R.id.robit_img);
	// robit_img.setImageBitmap(getLoacalBitmap(fn));
	// // imageMain.setImageBitmap(getLoacalBitmap("/sdcard/zhuxiang/t02.jpg"));
	// }

	// public class MyOnClickListener implements View.OnClickListener {
	// private int index = 0;
	//
	// public MyOnClickListener(int i) {
	// index = i;
	// }
	//
	// @Override
	// public void onClick(View v) {
	// mPager.setCurrentItem(index);
	// }
	// };

	// public static Bitmap getLoacalBitmap(String url) {
	// try {
	// FileInputStream fis = new FileInputStream(url);
	// return BitmapFactory.decodeStream(fis); // /把流转化为Bitmap图片
	//
	// } catch (FileNotFoundException e) {
	// e.printStackTrace();
	// return null;
	// }
	// }
	@Override
	public void onClick(View v) {

	}

}
