package com.example.zhuxiangrobitclass.util;

import android.content.Context;
import android.widget.Toast;

public class Util {
  public static void showToast(Context context ,String s){
	  Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
  }
}
