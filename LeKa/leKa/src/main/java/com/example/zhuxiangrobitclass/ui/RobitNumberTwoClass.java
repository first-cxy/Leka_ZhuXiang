package com.example.zhuxiangrobitclass.ui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

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
import android.os.Vibrator;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhuxiangrobitclass1.R;
import com.example.zhuxiangrobitclass.adpter.ClassToAdapter;
import com.example.zhuxiangrobitclass.ui.base.BaseActivity;
import com.example.zhuxiangrobitclass.util.Utils;
import com.example.zhuxiangrobitclass.zxing.BTCommunicator;
import com.example.zhuxiangrobitclass.zxing.BTConnectable;
import com.example.zhuxiangrobitclass.zxing.DeviceListActivity;
import com.example.zhuxiangrobitclass.zxing.activity.CaptureActivity;

public class RobitNumberTwoClass extends BaseActivity implements BTConnectable, OnClickListener {
    private String scanResult;
    private double dx;
    private double dy;
    public ViewPager mPager;
    private static int c_id = 0;
    private ArrayList<ImageView> list;
    private View[] vs;
    private LayoutInflater inflater;
    private ProgressDialog connectingProgressDialog;
    private BTCommunicator myBTCommunicator = null;
    private boolean connected = false;
    private long currentTimeMillis;
    private Handler btcHandler;
    private Toast reusableToast;
    public ArrayList<Integer> arrayList = new ArrayList<Integer>();
    private static final int REQUEST_CONNECT_DEVICE = 1000;
    private static final int REQUEST_ENABLE_BT = 2000;
    private static final int REQUEST_ENABLE_QR = 3000;
    int index;
    public static int currindex = 0;
    public ArrayList<String> arr = new ArrayList<String>();
    private boolean pairing;
    /**
     * @author fengxb
     * @parent 判断是不是第一次点击机器人
     */
    private boolean isDisplayMenu = false; // 菜单的显示状态, 默认为不显示
    private boolean isDisplayLevel2 = false; // 二级菜单的显示状态, 默认为不显示
    private boolean isDisplayLevel3 = false; // 三级菜单的显示状态, 默认为不显示
    private RelativeLayout rlLevel2;
    private ImageButton pageViewNumberChange;
    private ViewPager viewPager;
    private RelativeLayout rlLevel3;
    private TextView tow_showNumberForstudentInfo;
    public static String[] strDrawables = {
            "http://attach.bbs.miui.com/forum/month_1012/1012021239abb4059c16f58442.jpg",
            "http://www.qipaox.com/tupian/20091/20091822192.jpg",
            "http://image11.m1905.cn/uploadfile/2008/0924/19153127332.jpg",
            "http://sh.suijue.com/picture/1/1029/6.jpg",
            "http://attach.bbs.miui.com/forum/201111/15/184914onpr6a33shjo3jvt.jpg",
            "http://img.article.pchome.net/00/26/80/13/pic_lib/wm/20070706_24c538b0a0971bfe2881MEjkwy0SBFuN.jpg",
            "http://attach.bbs.miui.com/forum/201111/15/184851m2cl8dfnddcrtcdt.jpg",
            "http://pic.zhutou.com/html/UploadPic/2010-7/2010783933760.jpg",
            "http://up.emland.net/attachnew/Mona_1107/9f031311160263.jpg" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.classnumberone_main);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        // mImageURL = extras.getString("urlForImage");
        // position = extras.getInt("position");
        Display dis = getWindowManager().getDefaultDisplay();
        DisplayMetrics dism = new DisplayMetrics();
        dis.getMetrics(new DisplayMetrics());
        dx = dis.getWidth() / 100.0;
        dy = dis.getHeight() / 100.0;
        inflater = getLayoutInflater();
        initViewPage();
        initEvent();
    }

    private void initViewPage() {
        vs = new View[strDrawables.length];
        viewPager = (ViewPager) findViewById(R.id.vPager);
        setContentView(R.layout.classnumberone_main);
        ClassToAdapter classToAdapter = new ClassToAdapter(this, list);
        viewPager.setAdapter(classToAdapter);
        viewPager.setOnPageChangeListener(new MyListener());
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(3);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        super.onKeyDown(keyCode, event);
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            viewPager.destroyDrawingCache();
            return true;
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * @author fengxb 控件事件注册
     */
    public void initEvent() {
        /**
         * 在这里初始化所有的控件
         */
        initPageViewGroup();
        initEventView();
    }

    private void initPageViewGroup() {
        rlLevel3 = (RelativeLayout) findViewById(R.id.rl_level3);
        rlLevel2 = (RelativeLayout) findViewById(R.id.rl_level2);
        rlLevel32 = (RelativeLayout) findViewById(R.id.rl_level3);
        pageViewNumberChange = (ImageButton) findViewById(R.id.PageViewNumberChange);
    }

    private void initEventView() {
        pageViewNumberChange.setOnClickListener(this);
        findViewById(R.id.ib_home).setOnClickListener(this);
        findViewById(R.id.ib_menu).setOnClickListener(this);

    }

    /**
     * Creates a new object for communication to the NXT robot via bluetooth and fetches the corresponding handler.
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
                show(myMessage.getData().getString("toastText"));
                break;
            case BTCommunicator.STATE_CONNECTED:
                connectingProgressDialog.dismiss();
                reusableToast.makeText(getApplicationContext(), "你可以和小伙伴互动啦", Toast.LENGTH_LONG).show();
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
                    sensorValue = sensorMessage[10] + sensorMessage[11] * 256 + 256;
                nxtValue(sensorValue / 10);
                break;
            case BTCommunicator.MOTOR_STATE:

                if (myBTCommunicator != null) {
                    byte[] motorMessage = myBTCommunicator.getReturnMessage();
                    int position = byteToInt(motorMessage[21]) + (byteToInt(motorMessage[22]) << 8)
                            + (byteToInt(motorMessage[23]) << 16) + (byteToInt(motorMessage[24]) << 24);
                    show("Current position: " + position);
                }

                break;
            /*
             * case BTCommunicator.BATTERY_STATE: if(myBTCommunicator != null){
             * 
             * byte[] batteryMessage = myBTCommunicator.getReturnMessage(); mBatteryLevel =
             * byteToInt(batteryMessage[4]); mBatteryLevel = (mBatteryLevel<<8) + byteToInt(batteryMessage[3]); float
             * Btmp = ((int)mBatteryLevel/100); mTextBattery.setText("电池电压："+ String.valueOf(Btmp/10) +" V");
             * 
             * } break;
             */
            case BTCommunicator.STATE_CONNECTERROR_PAIRING:
                connectingProgressDialog.dismiss();
                reusableToast.makeText(getApplicationContext(), "STATE_CONNECTERROR_PAIRING", Toast.LENGTH_LONG).show();
                destroyBTCommunicator();
                break;

            case BTCommunicator.STATE_CONNECTERROR:
                connectingProgressDialog.dismiss();
                reusableToast.makeText(getApplicationContext(), "小伙伴不理你", Toast.LENGTH_LONG).show();
                break;
            case BTCommunicator.STATE_RECEIVEERROR:
            case BTCommunicator.STATE_SENDERROR:
                reusableToast.makeText(getApplicationContext(), "小伙伴离开了", Toast.LENGTH_LONG).show();
                destroyBTCommunicator();
                /*
                 * if (btErrorPending == false) { btErrorPending = true; // inform the user of the error with an
                 * AlertDialog AlertDialog.Builder builder = new AlertDialog.Builder(thisActivity);
                 * builder.setTitle(getResources ().getString(R.string.bt_error_dialog_title)) .setMessage(getResources
                 * ().getString(R.string.bt_error_dialog_message )).setCancelable(false) .setPositiveButton("OK", new
                 * DialogInterface.OnClickListener() {
                 * 
                 * @Override public void onClick(DialogInterface dialog, int id) { btErrorPending = false;
                 * dialog.cancel(); selectNXT(); } }); builder.create().show();
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
            sendBTCmessage(BTCommunicator.NO_DELAY, BTCommunicator.STOP_PROGRAM, 0, 0);
            sendBTCmessage(200, BTCommunicator.START_PROGRAM, programToStart);
        } else {
            sendBTCmessage(BTCommunicator.NO_DELAY, BTCommunicator.START_PROGRAM, programToStart);
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
            sendBTCmessage(BTCommunicator.NO_DELAY, BTCommunicator.DISCONNECT, 0, 0);
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

    public void startProgram(String name) {
        // for .rxe programs: get program name, eventually stop this and start
        // the new one delayed
        // is handled in startRXEprogram()
        if (name.endsWith(".rxe")) {
            programToStart = name;
            sendBTCmessage(BTCommunicator.NO_DELAY, BTCommunicator.GET_PROGRAM_NAME, 0, 0);
            return;
        }

        // for .nxj programs: stop bluetooth communication after starting the
        // program
        if (name.endsWith(".nxj")) {
            sendBTCmessage(BTCommunicator.NO_DELAY, BTCommunicator.START_PROGRAM, name);
            destroyBTCommunicator();
            return;
        }

        // for all other programs: just start the program
        sendBTCmessage(BTCommunicator.NO_DELAY, BTCommunicator.START_PROGRAM, name);
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

    void do2(int power) {
        // Toast.makeText(NumberOneClass.this, "左转", Toast.LENGTH_SHORT).show();
        sendBTCmessage(BTCommunicator.NO_DELAY, BTCommunicator.MOTOR_B, power, 0);
        sendBTCmessage(10, BTCommunicator.MOTOR_C, 0, 0);
    }

    void do3() {
        // Toast.makeText(NumberOneClass.this, "停止", Toast.LENGTH_SHORT).show();
        sendBTCmessage(BTCommunicator.NO_DELAY, BTCommunicator.MOTOR_B, 0, 0);
        sendBTCmessage(10, BTCommunicator.MOTOR_C, 0, 0);
    }

    void do4() {
        sendBTCmessage(BTCommunicator.NO_DELAY, BTCommunicator.MOTOR_C, 75, 0);
        sendBTCmessage(10, BTCommunicator.MOTOR_B, 0, 0);
    }

    void do4(int power) {
        sendBTCmessage(BTCommunicator.NO_DELAY, BTCommunicator.MOTOR_C, power, 0);
        sendBTCmessage(10, BTCommunicator.MOTOR_B, 0, 0);
    }

    /**
     * 左转控制
     */
    void do5(int power) {
        // Toast.makeText(NumberOneClass.this, "左转", Toast.LENGTH_SHORT).show();
        sendBTCmessage(BTCommunicator.NO_DELAY, BTCommunicator.MOTOR_B, power, 0);
        // sendBTCmessage(10, BTCommunicator.MOTOR_C, 0, 0);
    }

    /**
     * 右转控制
     */
    void do6(int power) {
        // Toast.makeText(NumberOneClass.this, "右转", Toast.LENGTH_SHORT).show();
        sendBTCmessage(BTCommunicator.NO_DELAY, BTCommunicator.MOTOR_C, power, 0);
        // sendBTCmessage(10, BTCommunicator.MOTOR_B, 0, 0);
    }

    /**
     * @author fengxb 控制速度的方法
     */
    void power(int power) {
        sendBTCmessage(BTCommunicator.NO_DELAY, BTCommunicator.MOTOR_B, power, 0);
        sendBTCmessage(10, BTCommunicator.MOTOR_C, power, 0);
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
    public static int powerState = 75;

    void demo2() {
        Toast.makeText(RobitNumberTwoClass.this, "执行卡程序", Toast.LENGTH_SHORT).show();
        if (arr.size() != 0) {
            for (int i = 0; i < arr.size(); i++) {
                String str = arr.get(i);
                String[] split = str.split("=");
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
                                power(powerState);
                            } else if (arr.get(savaIndex - 1).equals("CARD-00:02")) {
                                Log.e("LGONUM", num++ + "");
                                do2(powerState);
                            } else {
                                do4(powerState);
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
                                power(powerState);
                            } else if (arr.get(savaIndex - 1).equals("CARD-00:02")) {
                                Log.e("LGONUM", num++ + "");
                                do2(powerState);
                            } else {
                                do4(powerState);
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
                } else if (split[1].equals("CARD-00:06")) {
                    do3();
                } else if (split[1].equals("AANJGVMR")) {
                    powerState = 25;
                } else if (split[1].equals("AAMVQMCW")) {
                    powerState = 50;
                } else if (split[1].equals("AAGKMTJB")) {
                    powerState = 75;
                } else if (split[1].equals("CARD-00:06")) {
                    powerState = 100;
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
        tow_showNumberForstudentInfo.setText(String.valueOf(sensorValue));
    }

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            nxt2();
            handler.postDelayed(this, 500);
        }
    };
    private String mImageURL;
    private RelativeLayout rlLevel32;
    private int position;

    int sx(int x) {
        return (int) (dx * x);
    }

    int sy(int y) {
        return (int) (dy * y);
    }

    void selectNXT() {
        Intent serverIntent = new Intent(RobitNumberTwoClass.this, DeviceListActivity.class);
        startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
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

    @Override
    public boolean isPairing() {
        return false;
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
        case R.id.PageViewNumberChange:
            findViewById(R.id.showSearchPageView).setVisibility(View.VISIBLE);
            break;
        case R.id.pageNumberSearchBtn:
            EditText pageNumberSearch = (EditText) findViewById(R.id.pageNumberSearch);
            String number = pageNumberSearch.getText().toString().trim();
            int Inumber = Integer.parseInt(number);
            if (Inumber > 0 && Inumber < list.size()) {
                int currentItem = viewPager.getCurrentItem();
                if (currentItem < Inumber - 1) {
                    viewPager.setCurrentItem(Inumber - 1);
                }
                findViewById(R.id.showSearchPageView).setVisibility(View.GONE);
            }
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
        /*
         * case R.id.power_kuaiai: startProgram("Demo2_1.rxe"); break; case R.id.power_man: startProgram("Demo2_2.rxe");
         * break; case R.id.fangxiang_kuaiai: startProgram("Demo2_3.rxe"); break; case R.id.fangxiang_man:
         * startProgram("Demo2_4.rxe"); break; case R.id.juli_kuaiai: startProgram("Demo2_51.rxe"); break; case
         * R.id.juli_man: startProgram("Demo2_6.rxe"); break;
         */
        default:
            break;
        }
    }

    public void setBitmap() {
        String fn = String.format(Environment.getExternalStorageDirectory().getPath() + "/zhuxiang/t%02d.jpg", index);
        // robit_img.setImageBitmap(getLoacalBitmap(fn));
        // imageMain.setImageBitmap(getLoacalBitmap("/sdcard/zhuxiang/t02.jpg"));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
        case REQUEST_CONNECT_DEVICE:

            // When DeviceListActivity returns with a device to connect
            if (resultCode == Activity.RESULT_OK) {
                // Get the device MAC address and start a new bt communicator
                // thread
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
                // ((TextView)
                // findViewById(R.id.fullscreen_content)).setText(scanResult);
                // showToast(scanResult, Toast.LENGTH_SHORT);
                if ("NXT-01:16:53:0F:64:73".equals(scanResult)) {
                    startBTCommunicator("00:16:53:10:50:d9");
                } else if ("NXT-0F:64:73".equals(scanResult)) {
                    startBTCommunicator("00:16:53:0e:e5:23");
                } else {
                    arr.add(scanResult);
                    // http://www.irobo.org/zx.php?k=AAQSUTPZ ka 10
                    // http://www.irobo.org/zx.php?k=AANJGVMR ka7
                    // http://www.irobo.org/zx.php?k=AAMVQMCW ka8
                    // http://www.irobo.org/zx.php?k=AAGKMTJB ka9
                    // CARD-00:02
                }
            }
            break;
        // will not be called now, since the check intent is not generated
        /*
         * case TTS_CHECK_CODE: if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) { // success, create the
         * TTS instance mTts = new TextToSpeech(this, this); } else { // missing data, install it Intent installIntent =
         * new Intent(); installIntent.setAction( TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
         * startActivity(installIntent); }
         * 
         * break;
         */
        }
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
		Log.e("唱歌","经过这块");

	}


}
