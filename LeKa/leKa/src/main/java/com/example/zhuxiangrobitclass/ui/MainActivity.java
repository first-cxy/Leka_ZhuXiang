package com.example.zhuxiangrobitclass.ui;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.example.zhuxiangrobitclass1.R;

public class MainActivity extends Activity {

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);

//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see android.support.v4.app.FragmentActivity#onStart()
     */
    @Override
    protected void onStart() {
        super.onStart();
        handler = new Handler() {
            /*
             * (non-Javadoc)
             * 
             * @see android.os.Handler#handleMessage(android.os.Message)
             */
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                startActivity(new Intent(MainActivity.this, SubjectsCategory.class));
                MainActivity.this.finish();
                MainActivity.this.overridePendingTransition(R.anim.slide_left,
                        R.anim.left_out);

            }
        };
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                handler.sendEmptyMessage(1);
            }
        }, 2000);
    }

}
