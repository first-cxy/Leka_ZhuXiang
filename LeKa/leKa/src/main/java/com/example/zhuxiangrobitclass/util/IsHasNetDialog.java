package com.example.zhuxiangrobitclass.util;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;

public class IsHasNetDialog {
  public Context context;
  public IsHasNetDialog(Context c){
	  this.context=c;
  }
	public boolean CheckNetworkState() {
		boolean flag = false;
		
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (manager.getActiveNetworkInfo() != null) {
			flag = manager.getActiveNetworkInfo().isAvailable();
		}
		if (!flag) {
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setIcon(android.R.drawable.ic_dialog_alert);
			builder.setTitle("网络状态");
			builder.setMessage("当前网络不可用,是否设置网络?");
			builder.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {
				    public Intent intent;
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							 if(android.os.Build.VERSION.SDK_INT>10){
								   intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
				                }else{
				                    intent = new Intent();
				                    ComponentName component = new ComponentName("com.android.settings","com.android.settings.WirelessSettings");
				                    intent.setComponent(component);
				                    intent.setAction("android.intent.action.VIEW");
				                }
				               context.startActivity(intent);
						}
					});
			builder.setNegativeButton("取消",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					});
			builder.create();
			builder.show();
		}

		return flag;

	}

}
