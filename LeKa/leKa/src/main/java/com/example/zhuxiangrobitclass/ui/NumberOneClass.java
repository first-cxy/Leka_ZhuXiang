package com.example.zhuxiangrobitclass.ui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.example.zhuxiangrobitclass.adpter.GListviewAdapter;
import com.example.zhuxiangrobitclass.zxing.BTCommunicator;
import com.example.zhuxiangrobitclass.zxing.BTConnectable;
import com.example.zhuxiangrobitclass.zxing.DeviceListActivity;
import com.example.zhuxiangrobitclass.zxing.activity.CaptureActivity;
import com.example.zhuxiangrobitclass1.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

//import com.appendix.fxb.R;
//import com.appendix.fxb.util.Util;
//import com.zxing.BTCommunicator;
//import com.zxing.BTConnectable;
//import com.zxing.DeviceListActivity;
//import com.zxing.activity.CaptureActivity;

public class NumberOneClass extends Activity implements OnTouchListener,
		OnClickListener, BTConnectable {
	public ViewPager mPager;
	private static int c_id = 0;
	private ArrayList<View> list;
	private View[] vs;
	private LayoutInflater inflater;
	private View inflate2;
	private LinearLayout layout12;
	public ArrayList<Integer> arrayList = new ArrayList<Integer>();
	private static final int REQUEST_CONNECT_DEVICE = 1000;
	private static final int REQUEST_ENABLE_BT = 2000;
	private static final int REQUEST_ENABLE_QR = 3000;
	double dx;
	double dy;
	int index;
	public static int currindex = 0;
	public ArrayList<String> arr = new ArrayList<String>();
	private Button btn_robots;
	private RelativeLayout layout_title;
	private static final String TAG = "NumberOneClass";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		adapter = new SimpleAdapter(this, all, R.layout.layout_item,
				new String[] { "image" }, new int[] { R.id.imageView });

		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);

		screenWidth = dm.widthPixels;
		screenHeight = dm.heightPixels;

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
		
	}

	private void initViewSize() {
		vs = new View[list.size()];
		ViewGroup main = (ViewGroup) inflater.inflate(R.layout.numberone_main,
				null);

		ViewGroup group = (ViewGroup) main.findViewById(R.id.viewGroup);

		viewPager = (ViewPager) main.findViewById(R.id.vPager);
		for (int i = 0; i < list.size(); i++) {
			View v = new View(NumberOneClass.this);
			v.setLayoutParams(new LayoutParams(12, 12));
			// imageView.setPadding(10, 0, 10, 0);
			vs[i] = v;
			final int index = i;
			v.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					viewPager.setCurrentItem(index);
				}
			});
			// if (i == 0) {
			//
			// imageViews[i].setBackgroundResource(R.drawable.guide_dot_white);
			// } else {
			// imageViews[i].setBackgroundResource(R.drawable.guide_dot_black);
			// }
			group.addView(v);
		}
		setContentView(main);
		viewPager.setAdapter(new MyAdapter());
		viewPager.setOnPageChangeListener(new MyListener());
		// viewPager.setCurrentItem(300);
		viewPager.setCurrentItem(0);
		viewPager.setOffscreenPageLimit(3);

		
	}

	private boolean hide = true;

	private void reverse() {
		if (hide) {
			layout_title.setVisibility(View.VISIBLE);
		} else {
			layout_title.setVisibility(View.GONE);
		}
		hide = !hide;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		super.onKeyDown(keyCode, event);
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			viewPager.destroyDrawingCache();
			return true;
		}
		return false;
	}

	private void InitTextView() {
		createBTCommunicator();
		LayoutInflater inflater = getLayoutInflater();
		list = new ArrayList<View>();
		inflate2 = inflater.inflate(R.layout.lay1, null);
		list.add(inflate2);
		initview1();
		inflate22 = inflater.inflate(R.layout.lay2, null);
		list.add(inflate22);
		initview2();
		inflate3 = inflater.inflate(R.layout.lay3, null);
		list.add(inflate3);
		initview3();
		inflate4 = inflater.inflate(R.layout.lay4, null);
		list.add(inflate4);
		initview4();
		// inflate5 = inflater.inflate(R.layout.lay5, null);
		// list.add(inflate5);
		// initview5();
		inflate7 = inflater.inflate(R.layout.lay7, null);
		list.add(inflate7);
		initview7();
		inflate8 = inflater.inflate(R.layout.lay8, null);
		list.add(inflate8);
		initview8();

		inflate9 = inflater.inflate(R.layout.lay9, null);
		list.add(inflate9);
		initInfaate();
		inflate10 = inflater.inflate(R.layout.lay10, null);
		list.add(inflate10);
		inflate11 = inflater.inflate(R.layout.lay11, null);
		list.add(inflate11);
		infalte12 = inflater.inflate(R.layout.lay12, null);
		list.add(infalte12);
		galleryInitToOnclick();
		inflate13 = inflater.inflate(R.layout.lay13, null);
		list.add(inflate13);
		initview13();
		inflate14 = inflater.inflate(R.layout.lay14, null);
		list.add(inflate14);
		inflate15 = inflater.inflate(R.layout.lay15, null);
		list.add(inflate15);

		inflate16 = inflater.inflate(R.layout.lay16, null);
		list.add(inflate16);
		initlayout16();

		// inflate17 = inflater.inflate(R.layout.lay17, null);
		// initlayout17();
		// list.add(inflate17);

		inflate31 = inflater.inflate(R.layout.layout31, null);
		initlayout31();
		list.add(inflate31);
		
		inflate18 = inflater.inflate(R.layout.lay18, null);
		list.add(inflate18);
		
		inflate19 = inflater.inflate(R.layout.lay19, null);
		list.add(inflate19);
		
		inflate20 = inflater.inflate(R.layout.lay20, null);
		initlayout20();
		list.add(inflate20);
		inflate21 = inflater.inflate(R.layout.lay21, null);// 刷卡
		initlayout21();
		list.add(inflate21);
		
		inflate22 = inflater.inflate(R.layout.lay22, null);
		list.add(inflate22);
		
		View layout23 = inflater.inflate(R.layout.lay23, null);
		layout23.setBackgroundResource(R.drawable.a_24);
		list.add(layout23);
		
		inflate24 = inflater.inflate(R.layout.lay24, null);
		list.add(inflate24);
		
		inflate25 = inflater.inflate(R.layout.lay25, null);
		initlayout25();
		list.add(inflate25);
		
		inflate26 = inflater.inflate(R.layout.lay26, null);
		initlayout26();
		list.add(inflate26);
		
		View layout27 = inflater.inflate(R.layout.lay27, null);
		layout27.setBackgroundResource(R.drawable.a_28);
		list.add(layout27);
		View layout28 = inflater.inflate(R.layout.lay28, null);
		layout28.setBackgroundResource(R.drawable.a_29);
		list.add(layout28);
		View layout29 = inflater.inflate(R.layout.lay29, null);
		layout29.setBackgroundResource(R.drawable.a_30);
		list.add(layout29);
		View layout30 = inflater.inflate(R.layout.lay30, null);
		list.add(layout30);
	}

	private void initlayout31() {
		inflate31.findViewById(R.id.hand_btn1).setOnClickListener(
				new OnClickListener() {
					private TimerTask ts;
					private Timer timer;

					@Override
					public void onClick(View v) {

						ts = new TimerTask() {

							@Override
							public void run() {

								act1();
								if (timer != null) {
									timer.cancel();
									timer = null;
									ts.cancel();
									ts = null;
								}
							}
						};
						timer = new Timer(true);
						timer.schedule(ts, 0, 13000);
					}
				});

		inflate31.findViewById(R.id.xiaolian_btn1).setOnClickListener(
				new OnClickListener() {

					private Timer timer;
					private TimerTask ts;

					@Override
					public void onClick(View v) {
						ts = new TimerTask() {

							@Override
							public void run() {
								act4();
								if (timer != null) {
									timer.cancel();
									timer = null;
									ts.cancel();
									ts = null;
								}
								Log.e("111", "sssssssss");
							}
						};
						timer = new Timer(true);
						timer.schedule(ts, 0, 13000);
					}

				});
		inflate31.findViewById(R.id.changge_btn1).setOnClickListener(
				new OnClickListener() {

					private TimerTask ts;
					private Timer timer;

					@Override
					public void onClick(View v) {
						ts = new TimerTask() {

							@Override
							public void run() {
								songev3();
								// act2();
								// if (timer != null) {
								// timer.cancel();
								// timer = null;
								// ts.cancel();
								// ts = null;
								// }
							}
						};
						timer = new Timer(true);
						timer.schedule(ts, 0, 13000);
					}

				});
		inflate31.findViewById(R.id.qianjin_btn1).setOnClickListener(
				new OnClickListener() {

					private TimerTask ts;
					private Timer timer;

					@Override
					public void onClick(View v) {
						ts = new TimerTask() {

							@Override
							public void run() {
								do1ev3();
								// act3();
								if (timer != null) {
									timer.cancel();
									timer = null;
									ts.cancel();
									ts = null;

								}
							}
						};
						timer = new Timer(true);
						timer.schedule(ts, 0, 13000);
					}
				});
	}

	private void initlayout25() {
		showDate = (Button) inflate25.findViewById(R.id.showDate);
		showDate.setOnClickListener(new OnClickListener() {

			private TimerTask ts;
			private Timer timer;
			private Handler handler1;

			@Override
			public void onClick(View v) {

				handler1 = new Handler() {
					@Override
					public void handleMessage(Message msg) {
						// TODO Auto-generated method stub
						super.handleMessage(msg);
						if (msg.what == 1) {
							// nxt2();
							if (timer != null) {
								timer.cancel();
								timer = null;
								ts.cancel();
								ts = null;
							}
						}
					}
				};
				// Runnable runnable1 = new Runnable() {
				// @Override
				// public void run() {
				// nxt2();
				// handler1.postDelayed(this, 500);
				// }
				// };
				nxt1();
				ts = new TimerTask() {

					@Override
					public void run() {
						Message msg = new Message();
						msg.what = 1;
						handler1.sendEmptyMessage(msg.what);
					}
				};
				timer = new Timer(true);
				timer.schedule(ts, 500, 500);
			}
		});
	}

	private void initlayout26() {
		inflate26.findViewById(R.id.yijianwancheng).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						act6();
						Log.e("ontoch~~~~~~~", "act6");
					}
				});
	}

	private void initlayout20() {
		layout_comput = (LinearLayout) inflate20
				.findViewById(R.id.layout_comput);
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
				viewPager.setCurrentItem(19);
			}
		});
		one_over.setOnClickListener(new OnClickListener() {

			private TimerTask ts;
			private Timer timer;

			@Override
			public void onClick(View v) {
				ts = new TimerTask() {

					@Override
					public void run() {
						act7();
						if (timer != null) {
							timer.cancel();
							timer = null;
							ts.cancel();
							ts = null;
						}
					}
				};
				timer = new Timer(true);
				timer.schedule(ts, 3000, 1000);
			}
		});
	}

	private void initview2() {
		
		inflate22.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Log.i("NumberOneClass", "inflate22clicked");
				if (isConnected()) {
					Log.i("song", "singing");
					if(clickCount==1){
						songev3();
					}
					// do1ev3();
				} else {
					Log.i("song", "buzai");
					// show("小伙伴不在");
				}
			}
		});
		// inflate22.setOnTouchListener(new OnTouchListener() {
		//
		// @Override
		// public boolean onTouch(View v, MotionEvent event) {
		// // if (event.getX() > 400 && event.getX() < 800
		// // && event.getY() > 250) {
		// if (isConnected()) {
		// song();
		// } else {
		// show("小伙伴不在");
		// }
		// }
		// return false;
		// }
		// });
	}

	@Override
	protected void onResume() {
		adapter2.notifyDataSetChanged();
		listView_show.setAdapter(adapter2);
		super.onResume();
	}

	private void initlayout21() {
		inflate21.setOnTouchListener(this);
		Button btn1 = (Button) inflate21.findViewById(R.id.btn1);
		Button btn2 = (Button) inflate21.findViewById(R.id.btn2);
		Button btn3 = (Button) inflate21.findViewById(R.id.btn3);
		listView_show = (ListView) inflate21.findViewById(R.id.listView_show);
		adapter2 = new GListviewAdapter(NumberOneClass.this, arr);
		btn1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent openCameraIntent = new Intent(NumberOneClass.this,
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
				demo2();
			}
		});
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

	private void initlayout17() {
		ontouch ontouch = new ontouch();
		inflate17.findViewById(R.id.hand_btn).setOnTouchListener(ontouch);
		ListView show_Group_action = (ListView) inflate17
				.findViewById(R.id.show_Group_action);
		inflate17.findViewById(R.id.xiaolian_btn).setOnTouchListener(ontouch);
		inflate17.findViewById(R.id.changge_btn).setOnTouchListener(ontouch);
		inflate17.findViewById(R.id.qianjin_btn).setOnTouchListener(ontouch);
		inflate17.findViewById(R.id.group_action).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						String saveList = null;
						// for(int i=0;i<4;i++){
						ts = new TimerTask() {

							@Override
							public void run() {
								act1();
								act2();
								act3();

								act4();
								if (timer != null) {
									timer.cancel();
									timer = null;
									ts.cancel();
									ts = null;
								}
							}
						};
						timer = new Timer(true);
						timer.schedule(ts, 2000, 1000);
						// }

						Toast.makeText(NumberOneClass.this, saveList, 1).show();
					}
				});
		inflate17.findViewById(R.id.hand_btn).setOnClickListener(
				new OnClickListener() {
					private TimerTask ts;
					private Timer timer;

					@Override
					public void onClick(View v) {

						ts = new TimerTask() {

							@Override
							public void run() {
								act1();
								if (timer != null) {
									timer.cancel();
									timer = null;
									ts.cancel();
									ts = null;
								}
							}
						};
						timer = new Timer(true);
						timer.schedule(ts, 2000, 1000);
					}
				});

		inflate17.findViewById(R.id.xiaolian_btn).setOnClickListener(
				new OnClickListener() {

					private Timer timer;
					private TimerTask ts;

					@Override
					public void onClick(View v) {
						ts = new TimerTask() {

							@Override
							public void run() {
								act4();
								if (timer != null) {
									timer.cancel();
									timer = null;
									ts.cancel();
									ts = null;
								}
								Log.e("111", "sssssssss");
							}
						};
						timer = new Timer(true);
						timer.schedule(ts, 2000, 1000);
					}

				});
		inflate17.findViewById(R.id.changge_btn).setOnClickListener(
				new OnClickListener() {

					private TimerTask ts;
					private Timer timer;

					@Override
					public void onClick(View v) {
						ts = new TimerTask() {

							@Override
							public void run() {
								act2();
								if (timer != null) {
									timer.cancel();
									timer = null;
									ts.cancel();
									ts = null;
								}
							}
						};
						timer = new Timer(true);
						timer.schedule(ts, 2000, 1000);
					}

				});
		inflate17.findViewById(R.id.qianjin_btn).setOnClickListener(
				new OnClickListener() {

					private TimerTask ts;
					private Timer timer;

					@Override
					public void onClick(View v) {
						ts = new TimerTask() {

							@Override
							public void run() {
								act3();
								if (timer != null) {
									timer.cancel();
									timer = null;
									ts.cancel();
									ts = null;
								}
							}
						};
						timer = new Timer(true);
						timer.schedule(ts, 2000, 1000);
					}
				});

	}

	private void initlayout16() {
		inflate16.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra("showMode", 2);
				intent.setComponent(new ComponentName("org.giles.ui",
						"org.giles.ui.MainActivity"));
				startActivity(intent);
			}
		});

	}

	private void initview13() {
		inflate13.setOnTouchListener(new Judgelistener());
	}

	private void initview8() {
		inflate8.setOnTouchListener(new Judgelistener());
	}

	private void initview7() {
		inflate7.setOnTouchListener(new Judgelistener());
	}

	private void initview5() {
		inflate5.setOnTouchListener(new Judgelistener());
	}

	private void initInfaate() {
		Button hello_btn = (Button) inflate9.findViewById(R.id.hello_btn);
		hello_btn.setOnClickListener(new OnClickListener() {
			private Timer timer;
			private TimerTask ts;

			@Override
			public void onClick(View arg0) {
				ts = new TimerTask() {
					@Override
					public void run() {
						act5();
						if (timer != null) {
							timer.cancel();
							timer = null;
							ts.cancel();
							ts = null;
						}
					}
				};
				timer = new Timer(true);
				timer.schedule(ts, 3000, 1000);

			}
		});
	}

	private void initview4() {
		inflate4.setOnTouchListener(new Judgelistener());
	}

	private class Judgelistener implements OnTouchListener {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getX() > 600 * screenWidth / 960
					&& event.getX() < 700 * screenWidth / 960
					&& event.getY() > 370 * screenHeight / 540
					&& event.getY() < 450 * screenHeight / 540) {
				startActivity(new Intent(NumberOneClass.this, WinClass.class));
				overridePendingTransition(R.anim.right_out, R.anim.hold);
			} else if (event.getX() > 795 * screenWidth / 960
					&& event.getX() < 876 * screenWidth / 960
					&& event.getY() > 370 * screenHeight / 540
					&& event.getY() < 450 * screenHeight / 540) {
				Intent intent = new Intent(NumberOneClass.this,
						ErrorClass.class);
				intent.putExtra("errornum", 1);
				startActivity(intent);
				overridePendingTransition(R.anim.right_out, R.anim.hold);
			}
			return false;
		}
	}

	private void galleryInitToOnclick() {
		infalte12.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra("showMode", 1);
				intent.setComponent(new ComponentName("org.giles.ui",
						"org.giles.ui.MainActivity"));
				startActivity(intent);
			}
		});
	}

	public boolean isStartMedio = false;

	private void initview3() {
		LinearLayout LinearLayout_id = (LinearLayout) inflate3
				.findViewById(R.id.LinearLayout_id);
		LinearLayout_id.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getX() > 100 * screenWidth / 960
						&& event.getX() < 470 * screenWidth / 960
						&& event.getY() > 200 * screenHeight / 540
						&& event.getY() < 400 * screenHeight / 540) {
					String url = Environment
							.getExternalStorageDirectory().getPath()
							+ "/a_3.mp4";
					Intent intent = new Intent(NumberOneClass.this,
							videoplayer.class);
					// intent.setDataAndType(uri, "video/mp4");
					intent.putExtra("url", url);
					startActivity(intent);

				} else if (event.getX() > 537 * screenWidth / 960
						&& event.getX() < 904 * screenWidth / 960
						&& event.getY() > 200 * screenHeight / 540
						&& event.getY() < 400 * screenHeight / 540) {
					String url = Environment
							.getExternalStorageDirectory().getPath()
							+ "/a_3.mp4";
					// intent.setDataAndType(uri, "video/mp4");
					Intent intent = new Intent(NumberOneClass.this,
							videoplayer.class);
					intent.putExtra("url", url);
					startActivity(intent);
				}
				return false;
			}
		});
	}

	public int onclickNumber = 0;
	private View inflate3;
	private View gallery_main;
	private ArrayList<Map<String, Integer>> all;
	private ImageSwitcher imageSwitcher;
	private SimpleAdapter adapter;
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
	private View inflate21;
	private View inflate24;
	
	
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
					TextView konew_text1 = (TextView) layout12
							.findViewById(R.id.konew_text1);
					konew_text1.setVisibility(View.VISIBLE);
					return;
				} else if (onclickNumber == 2) {
					TextView konew_text2 = (TextView) layout12
							.findViewById(R.id.konew_text2);
					konew_text2.setVisibility(View.VISIBLE);
					return;
				} else {
					return;
				}
			}
		});
	}

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
			// return Integer.MAX_VALUE;
			return list.size();
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
				((ViewPager) arg0).addView(list.get(arg1));
			} catch (Exception e) {
			}
			// return list.get(arg1%list.size());
			if (arg1 == list.size()) {
				NumberOneClass.this.finish();
				viewPager.destroyDrawingCache();
				Toast.makeText(getBaseContext(), "亲 第一个课时结束了",
						Toast.LENGTH_LONG).show();
				overridePendingTransition(R.anim.slide_left, R.anim.hold);
				return null;
				// }else if (arg1 > list.size()+1) {
				//
				// return null;
			} else {
				return list.get(arg1);
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
		}

		// 当新的页面被选中时调用
		@Override
		public void onPageSelected(int arg0) {
			// Log.e("onPageSelected", "onPageSelected");
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
			if (arg0 == 23) {
				nxt1();
				handler.postDelayed(runnable, 500);
			}
			if (arg0 == 24) {
				handler.postDelayed(runnable, 500);
				nxt3();
			}

			Log.e("-------------", "当前是第" + c_id + "页");
		}

	}

	private float x, y;
	private int mx, my;
	private int screenWidth;
	private int screenHeight;
	private int lastX, lastY;

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
	private int clickCount = 0;
	private long firstClick = 0;
	private long lastClick = 0;
	private int CLICK_DELAY = 1000;

	@Override
	public boolean onTouch(View v, MotionEvent event) {

		if (MotionEvent.ACTION_DOWN == event.getAction()) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				// 如果第二次点击 距离第一次点击时间过长 那么将第二次点击看为第一次点击
				Log.i(TAG, "clickCount:" + clickCount);
				if (firstClick != 0
						&& System.currentTimeMillis() - firstClick > 500) {
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
						reverse();
					}
				}
			}
		}

		// int ea = event.getAction();
		// Log.i("TAG", "Touch:" + ea);

		// Toast.makeText(DraftTest.this, "位置："+x+","+y,
		// Toast.LENGTH_SHORT).show();

		// switch (ea) {
		// case MotionEvent.ACTION_DOWN:
		// lastX = (int) event.getRawX();
		// lastY = (int) event.getRawY();
		// if (event.getRawX() > 1050 && event.getRawX() < 1700
		// && event.getRawY() > 250 && event.getRawY() < 1000) {
		// for (int i = 0; i < arrayList.size(); i++) {
		// if (arrayList.get(i) == v.getId()) {
		// arrayList.remove(i);
		// Toast.makeText(this, "删除了" + v.getId(), Toast.LENGTH_SHORT).show();
		// }
		// }
		// }

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

		// if (event.getX() < sx(15)) {
		// if (event.getY() < sy(15)) {
		// // 搜索蓝牙
		// // selectNXT();
		// } else {
		// if (index > 1) {
		// index--;
		// setBitmap();
		// }
		// }
		// }
		// if (event.getX() > sx(85)) {
		// if (event.getY() < sy(15)) {
		// // // 扫描蓝牙
		// // Intent openCameraIntent = new Intent(this,
		// // CaptureActivity.class);
		// // startActivityForResult(openCameraIntent, REQUEST_ENABLE_QR);
		// } else {
		// if (index < 30) {
		// index++;
		// setBitmap();
		// }
		// }
		// }
		// break;
		// /**
		// * layout(l,t,r,b) l Left position, relative to parent t Top position,
		// * relative to parent r Right position, relative to parent b Bottom
		// * position, relative to parent
		// * */
		// case MotionEvent.ACTION_MOVE:
		// int dx = (int) event.getRawX() - lastX;
		// int dy = (int) event.getRawY() - lastY;
		//
		// int left = v.getLeft() + dx;
		// int top = v.getTop() + dy;
		// int right = v.getRight() + dx;
		// int bottom = v.getBottom() + dy;
		//
		// if (left < 0) {
		// left = 0;
		// right = left + v.getWidth();
		// }
		//
		// if (right > screenWidth) {
		// right = screenWidth;
		// left = right - v.getWidth();
		// }
		//
		// if (top < 0) {
		// top = 0;
		// bottom = top + v.getHeight();
		// }
		//
		// if (bottom > screenHeight) {
		// bottom = screenHeight;
		// top = bottom - v.getHeight();
		// }
		//
		// v.layout(left, top, right, bottom);
		//
		// Log.i("", "position：" + left + ", " + top + ", " + right + ", "
		// + bottom);
		//
		// lastX = (int) event.getRawX();
		// lastY = (int) event.getRawY();
		// if (event.getRawX() > 1050 && event.getRawX() < 1700
		// && event.getRawY() > 250 && event.getRawY() < 1000) {
		// arrayList.add(v.getId());
		// Toast.makeText(this, "添加了" + v.getId(), Toast.LENGTH_SHORT).show();
		// }
		// break;
		// case MotionEvent.ACTION_UP:
		// break;
		// }
		return false;
	}

	// public void song() {
	// // 中1DO 523 中2RE 578 中3MI 659 中4FA 698
	// // 中5SO 784 中6LA 880 中7SI 988 100 1/4 400 -1
	// sendBTCmessage(BTCommunicator.NO_DELAY, BTCommunicator.DO_BEEP, 523,
	// 200);
	// sendBTCmessage(300, BTCommunicator.DO_BEEP, 523, 200);
	// sendBTCmessage(600, BTCommunicator.DO_BEEP, 784, 200);
	// sendBTCmessage(900, BTCommunicator.DO_BEEP, 784, 200);
	// sendBTCmessage(1200, BTCommunicator.DO_BEEP, 880, 200);
	// sendBTCmessage(1500, BTCommunicator.DO_BEEP, 880, 200);
	// sendBTCmessage(1800, BTCommunicator.DO_BEEP, 784, 400);
	//
	// sendBTCmessage(2300, BTCommunicator.DO_BEEP, 698, 200);
	// sendBTCmessage(2600, BTCommunicator.DO_BEEP, 698, 200);
	// sendBTCmessage(2900, BTCommunicator.DO_BEEP, 659, 200);
	// sendBTCmessage(3200, BTCommunicator.DO_BEEP, 659, 200);
	// sendBTCmessage(3500, BTCommunicator.DO_BEEP, 578, 200);
	// sendBTCmessage(3800, BTCommunicator.DO_BEEP, 578, 200);
	// sendBTCmessage(4100, BTCommunicator.DO_BEEP, 523, 400);
	//
	// }

	public void songev3() {
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

	@Override
	public void onClick(View v) {

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
					arr.add(scanResult);
				}
			}
			break;
		// will not be called now, since the check intent is not generated
		/*
		 * case TTS_CHECK_CODE: if (resultCode ==
		 * TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) { // success, create the
		 * TTS instance mTts = new TextToSpeech(this, this); } else { // missing
		 * data, install it Intent installIntent = new Intent();
		 * installIntent.setAction(
		 * TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
		 * startActivity(installIntent); }
		 * 
		 * break;
		 */
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
			switch (myMessage.getData().getInt("message")) {
			case BTCommunicator.DISPLAY_TOAST:
				Toast.makeText(getBaseContext(),
						"" + myMessage.getData().getString("toastText"),
						Toast.LENGTH_SHORT).show();
				break;
			case BTCommunicator.STATE_CONNECTED:
				connectingProgressDialog.dismiss();
				reusableToast.makeText(getApplicationContext(), "你可以和小伙伴互动啦",
						Toast.LENGTH_LONG).show();
				connected = true;
				// Vibrator mVibrator =
				// (Vibrator)getSystemService(VIBRATOR_SERVICE);
				// mVibrator.vibrate(1000);
				// sendBTCmessage(BTCommunicator.NO_DELAY,
				// BTCommunicator.DO_BEEP, 500, 1000);
				break;
			case BTCommunicator.PROGRAM_NAME:
				if (myBTCommunicator != null) {
					byte[] returnMessage = myBTCommunicator.getReturnMessage();
					startRXEprogram(returnMessage[2]);
				}

				break;
			case BTCommunicator.GET_VALUE:
				byte[] sensorMessage = myBTCommunicator.getReturnMessage();
				int sensorValue = 0;
				if (sensorMessage[10] > 0)
					sensorValue = sensorMessage[10] + sensorMessage[11] * 256;
				else
					sensorValue = sensorMessage[10] + sensorMessage[11] * 256
							+ 256;
				nxtValue(sensorValue / 10);
				break;
			case BTCommunicator.MOTOR_STATE:

				if (myBTCommunicator != null) {
					byte[] motorMessage = myBTCommunicator.getReturnMessage();
					int position = byteToInt(motorMessage[21])
							+ (byteToInt(motorMessage[22]) << 8)
							+ (byteToInt(motorMessage[23]) << 16)
							+ (byteToInt(motorMessage[24]) << 24);
					Toast.makeText(getBaseContext(),
							"" + "Current position: " + position,
							Toast.LENGTH_SHORT).show();
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
				/*
				 * if (btErrorPending == false) { btErrorPending = true; //
				 * inform the user of the error with an AlertDialog
				 * AlertDialog.Builder builder = new
				 * AlertDialog.Builder(thisActivity);
				 * builder.setTitle(getResources
				 * ().getString(R.string.bt_error_dialog_title))
				 * .setMessage(getResources
				 * ().getString(R.string.bt_error_dialog_message
				 * )).setCancelable(false) .setPositiveButton("OK", new
				 * DialogInterface.OnClickListener() {
				 * 
				 * @Override public void onClick(DialogInterface dialog, int id)
				 * { btErrorPending = false; dialog.cancel(); selectNXT(); } });
				 * builder.create().show();
				 * 
				 * }
				 */

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
		if (status == 0x00) {
			sendBTCmessage(BTCommunicator.NO_DELAY,
					BTCommunicator.STOP_PROGRAM, 0, 0);
			sendBTCmessage(200, BTCommunicator.START_PROGRAM, programToStart);
		} else {
			sendBTCmessage(BTCommunicator.NO_DELAY,
					BTCommunicator.START_PROGRAM, programToStart);
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

	public void startProgram(String name) {
		// for .rxe programs: get program name, eventually stop this and start
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

	void act1() {
		// Toast.makeText(NumberOneClass.this, "举手", Toast.LENGTH_SHORT).show();
		// show("举手");
		startProgram("demo001.rxe");
	}

	void act2() {
		// Toast.makeText(NumberOneClass.this, "唱歌", Toast.LENGTH_SHORT).show();
		startProgram("demo002.rxe");
	}

	void act3() {
		// Toast.makeText(NumberOneClass.this, "前进", Toast.LENGTH_SHORT).show();
		startProgram("demo003.rxe");
	}

	void act4() {
		// Toast.makeText(NumberOneClass.this, "笑脸", Toast.LENGTH_SHORT).show();
		startProgram("demo004.rxe");
	}

	void act5() {
		// Toast.makeText(NumberOneClass.this, "打招呼",
		// Toast.LENGTH_SHORT).show();
		startProgram("demo006.rxe");
	}

	void act7() {
		// Toast.makeText(NumberOneClass.this, "打招呼",
		// Toast.LENGTH_SHORT).show();
		startProgram("demo014.rxe");
	}

	void act6() {
		// Toast.makeText(NumberOneClass.this, "一键完成",
		// Toast.LENGTH_SHORT).show();
		startProgram("demo014.rxe");
	}

	void do1ev3() {
		// 前进1秒
		sendBTCmessage(BTCommunicator.NO_DELAY, BTCommunicator.EV3_MOTOR,
				BTCommunicator.EV3_MOTOR_B, 75);
		sendBTCmessage(BTCommunicator.NO_DELAY, BTCommunicator.EV3_MOTOR,
				BTCommunicator.EV3_MOTOR_C, 75);
		sendBTCmessage(5000, BTCommunicator.EV3_MOTOR,
				BTCommunicator.EV3_MOTOR_B, 0);
		sendBTCmessage(5000, BTCommunicator.EV3_MOTOR,
				BTCommunicator.EV3_MOTOR_C, 0);
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

	// void demo2() {
	// Toast.makeText(NumberOneClass.this, "执行卡程序", Toast.LENGTH_SHORT).show();
	// ts = new TimerTask() {
	//
	// @Override
	// public void run() {
	// do3();
	// if (timer2 != null) {
	// timer2.cancel();
	// timer2 = null;
	// ts.cancel();
	// ts = null;
	// }
	// }
	// };
	// timer2 = new Timer(true);
	// timer2.schedule(ts, 1000, 1000);
	// if (arr.size() != 0) {
	// for (int i = 0; i < arr.size(); i++) {
	// if (arr.get(i).equals("CARD-00:01")) {
	// do1();
	// } else if (arr.get(i).equals("CARD-00:02")) {
	// do2();
	// } else if (arr.get(i).equals("CARD-00:03")) {
	//
	// } else if (arr.get(i).equals("CARD-00:04")) {
	// ts = new TimerTask() {
	//
	// @Override
	// public void run() {
	// if (arr.get(0).equals("CARD-00:01")) {
	// do1();
	// } else if (arr.get(0).equals("CARD-00:02")) {
	// do2();
	// }
	// if (timer2 != null) {
	// timer2.cancel();
	// timer2 = null;
	// ts.cancel();
	// ts = null;
	// }
	// }
	// };
	// timer2 = new Timer(true);
	// timer2.schedule(ts, 0, 1000);
	// } else if (arr.get(i).equals("CARD-00:05")) {
	// ts = new TimerTask() {
	//
	// @Override
	// public void run() {
	// if (arr.get(0).equals("CARD-00:01")) {
	// do1();
	// } else if (arr.get(0).equals("CARD-00:02")) {
	// do2();
	// }
	// if (timer2 != null) {
	// timer2.cancel();
	// timer2 = null;
	// ts.cancel();
	// ts = null;
	// }
	// }
	// };
	// timer2 = new Timer(true);
	// timer2.schedule(ts, 0, 1000);
	//
	// } else if (arr.get(i).equals("CARD-00:06")) {
	// do3();
	// }
	// }
	// }
	// }
	public static int savaIndex = 0;
	public Thread t;

	void demo2() {
		Toast.makeText(NumberOneClass.this, "执行卡程序", Toast.LENGTH_SHORT).show();
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

	void demo3() {
		// Toast.makeText(NumberOneClass.this, "传感器读数",
		// Toast.LENGTH_SHORT).show();
		// 开灯
		// 循环测量读数，周期0.5秒，直到翻页
		// 关灯
	}

	void nxt1() {
		// Toast.makeText(NumberOneClass.this, "开灯", Toast.LENGTH_SHORT).show();
		sendBTCmessage(BTCommunicator.NO_DELAY, BTCommunicator.LIGHT_ON, 0, 0);
	}

	void nxt2() {
		// 测量
		sendBTCmessage(BTCommunicator.NO_DELAY, BTCommunicator.LIGHT_ON, 0, 0);
		sendBTCmessage(100, BTCommunicator.GET_VALUE, 0, 0);
	}

	void nxt3() {
		// Toast.makeText(NumberOneClass.this, "关灯", Toast.LENGTH_SHORT).show();
		sendBTCmessage(BTCommunicator.NO_DELAY, BTCommunicator.LIGHT_OFF, 0, 0);
	}

	void nxtValue(int sensorValue) {
		// showToast("传感器:"+sensorValue, Toast.LENGTH_SHORT);
		// 显示大字读数
		showDate.setText(String.valueOf(sensorValue));
	}

	Handler handler = new Handler();
	Runnable runnable = new Runnable() {
		@Override
		public void run() {
			nxt2();
			handler.postDelayed(this, 500);
		}
	};
	private View inflate20;
	private Button computer;
	private Button shua_ka;
	private Button one_over;
	private ViewPager viewPager;
	private View inflate26;
	private View inflate25;
	private String scanResult;
	private ListView listView_show;
	private GListviewAdapter adapter2;
	private TimerTask ts;
	private Timer timer;
	private LinearLayout layout_comput;
	private ImageView com_img;
	private Button showDate;
	private View inflate31;
	private Timer timer2;
	private long currentTimeMillis;

	int sx(int x) {
		return (int) (dx * x);
	}

	int sy(int y) {
		return (int) (dy * y);
	}

	void selectNXT() {
		Intent serverIntent = new Intent(NumberOneClass.this,
				DeviceListActivity.class);
		startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
	}

	public void setBitmap() {
		String fn = String.format(Environment.getExternalStorageDirectory()
				.getPath() + "/zhuxiang/t%02d.jpg", index);
		ImageView robit_img = (ImageView) layout12.findViewById(R.id.robit_img);
		robit_img.setImageBitmap(getLoacalBitmap(fn));
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

	@Override
	public boolean isPairing() {
		// TODO Auto-generated method stub
		return false;
	}

}
