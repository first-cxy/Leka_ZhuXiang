package com.example.zhuxiangrobitclass.ui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Fragment;
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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.zhuxiangrobitclass.mApplication;
import com.example.zhuxiangrobitclass.adpter.ShuaKaListviewAdapter;
import com.example.zhuxiangrobitclass.ui.base.BaseActivity;
import com.example.zhuxiangrobitclass.util.ScreenObserver;
import com.example.zhuxiangrobitclass.util.ScreenObserver.ScreenStateListener;
import com.example.zhuxiangrobitclass.util.Utils;
import com.example.zhuxiangrobitclass.zxing.BTCommunicator;
import com.example.zhuxiangrobitclass.zxing.BTConnectable;
import com.example.zhuxiangrobitclass.zxing.DeviceListActivity;
import com.example.zhuxiangrobitclass.zxing.activity.CaptureActivity;
import com.example.zhuxiangrobitclass1.R;
//import com.example.zhuxiangrobitclass.zxing.LCPMessage;

public class FragmentLeShuaNumberOneClass extends Fragment implements
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
	private Handler startHandler;

	private ScreenObserver screenObserver;  
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		mApplication = (com.example.zhuxiangrobitclass.mApplication) getActivity().getApplication();
//		SimpleAdapter adapter = new SimpleAdapter(getActivity(), all,
//				R.layout.layout_item, new String[] { "image" },
//				new int[] { R.id.imageView });
		inflater = getActivity().getLayoutInflater();
		createBTCommunicator();
		
		LayoutInflater inflater2 =  getActivity().getLayoutInflater();
		list = new ArrayList<View>();
		inflate21 = inflater2.inflate(R.layout.layout21, null);
		initlayout21();
		list.add(inflate21);
		
		vs = new View[list.size()];
		ViewGroup main = (ViewGroup) inflater.inflate(
				R.layout.classnumberone_main, null);

		ViewGroup group = (ViewGroup) main.findViewById(R.id.viewGroup);
		rlLevel3 = (RelativeLayout) main.findViewById(R.id.rl_level3);
		rlLevel2 = (RelativeLayout) main.findViewById(R.id.rl_level2);
		rlLevel32 = (RelativeLayout) main.findViewById(R.id.rl_level1);
		viewPager = (ViewPager) main.findViewById(R.id.vPager);
		
		ImageButton ib_home = (ImageButton)main.findViewById(R.id.ib_home);
		ib_home.setOnClickListener(this);
		
		ImageButton ib_menu = (ImageButton)main.findViewById(R.id.ib_menu);
		ib_menu.setOnClickListener(this);
		
		ImageButton Connection_Robit = (ImageButton)main.findViewById(R.id.Connection_Robit);
		Connection_Robit.setOnClickListener(this);
		
		ImageButton shaomiaoLanya = (ImageButton)main.findViewById(R.id.shaomiaoLanya);
		shaomiaoLanya.setOnClickListener(this);
		
		
		
		for (int i = 0; i < list.size(); i++) {
			View v = new View(getActivity());
			v.setLayoutParams(new LayoutParams(12, 12));
			vs[i] = v;
			// if (i == 0) {
			//
			// imageViews[i].setBackgroundResource(R.drawable.guide_dot_white);
			// } else {
			// imageViews[i].setBackgroundResource(R.drawable.guide_dot_black);
			group.addView(v);
		}
		
		viewPager.setAdapter(new MyAdapter());
		viewPager.setCurrentItem(300);
		viewPager.setCurrentItem(0);
		
		
		dis = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dis);
		// show("屏幕分辨率为:" + dis.widthPixels + " * " + dis.heightPixels);
		Display mDisplay = getActivity().getWindowManager().getDefaultDisplay();
		dx = mDisplay.getWidth() / 100.0;
		dy = mDisplay.getHeight() / 100.0;
		index = 0;
		startHandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				//
				// Bundle myBundle = new Bundle();
				// myBundle.putInt("message", BTCommunicator.EV3_BEEP);
				// myBundle.getInt("value1", msg.getData().getInt("value1"));
				// myBundle.putInt("value2", msg.getData().getInt("value2"));
				// Message myMessage = myHandler.obtainMessage();
				// myMessage.setData(myBundle);
				// btCommunicatorHandler.sendMessage(myMessage);

				name = (String) msg.obj;
				subSequence = name.subSequence(0, 3).toString();
				if (mApplication.getDeviceType().equals("ev3")) {
					Log.i("startHandler", "_____startev3startProgram__" + name);
					ev3startProgram(1, subSequence, name + ".rbf");
				} else {
					Log.i("startHandler", "startProgram" + name + ".rxe");
					// nxtstartProgram(1, subSequence, name + ".rxe");
					startProgram(name + ".rxe");
				}
			}
		};
		
		 screenObserver = new ScreenObserver(getActivity());  
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
		
		
		
		return main;
	}

	
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
		case R.id.Connection_Robit:
			selectNXT();
			if (index > 1) {
				index--;
				setBitmap();
			}
			break;
		case R.id.shaomiaoLanya:
			// 扫描蓝牙
			Intent openCameraIntent = new Intent(getActivity(), CaptureActivity.class);
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
	

	private void doSomethingOnScreenOn() {  
        Log.i(TAG, "Screen is on");  
    }  
  
    private void doSomethingOnScreenOff() {  
        Log.i(TAG, "Screen is off"); 
        destroyBTCommunicator();
    }  
	
	
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //相当于Fragment的onResume
        	adapter2.notifyDataSetChanged();
    		listView_show.setAdapter(adapter2);
        } else {
            //相当于Fragment的onPause
        }
    }
	



	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		destroyBTCommunicator();
		screenObserver.stopScreenStateUpdate();  
	}



	private void initlayout21() {
		Button btn1 = (Button) inflate21.findViewById(R.id.btn1);
		Button btn2 = (Button) inflate21.findViewById(R.id.btn2);
		Button btn3 = (Button) inflate21.findViewById(R.id.btn3);
		listView_show = (ListView) inflate21.findViewById(R.id.listView_show);
		adapter2 = new ShuaKaListviewAdapter(getActivity(), arr);
		btn1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// ev3startProgram(0,"Project","A0101.rpf");
				// song();
				Intent openCameraIntent = new Intent(getActivity(),
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
				// song();
				i = 0;// 从0开始运行
				try {
					RunLekaImage();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		});
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
		startHandler.sendMessageAtTime(msg, 1000);
		Log.i("RunLekaImage", "运行图片第" + i + "张");

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
					}
				}
			}
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
					Intent openCameraIntent = new Intent(getActivity(),
							CaptureActivity.class);
					startActivityForResult(openCameraIntent, REQUEST_ENABLE_QR);
				} else {
					if (index < 30) {
						index++;
						setBitmap();
					}
				}
			}
			
			
			
			
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
	private String address = null;

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case REQUEST_CONNECT_DEVICE:

			// When DeviceListActivity returns with a device to connect
			if (resultCode == Activity.RESULT_OK) {
				// Get the device MAC address and start a new bt communicator
				// thread
				address = data.getExtras().getString(
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
//				finish();
				break;
			default:
				// showToast("problem_at_connecting", Toast.LENGTH_SHORT);
//				finish();
				break;
			}

			break;
		case REQUEST_ENABLE_QR:
			if (resultCode == Activity.RESULT_OK) {
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
							Intent intent = new Intent(getActivity(),
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
	private BTCommunicator btCommunicator = null;
	private boolean connected = false;
	private Handler btCommunicatorHandler;
	private Toast reusableToast;

	private void startBTCommunicator(String mac_address) {
//		if (!connected) {
			connectingProgressDialog = ProgressDialog.show(getActivity(), "", "和小伙伴交流中",
					true);
			if (btCommunicator != null) {
				try {
					btCommunicator.destroyNXTconnection();
				} catch (IOException e) {
				}
			}
			createBTCommunicator();
			btCommunicator.setMACAddress(mac_address);
			btCommunicator.start();
//		}
	}

	/**
	 * Creates a new object for communication to the NXT robot via bluetooth and
	 * fetches the corresponding handler.
	 */
	private void createBTCommunicator() {
		// interestingly BT adapter needs to be obtained by the UI thread - so
		// we pass it in in the constructor
		btCommunicator = new BTCommunicator(this, messageHandler,
				BluetoothAdapter.getDefaultAdapter(), getResources());
		btCommunicatorHandler = btCommunicator.getHandler();
	}

	/**
	 * Receive messages from the BTCommunicator
	 */
	final Handler messageHandler = new Handler() {

		@Override
		public void handleMessage(Message myMessage) {
			Log.i("switch", ":" + myMessage.getData().getInt("message"));
			switch (myMessage.getData().getInt("message")) {
			case BTCommunicator.DISPLAY_TOAST:
				Log.i("BTCommunicator.DISPLAY_TOAST", ":"
						+ myMessage.getData().getInt("message"));
				Toast.makeText(getActivity(), myMessage.getData().getString("toastText"), Toast.LENGTH_SHORT).show();;
				break;
			case BTCommunicator.STATE_CONNECTED:
				Log.i("BTCommunicator.STATE_CONNECTED", ":"
						+ myMessage.getData().getInt("message"));
				connectingProgressDialog.dismiss();
				reusableToast.makeText(getActivity(), "你可以和小伙伴互动啦",
						Toast.LENGTH_LONG).show();
				connected = true;
				break;
			case BTCommunicator.PROGRAM_NAME:
				Log.i("BTCommunicator.PROGRAM_NAME", ":"
						+ myMessage.getData().getInt("message"));
				if (btCommunicator != null) {
					byte[] returnMessage = btCommunicator.getReturnMessage();
					startRXEprogram(returnMessage[2]);
				}

				break;
			case BTCommunicator.GET_VALUE:
				Log.i("BTCommunicator.GET_VALUE", ":"
						+ myMessage.getData().getInt("message"));
				byte[] sensorMessage = btCommunicator.getReturnMessage();
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
				infoMessages = btCommunicator.getReturnMessage();
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
							startHandler.sendMessageAtTime(msg, 2000);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						i++;
					}
				} else if (infoMessages[3] == 1) {

				}

				break;
			case BTCommunicator.EV3_BEEP:
				Message msg = new Message();

				msg.obj = name;
				Bundle myBundle = new Bundle();
				myBundle.putInt("message", BTCommunicator.EV3_BEEP);
				myBundle.putInt("value1", 698);
				myBundle.putInt("value2", 200);
				msg.setData(myBundle);

				startHandler.sendMessageAtTime(msg, 2000);
				break;
			// case LCPMessage.STOP_PROGRAM:
			// if (mApplication.getDeviceType().equals("nxt")) {
			// infoMessages = myBTCommunicator.getReturnMessage();
			// Log.i("LeShua", infoMessages.toString());
			// try {
			// if (i < arr.size()) {
			// JSONObject json = new JSONObject(arr.get(i)
			// .toString()).getJSONObject("leka");
			// name = json.getString("name");
			// startProgram(name+".rxe");
			// i++;
			// }
			// } catch (JSONException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			// } else {
			// }
			// break;

			// case LCPMessage.START_PROGRAM:
			// Log.i("nxtstartProgram", "_____start6__");
			// if (mApplication.getDeviceType().equals("nxt")) {
			// try {
			// if (i < arr.size()) {
			// JSONObject json = new JSONObject(arr.get(i)
			// .toString()).getJSONObject("leka");
			// name = json.getString("name");
			// Log.i("name", name);
			// startProgram(name+".rxe");
			// i++;
			// }
			// } catch (JSONException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			//
			// } else {
			// }
			// break;
			// case LCPMessage.EV3_INFOPROGRAM:
			// handler2.removeCallbacks(runnable2);
			// do3_nxt();
			// Log.i("LCPMessage.EV3_INFOPROGRAM",
			// "__________LCPMessage.EV3_INFOPROGRAM");
			// try {
			// if (i < arr.size()) {
			// JSONObject json = new JSONObject(arr.get(i)
			// .toString()).getJSONObject("leka");
			// name = json.getString("name");
			// Log.i("name", name);
			// startProgram(name+".rxe");
			// i++;
			// }
			// } catch (JSONException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			//
			// break;
			case BTCommunicator.MOTOR_STATE:
				Log.i("BTCommunicator.MOTOR_STATE", ":"
						+ myMessage.getData().getInt("message"));
				if (btCommunicator != null) {
					byte[] motorMessage = btCommunicator.getReturnMessage();
					int position = byteToInt(motorMessage[21])
							+ (byteToInt(motorMessage[22]) << 8)
							+ (byteToInt(motorMessage[23]) << 16)
							+ (byteToInt(motorMessage[24]) << 24);
					Toast.makeText(getActivity(), "Current position: " + position, Toast.LENGTH_SHORT).show();
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
				reusableToast.makeText(getActivity(),
						"STATE_CONNECTERROR_PAIRING", Toast.LENGTH_LONG).show();
				destroyBTCommunicator();
				connectingProgressDialog.dismiss();
				break;

			case BTCommunicator.STATE_CONNECTERROR:
				reusableToast.makeText(getActivity(), "小伙伴不理你",
						Toast.LENGTH_LONG).show();
				connectingProgressDialog.dismiss();
				break;
			case BTCommunicator.STATE_RECEIVEERROR:
			case BTCommunicator.STATE_SENDERROR:
				reusableToast.makeText(getActivity(), "小伙伴离开了",
						Toast.LENGTH_LONG).show();
				destroyBTCommunicator();

				break;

			case BTCommunicator.VIBRATE_PHONE:
				if (btCommunicator != null) {
					byte[] vibrateMessage = btCommunicator.getReturnMessage();
					Vibrator myVibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
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
		address = null;
		Log.i(TAG, "destroyBTCommunicator");
		if (btCommunicator != null) {
			sendBTCmessage(BTCommunicator.NO_DELAY, BTCommunicator.DISCONNECT,
					0, 0);
			btCommunicator = null;
		}

		connected = false;
	}

	void sendBTCmessage(int delay, int message, int value1, int value2) {
		Bundle myBundle = new Bundle();
		myBundle.putInt("message", message);
		myBundle.putInt("value1", value1);
		myBundle.putInt("value2", value2);
		Message myMessage = messageHandler.obtainMessage();
		myMessage.setData(myBundle);

		if (delay == 0)
			btCommunicatorHandler.sendMessage(myMessage);

		else
			btCommunicatorHandler.sendMessageDelayed(myMessage, delay);
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
		Message myMessage = messageHandler.obtainMessage();
		myMessage.setData(myBundle);

		if (delay == 0)
			btCommunicatorHandler.sendMessage(myMessage);
		else
			btCommunicatorHandler.sendMessageDelayed(myMessage, delay);
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
		Toast.makeText(getActivity(), "执行卡程序", Toast.LENGTH_SHORT)
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

	int sx(int x) {
		return (int) (dx * x);
	}

	int sy(int y) {
		return (int) (dy * y);
	}

	void selectNXT() {
		Intent serverIntent = new Intent(getActivity(),
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
		Message myMessage = messageHandler.obtainMessage();
		myMessage.setData(myBundle);
		btCommunicatorHandler.sendMessage(myMessage);
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

		Message myMessage = messageHandler.obtainMessage();
		myMessage.setData(myBundle);
		btCommunicatorHandler.sendMessage(myMessage);
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
