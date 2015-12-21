package com.example.zhuxiangrobitclass.ui;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Timer;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhuxiangrobitclass.mApplication;
import com.example.zhuxiangrobitclass.ui.imageView.PhotoView;
import com.example.zhuxiangrobitclass.zxing.BTCommunicator;
import com.example.zhuxiangrobitclass.zxing.BTConnectable;
import com.example.zhuxiangrobitclass.zxing.DeviceListActivity;
import com.example.zhuxiangrobitclass1.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class ProImagePagerActivity extends Activity implements OnClickListener,
		OnItemClickListener, BTConnectable {
	private ViewPager viewPager;
	private static String TAG = "ProImagePagerActivity";
	private TextView txtcountTextView;
	private Button mShuakaBtn;
	Bitmap defaultbmp;
	DisplayImageOptions options;// jar包声明配置
	protected ImageLoader imageLoader = ImageLoader.getInstance();// jar包声明图片类
	// protected ImageLoader imageLoader ;// jar包声明图片类
	private Button tiaozhuan_btn_id;
	private EditText tiaozhuan_edittext_id;
	private mApplication mApplication;
	private String KemuId;
	private String ClassId;
	private int eventflag = 0;
	private int size;
	private Gallery gallery;
	private HashMap<Integer, ImageView> viewMap;
	private int position;
	private RelativeLayout layout_title;

	private int clickCount = 0;
	private long firstClick = 0;
	private long lastClick = 0;

	private int CLICK_DELAY = 1000;
	private Timer cleanClickTimer = new Timer();
	private boolean hide = true;
	private final static float TARGET_HEAP_UTILIZATION = 0.75f;
	private LayoutInflater inflater;
	private int screenWidth;
	private int screenHeight;
	private float density;
	private CheckBox check_hide;
	private Button btn_robots;
	private int visible = View.VISIBLE;
	private static final int REQUEST_CONNECT_DEVICE = 1000;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mApplication = (com.example.zhuxiangrobitclass.mApplication) getApplication();
		inflater = getLayoutInflater();

		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);

		screenWidth = dm.widthPixels;
		screenHeight = dm.heightPixels;
		density = dm.density;

		defaultbmp = BitmapFactory.decodeResource(getResources(),
				R.drawable.ic_launcher);
		// imageLoader= mApplication.getImageLoader();
		imageLoader.init(ImageLoaderConfiguration
				.createDefault(ProImagePagerActivity.this));

		options = new DisplayImageOptions.Builder()
				.showImageForEmptyUri(R.drawable.ic_empty)
				.showImageOnFail(R.drawable.ic_error).resetViewBeforeLoading()
				.cacheOnDisc().imageScaleType(ImageScaleType.EXACTLY)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.displayer(new FadeInBitmapDisplayer(300)).build();
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		String mImageURL = extras.getString("urlForImage");
		position = extras.getInt("position");

		KemuId = extras.getString("KemuId");
		ClassId = extras.getString("ClassId");
		size = extras.getInt("size");

		// Log.i(TAG, "position" + position );
		setContentView(R.layout.ac_pageview);

		layout_title = (RelativeLayout) findViewById(R.id.layout_title);
		layout_title.setVisibility(View.GONE);

		viewPager = (ViewPager) findViewById(R.id.viewPager);
		gallery = (Gallery) findViewById(R.id.gallery);
		GalleryAdapter galleryAdapter = new GalleryAdapter(getBaseContext(),
				size);
		gallery.setAdapter(galleryAdapter);
		gallery.setVisibility(View.GONE);
		gallery.setSelection(position);
		gallery.setOnItemClickListener(this);

		mShuakaBtn = (Button) findViewById(R.id.GoShaka_tbn_id);
		mShuakaBtn.setOnClickListener(this);
		tiaozhuan_btn_id = (Button) findViewById(R.id.tiaozhuan_btn_id);
		tiaozhuan_btn_id.setOnClickListener(this);
		tiaozhuan_edittext_id = (EditText) findViewById(R.id.tiaozhuan_edittext_id);

		btn_robots = (Button) findViewById(R.id.btn_robots);
		btn_robots.setOnClickListener(this);

		SamplePagerAdapter adapter = new SamplePagerAdapter();
		viewPager.setAdapter(adapter);
		// mViewPager.setCurrentItem(position);
		txtcountTextView = (TextView) findViewById(R.id.txtcount);
		txtcountTextView.setText("1/"
				+ String.valueOf(ClassNumberCategory.mGridViewDate.size()));
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			// 页面更变
			@Override
			public void onPageSelected(int position) {
				Log.i(TAG, "onPageSelected:" + position);
				txtcountTextView.setText(String.valueOf(position + 1)
						+ "/"
						+ String.valueOf(ClassNumberCategory.mGridViewDate
								.size()));
				gallery.setSelection(position);

			}

			@Override
			public void onPageScrollStateChanged(int state) {
				Log.i(TAG, "onPageScrollStateChanged:" + state);
			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				Log.i(TAG, "onPageScrolled:" + position);
				eventflag = 0;
			}
		});
		viewPager.setCurrentItem(position);
		// LinearLayout layout_root = (LinearLayout)
		// findViewById(R.id.layout_root);
		// layout_root.setOnTouchListener(this);
		mApplication = (com.example.zhuxiangrobitclass.mApplication) getApplication();

		check_hide = (CheckBox) findViewById(R.id.check_hide);
		check_hide.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {

				// TODO Auto-generated method stub
				if (isChecked) {
					visible = View.VISIBLE;
				} else {
					visible = View.GONE;
				}
				// viewPager.setCurrentItem(position);
			}
		});

	}

	// 清空状态
	private void clear() {
		clickCount = 0;
		firstClick = 0;
		lastClick = 0;
	}

	private void reverse() {
		if (hide) {
			layout_title.setVisibility(View.VISIBLE);
			gallery.setVisibility(View.VISIBLE);
		} else {
			layout_title.setVisibility(View.GONE);
			gallery.setVisibility(View.GONE);
		}
		hide = !hide;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {

		if (position == pos) {
			// 如果在显示当前图片，再点击，就不再加载。
			return;
		}
		position = pos;
		// imageSwitcher.setImageResource(resIds[position %
		// resIds.length]);
		viewPager.setCurrentItem(position);

	}

	public class GalleryAdapter extends BaseAdapter {
		int galleryItemBackground;
		private Context context;
		private int count;
		private LayoutInflater inflater;

		public GalleryAdapter(Context context, int count) {
			super();
			this.context = context;
			this.count = count;
			viewMap = new HashMap<Integer, ImageView>(count);
			TypedArray typedArray = obtainStyledAttributes(R.styleable.Gallery);
			galleryItemBackground = typedArray.getResourceId(
					R.styleable.Gallery_android_galleryItemBackground, 0);
			inflater = getLayoutInflater();
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return count;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub

			HashMap<String, Object> hashMap = ClassNumberCategory.mGridViewDate
					.get(position);
			final String imgUrl = (String) hashMap.get("URL");

			if (Integer.parseInt(KemuId) > 99) {
				ImageView imageview = new ImageView(context);
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inJustDecodeBounds = false;
				options.inSampleSize = 10;
				imageview.setImageBitmap(BitmapFactory.decodeStream(
						getResources().openRawResource(
								getResources().getIdentifier(imgUrl,
										"drawable", getPackageName())), null,
						options));
				imageview.setScaleType(ImageView.ScaleType.FIT_XY);
				imageview.setLayoutParams(new Gallery.LayoutParams(136, 88));
				imageview.setBackgroundResource(galleryItemBackground);

				return imageview;
			} else {
				View imageLayout = inflater.inflate(R.layout.pro_imageshow,
						parent, false);
				RelativeLayout layout_pager = (RelativeLayout) imageLayout
						.findViewById(R.id.layout_pager);
				layout_pager
						.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
				// View imageLayout = new GalleryItemLayout(context,parent);
				final PhotoView imageview = (PhotoView) imageLayout
						.findViewById(R.id.image);

				imageview.setOnTouchListener(new OnTouchListener() {

					@Override
					public boolean onTouch(View arg0, MotionEvent arg1) {
						// TODO Auto-generated method stub
						return false;
					}
				});

				final ProgressBar spinner = (ProgressBar) imageLayout
						.findViewById(R.id.loading);
				String baseUrl = mApplication.getBaseUrl();
				final String mImgUrl = baseUrl + imgUrl;
				// Log.i(TAG, mImgUrl);
				imageLoader.displayImage(mImgUrl, imageview, options,
						new SimpleImageLoadingListener() {
							@Override
							public void onLoadingStarted(String imageUri,
									View view) {
								spinner.setVisibility(View.VISIBLE);
							}

							@Override
							public void onLoadingFailed(String imageUri,
									View view, FailReason failReason) {
								String message = null;
								switch (failReason.getType()) {
								case IO_ERROR:
									message = "Input/Output error";
									break;
								case DECODING_ERROR:
									message = "Image can't be decoded";
									break;
								case NETWORK_DENIED:
									message = "Downloads are denied";
									break;
								case OUT_OF_MEMORY:
									message = "Out Of Memory error";
									break;
								case UNKNOWN:
									message = "Unknown error";
									break;
								}
								Toast.makeText(ProImagePagerActivity.this,
										message, Toast.LENGTH_SHORT).show();

								spinner.setVisibility(View.GONE);
							}

							@Override
							public void onLoadingComplete(String imageUri,
									View view, Bitmap loadedImage) {
								spinner.setVisibility(View.GONE);
							}
						});
				// ( (ViewParent)
				// imageview).requestDisallowInterceptTouchEvent(false);
				imageview.setScaleType(ImageView.ScaleType.FIT_XY);
				imageLayout.setLayoutParams(new Gallery.LayoutParams(136, 88));
				imageLayout.setBackgroundResource(galleryItemBackground);
				((ViewGroup) imageLayout)
						.requestDisallowInterceptTouchEvent(false);
				return imageLayout;
			}

		}

	}

	public class SamplePagerAdapter extends PagerAdapter {
		private LayoutInflater inflater;

		SamplePagerAdapter() {
			inflater = getLayoutInflater();
		}

		@Override
		public int getCount() {
			return ClassNumberCategory.mGridViewDate.size();
		}

		@Override
		public View instantiateItem(ViewGroup container, final int position) {
			View imageLayout = inflater.inflate(R.layout.pro_imageshow,
					container, false);
			final RelativeLayout layout_pager = (RelativeLayout) imageLayout
					.findViewById(R.id.layout_pager);

			// ((RelativeLayout)
			// layout_pager).requestDisallowInterceptTouchEvent(true);

			final PhotoView photoView = (PhotoView) imageLayout
					.findViewById(R.id.image);

			final ProgressBar spinner = (ProgressBar) imageLayout
					.findViewById(R.id.loading);

			// final String imgUrl = strDrawables[position];
			HashMap<String, Object> hashMap = ClassNumberCategory.mGridViewDate
					.get(position);
			String act = "";
			String name = "";
			if (hashMap.containsKey("act_form")
					&& hashMap.containsKey("act_name")) {
				RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
				lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

				final Button actButton = new Button(getBaseContext());
				actButton.setLayoutParams(lp);

				final Handler handler = new Handler();
				Runnable runnable = new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub

						actButton.setVisibility(visible);
						handler.post(this);
					}
				};

				handler.post(runnable);

				final String act_form = (String) hashMap.get("act_form");
				final String act_name = (String) hashMap.get("act_name");
				actButton.setText(act_form);
				actButton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						// Toast.makeText(getBaseContext(), act_form + act_name,
						// Toast.LENGTH_SHORT).show();
						if ("question".equals(act_form)) {
							Intent intent = new Intent(getBaseContext(),
									QuestionDialog.class);
							intent.putExtra("queans", act_name);
							startActivity(intent);
						} else if ("video".equals(act_form)) {
							//视频的url
							String url = act_name;
							// 打开相关视频
							Intent intent = new Intent(getBaseContext(),
									videoplayer.class);
							startActivity(intent);
							
						}

						if ("sing".equals(act_form)) {
							if (connected) {
								songev3();
//								String s = act_name;
//								Class<ProImagePagerActivity> c = ProImagePagerActivity.class;
//								try {
//									Method method = c.getDeclaredMethod(s);
//									method.invoke(c);
//								} catch (NoSuchMethodException e) {
//									// TODO Auto-generated catch block
//									Log.i(TAG, "NoSuchMethodException",e);
//								} catch (IllegalAccessException e) {
//									// TODO Auto-generated catch block
//									Log.i(TAG, "IllegalAccessException",e);
//								} catch (IllegalArgumentException e) {
//									// TODO Auto-generated catch block
//									Log.i(TAG, "IllegalArgumentException",e);
//								} catch (InvocationTargetException e) {
//									// TODO Auto-generated catch block
//									Log.i(TAG, "InvocationTargetException",e);
//								}
								
							} else {
								Toast.makeText(getBaseContext(), "请连接机器人！",
										Toast.LENGTH_SHORT).show();
							}
						} else if ("run".equals(act_form)) {
							String name = act_name;
							if (connected) {
//								do1ev3();
								String subSequence = name.subSequence(0, 3).toString();
								ev3startProgram(1, subSequence, name + ".rbf");
							} else {
								Toast.makeText(getBaseContext(), "请连接机器人！",
										Toast.LENGTH_SHORT).show();
							}
						}

					}
				});
				layout_pager.addView(actButton);
			}

			final String imgUrl = (String) hashMap.get("URL");
			if (Integer.parseInt(KemuId) > 99) {
				// Log.i(TAG, imgUrl);
				photoView.setImageDrawable(getResources().getDrawable(
						getResources().getIdentifier(imgUrl, "drawable",
								getPackageName())));

			} else {

				String baseUrl = mApplication.getBaseUrl();
				final String mImgUrl = baseUrl + imgUrl;
				// Log.i(TAG, mImgUrl);
				imageLoader.displayImage(mImgUrl, photoView, options,
						new SimpleImageLoadingListener() {
							@Override
							public void onLoadingStarted(String imageUri,
									View view) {
								spinner.setVisibility(View.VISIBLE);
							}

							@Override
							public void onLoadingFailed(String imageUri,
									View view, FailReason failReason) {
								String message = null;
								switch (failReason.getType()) {
								case IO_ERROR:
									message = "Input/Output error";
									break;
								case DECODING_ERROR:
									message = "Image can't be decoded";
									break;
								case NETWORK_DENIED:
									message = "Downloads are denied";
									break;
								case OUT_OF_MEMORY:
									message = "Out Of Memory error";
									break;
								case UNKNOWN:
									message = "Unknown error";
									break;
								}
								Toast.makeText(ProImagePagerActivity.this,
										message, Toast.LENGTH_SHORT).show();

								spinner.setVisibility(View.GONE);
							}

							@Override
							public void onLoadingComplete(String imageUri,
									View view, Bitmap loadedImage) {
								spinner.setVisibility(View.GONE);
							}
						});
			}

			photoView.setOnTouchListener(new OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					// eventflag++;
					// // Log.i(TAG, eventflag + "");

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
								act(Integer.parseInt(KemuId),
										Integer.parseInt(ClassId), position,
										layout_pager);
							} else if (clickCount == 2) {
								lastClick = System.currentTimeMillis();
								// 两次点击小于500ms 也就是连续点击
								if (lastClick - firstClick < 500) {
									Log.v("Double", "Double");
									reverse();
								}
								clear();
							}
						}
					}
					return true;
				}
			});

			// KemuId;
			// position+1;

			((ViewPager) container).addView(imageLayout, 0);
			return imageLayout;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

	}
	/* pos：0机身，1存储卡；path文件夹；name文件名（含扩展名） */
	public void ev3startProgram(int pos, String path, String name) {
		Log.i("ev3startProgram", "start");
		if (!connected){
			Log.i("ev3startProgram", "ev3未连接");
			return;
		}
		Bundle myBundle = new Bundle();
		myBundle.putInt("message", BTCommunicator.EV3_STARTPROGRAM);
		myBundle.putInt("pos", pos);
		myBundle.putString("path", path);
		myBundle.putString("name", name);
		
		Message myMessage = myHandler .obtainMessage();
		myMessage.setData(myBundle);
		btCommunicatorHandler.sendMessage(myMessage);
		Log.i("ev3startProgram", "end");
	}
	
	// void sendBTCmessage(int delay, int message, int value1, int value2) {
	// Bundle myBundle = new Bundle();
	// myBundle.putInt("message", message);
	// myBundle.putInt("value1", value1);
	// myBundle.putInt("value2", value2);
	// Message myMessage = myHandler.obtainMessage();
	// myMessage.setData(myBundle);
	//
	// if (delay == 0)
	// btcHandler.sendMessage(myMessage);
	//
	// else
	// btcHandler.sendMessageDelayed(myMessage, delay);
	// }

	// final Handler myHandler = new Handler() {
	// @Override
	// public void handleMessage(Message myMessage) {
	// switch (myMessage.getData().getInt("message")) {
	// case BTCommunicator.STATE_CONNECTED:
	// connectingProgressDialog.dismiss();
	// reusableToast.makeText(getApplicationContext(), "你可以和小伙伴互动啦",
	// Toast.LENGTH_LONG).show();
	// connected = true;
	// // Vibrator mVibrator =
	// // (Vibrator)getSystemService(VIBRATOR_SERVICE);
	// // mVibrator.vibrate(1000);
	// // sendBTCmessage(BTCommunicator.NO_DELAY,
	// // BTCommunicator.DO_BEEP, 500, 1000);
	// break;
	// // case BTCommunicator.GET_VALUE:
	// // byte[] sensorMessage = myBTCommunicator.getReturnMessage();
	// // int sensorValue = 0;
	// // if (sensorMessage[10] > 0)
	// // sensorValue = sensorMessage[10] + sensorMessage[11] * 256;
	// // else
	// // sensorValue = sensorMessage[10] + sensorMessage[11] * 256
	// // + 256;
	// // nxtValue(sensorValue / 10);
	// // break;
	// // case BTCommunicator.MOTOR_STATE:
	// //
	// // if (myBTCommunicator != null) {
	// // byte[] motorMessage = myBTCommunicator.getReturnMessage();
	// // int position = byteToInt(motorMessage[21])
	// // + (byteToInt(motorMessage[22]) << 8)
	// // + (byteToInt(motorMessage[23]) << 16)
	// // + (byteToInt(motorMessage[24]) << 24);
	// // show("Current position: " + position);
	// // }
	// //
	// // break;
	// /*
	// * case BTCommunicator.BATTERY_STATE: if(myBTCommunicator != null){
	// *
	// * byte[] batteryMessage = myBTCommunicator.getReturnMessage();
	// * mBatteryLevel = byteToInt(batteryMessage[4]); mBatteryLevel =
	// * (mBatteryLevel<<8) + byteToInt(batteryMessage[3]); float Btmp =
	// * ((int)mBatteryLevel/100); mTextBattery.setText("电池电压："+
	// * String.valueOf(Btmp/10) +" V");
	// *
	// * } break;
	// */
	// case BTCommunicator.STATE_CONNECTERROR_PAIRING:
	// connectingProgressDialog.dismiss();
	// // reusableToast.makeText(getApplicationContext(),
	// // "STATE_CONNECTERROR_PAIRING", Toast.LENGTH_LONG).show();
	// destroyBTCommunicator();
	// break;
	//
	// case BTCommunicator.STATE_CONNECTERROR:
	// connectingProgressDialog.dismiss();
	// // reusableToast.makeText(getApplicationContext(), "小伙伴不理你",
	// // Toast.LENGTH_LONG).show();
	// break;
	// case BTCommunicator.STATE_RECEIVEERROR:
	// case BTCommunicator.STATE_SENDERROR:
	// // reusableToast.makeText(getApplicationContext(), "小伙伴离开了",
	// // Toast.LENGTH_LONG).show();
	// destroyBTCommunicator();
	// /*
	// * if (btErrorPending == false) { btErrorPending = true; //
	// * inform the user of the error with an AlertDialog
	// * AlertDialog.Builder builder = new
	// * AlertDialog.Builder(thisActivity);
	// * builder.setTitle(getResources
	// * ().getString(R.string.bt_error_dialog_title))
	// * .setMessage(getResources
	// * ().getString(R.string.bt_error_dialog_message
	// * )).setCancelable(false) .setPositiveButton("OK", new
	// * DialogInterface.OnClickListener() {
	// *
	// * @Override public void onClick(DialogInterface dialog, int id)
	// * { btErrorPending = false; dialog.cancel(); selectNXT(); } });
	// * builder.create().show();
	// *
	// * }
	// */
	//
	// break;
	//
	// case BTCommunicator.VIBRATE_PHONE:
	// if (myBTCommunicator != null) {
	// byte[] vibrateMessage = myBTCommunicator.getReturnMessage();
	// Vibrator myVibrator = (Vibrator)
	// getSystemService(Context.VIBRATOR_SERVICE);
	// myVibrator.vibrate(vibrateMessage[2] * 10);
	// }
	//
	// break;
	// default:
	// break;
	// }
	// }
	// };

	// void do1ev3() {
	// //前进1秒
	// sendBTCmessage(BTCommunicator.NO_DELAY, BTCommunicator.EV3_MOTOR,
	// BTCommunicator.EV3_MOTOR_B, 75);
	// sendBTCmessage(BTCommunicator.NO_DELAY, BTCommunicator.EV3_MOTOR,
	// BTCommunicator.EV3_MOTOR_C, 75);
	// sendBTCmessage(2000, BTCommunicator.EV3_MOTOR,
	// BTCommunicator.EV3_MOTOR_B, 0);
	// sendBTCmessage(2000, BTCommunicator.EV3_MOTOR,
	// BTCommunicator.EV3_MOTOR_C, 0);
	// }

	private View inflate;

	public void act(int kemu, int clas, int pos, RelativeLayout layout_pager) {
		pos = pos + 1;// 第几页
		if (kemu == 100) {
			if (clas == 0) {
				if (pos == 4) {
					eventflag++;
					inflate = inflater.inflate(R.layout.layout4, null);
					ImageView image_act = (ImageView) inflate
							.findViewById(R.id.image_act);
					if (eventflag == 1) {
						image_act.setImageDrawable(getResources().getDrawable(
								R.drawable.p4_1));
						layout_pager.addView(inflate);
					}
					if (eventflag == 2) {
						image_act.setImageDrawable(getResources().getDrawable(
								R.drawable.p4_2));
						layout_pager.addView(inflate);
					}
					if (eventflag == 3) {
						image_act.setImageDrawable(getResources().getDrawable(
								R.drawable.p4_3));
						layout_pager.addView(inflate);
					}
					if (eventflag == 4) {
						image_act.setImageDrawable(getResources().getDrawable(
								R.drawable.p4_4));
						layout_pager.addView(inflate);
					}
				}

				if (pos == 7) {
					eventflag++;
					inflate = inflater.inflate(R.layout.layout4, null);
					ImageView image_act = (ImageView) inflate
							.findViewById(R.id.image_act);
					if (eventflag == 1) {
						image_act.setImageDrawable(getResources().getDrawable(
								R.drawable.p7_1));
						layout_pager.addView(inflate);
					}
					if (eventflag == 2) {
						image_act.setImageDrawable(getResources().getDrawable(
								R.drawable.p7_2));
						layout_pager.addView(inflate);
					}
					if (eventflag == 3) {
						image_act.setImageDrawable(getResources().getDrawable(
								R.drawable.p7_3));
						layout_pager.addView(inflate);
					}

				}
				if (pos == 8) {
					eventflag++;
					inflate = inflater.inflate(R.layout.layout4, null);
					ImageView image_act = (ImageView) inflate
							.findViewById(R.id.image_act);
					if (eventflag == 1) {
						image_act.setImageDrawable(getResources().getDrawable(
								R.drawable.p8_1));
						layout_pager.addView(inflate);
					}
				}
				if (pos == 9) {
					eventflag++;
					inflate = inflater.inflate(R.layout.layout4, null);
					final ImageView image_act = (ImageView) inflate
							.findViewById(R.id.image_act);
					if (eventflag == 1) {
						image_act.setImageDrawable(getResources().getDrawable(
								R.drawable.p9_1));
						layout_pager.addView(inflate);

						image_act.setOnTouchListener(new OnTouchListener() {

							@Override
							public boolean onTouch(View v, MotionEvent event) {
								// TODO Auto-generated method stub
								// metric.getwidth*350/960
								if (event.getAction() == MotionEvent.ACTION_DOWN) {
									if (event.getX() > screenWidth * 350 / 960
											&& event.getX() < screenWidth * 450 / 960
											&& event.getY() > screenHeight * 120 / 540
											&& event.getY() < screenHeight * 320 / 540) {
										Toast.makeText(getBaseContext(),
												"恭喜回答正确！！！", Toast.LENGTH_SHORT)
												.show();
										((ImageView) v)
												.setImageDrawable(getResources()
														.getDrawable(
																R.drawable.p9_2));
										v.setOnTouchListener(null);
									} else if (event.getX() > screenWidth * 300 / 960
											&& event.getX() < screenWidth * 400 / 960
											&& event.getY() > screenHeight * 350 / 540
											&& event.getY() < screenHeight * 480 / 540) {
										Toast.makeText(getBaseContext(),
												"回答错误，再试一次！",
												Toast.LENGTH_SHORT).show();
									} else if (event.getX() > screenWidth * 580 / 960
											&& event.getX() < screenWidth * 660 / 960
											&& event.getY() > screenHeight * 200 / 540
											&& event.getY() < screenHeight * 310 / 540) {
										Toast.makeText(getBaseContext(),
												"回答错误，再试一次！",
												Toast.LENGTH_SHORT).show();
									} else if (event.getX() > screenWidth * 460 / 960
											&& event.getX() < screenWidth * 570 / 960
											&& event.getY() > screenHeight * 385 / 540
											&& event.getY() < screenHeight * 500 / 540) {
										Toast.makeText(getBaseContext(),
												"回答错误，再试一次！",
												Toast.LENGTH_SHORT).show();
									}
								}

								return true;
							}
						});
					}
				}

				if (pos == 10) {
					eventflag++;
					inflate = inflater.inflate(R.layout.layout4, null);
					RelativeLayout layout_root = (RelativeLayout)inflate.findViewById(R.id.layout_root);
					
					ImageView image_act = (ImageView) inflate
							.findViewById(R.id.image_act);
					RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
							250 * screenWidth / 960, 90 * screenHeight / 540);
					lp.addRule(RelativeLayout.CENTER_IN_PARENT , R.id.layout_root);
					layout_root.setLayoutParams(lp);
					if (eventflag == 1) {
						image_act.setImageDrawable(getResources().getDrawable(
								R.drawable.p10_1));
//						image_act.setLayoutParams(lp);
						// image_act.setX(113*screenWidth/960);
						// image_act.setY(265*screenHeight/540);
						// image_act.layout(113*screenWidth/960,
						// 265*screenHeight/540, 363*screenWidth/960,
						// 355*screenHeight/540);
						// image_act.postInvalidate();
//						image_act.setClickable(true);
						layout_root.setOnTouchListener(new ImageDrag());
						layout_pager.addView(inflate);
					}

				}

			}
		}
	}

	private float lastx, lasty, t, r;

	private class ImageDrag implements OnTouchListener {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				Log.i(TAG,
						"RawX:" + event.getRawX() + "RawY:" + event.getRawY());
				Log.i(TAG, "X:" + event.getX() + "Y:" + event.getY());
				Log.i(TAG, "Top:" + v.getTop() + "Right:" + v.getRight());
				lastx = event.getRawX();
				lasty = event.getRawY();
			case MotionEvent.ACTION_MOVE:
				int dx = (int) (event.getRawX() - lastx);
				int dy = (int) (event.getRawY() - lasty);

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
				lastx = event.getRawX();
				lasty = event.getRawY();
				v.postInvalidate();
				t = top;
				r = right;
				break;
			case MotionEvent.ACTION_UP:
				Log.i(TAG, " top:" + t + "right:" + r);
				if (r > screenWidth * 660 / 960 && r < screenWidth * 685 / 960
						&& t > screenHeight * 265 / 540
						&& t < screenHeight * 300 / 540) {
					Toast.makeText(getBaseContext(), "恭喜你答对了!!!",
							Toast.LENGTH_SHORT).show();
				}
				break;
			}
			return true;
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.GoShaka_tbn_id:
			Intent intent = new Intent(ProImagePagerActivity.this,
					LeShuaNumberOneClass.class);
			startActivity(intent);
			ProImagePagerActivity.this.overridePendingTransition(
					R.anim.slide_left, R.anim.left_out);

			break;
		case R.id.btn_robots:
			Intent serverIntent = new Intent(ProImagePagerActivity.this,
					DeviceListActivity.class);
			startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
			break;
		case R.id.tiaozhuan_btn_id:
			String trim = tiaozhuan_edittext_id.getText().toString().trim();
			if (!trim.isEmpty()) {
				int pos = Integer.parseInt(trim);
				viewPager.setCurrentItem(pos - 1);
			}
			break;
		default:
			break;
		}
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
			} else if (resultCode == Activity.RESULT_CANCELED) {
				destroyBTCommunicator();
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
		connected = false;
		connectingProgressDialog = ProgressDialog.show(this, "", "和小伙伴交流中",
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
	}

	private void createBTCommunicator() {
		// interestingly BT adapter needs to be obtained by the UI thread - so
		// we pass it in in the constructor
		btCommunicator = new BTCommunicator(this, myHandler,
				BluetoothAdapter.getDefaultAdapter(), getResources());
		btCommunicatorHandler = btCommunicator.getHandler();
	}

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
			// case BTCommunicator.PROGRAM_NAME:
			// if (btCommunicator != null) {
			// byte[] returnMessage = btCommunicator.getReturnMessage();
			// startRXEprogram(returnMessage[2]);
			// }
			//
			// break;
			// case BTCommunicator.GET_VALUE:
			// byte[] sensorMessage = btCommunicator.getReturnMessage();
			// int sensorValue = 0;
			// if (sensorMessage[10] > 0)
			// sensorValue = sensorMessage[10] + sensorMessage[11] * 256;
			// else
			// sensorValue = sensorMessage[10] + sensorMessage[11] * 256
			// + 256;
			// nxtValue(sensorValue / 10);
			// break;
			case BTCommunicator.MOTOR_STATE:

				if (btCommunicator != null) {
					byte[] motorMessage = btCommunicator.getReturnMessage();
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
				if (btCommunicator != null) {
					byte[] vibrateMessage = btCommunicator.getReturnMessage();
					Vibrator myVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
					myVibrator.vibrate(vibrateMessage[2] * 10);
				}

				break;
			default:
				break;
			}
		}
	};

	public void destroyBTCommunicator() {

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
		Message myMessage = myHandler.obtainMessage();
		myMessage.setData(myBundle);

		if (delay == 0)
			btCommunicatorHandler.sendMessage(myMessage);

		else
			btCommunicatorHandler.sendMessageDelayed(myMessage, delay);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		try {
			finalize();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			Log.i(TAG, "Throwable", e);
		}
	}

	@Override
	public boolean isPairing() {
		// TODO Auto-generated method stub
		return false;
	}

	private int byteToInt(byte byteValue) {
		int intValue = (byteValue & (byte) 0x7f);

		if ((byteValue & (byte) 0x80) != 0)
			intValue |= 0x80;
		return intValue;
	}

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
	}

	
	
	void do1ev3() {
		// 前进1秒
		sendBTCmessage(BTCommunicator.NO_DELAY, BTCommunicator.EV3_MOTOR,
				BTCommunicator.EV3_MOTOR_B, 75);
		// sendBTCmessage(BTCommunicator.NO_DELAY, BTCommunicator.EV3_MOTOR,
		// BTCommunicator.EV3_MOTOR_C, 75);
		sendBTCmessage(3000, BTCommunicator.EV3_MOTOR,
				BTCommunicator.EV3_MOTOR_B, 0);
		// sendBTCmessage(2000, BTCommunicator.EV3_MOTOR,
		// BTCommunicator.EV3_MOTOR_C, 0);
	}
}
