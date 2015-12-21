package com.example.zhuxiangrobitclass.factory;

import com.example.zhuxiangrobitclass.ui.LoginFragment;
import com.example.zhuxiangrobitclass.ui.RegisterFragment;
import com.example.zhuxiangrobitclass1.R;

import android.app.Fragment;

public class LogFragmentFactory {
	public static Fragment getInstanceByIndex(int index) {
		Fragment fragment = null;
		switch (index) {
		case R.id.textview_login:
			fragment = new LoginFragment();
			break;
		case R.id.textview_register:
			fragment = new RegisterFragment();
			break;
		
		}
		return fragment;
	}
}
