package com.example.zhuxiangrobitclass.factory;

import android.app.Fragment;

import com.example.zhuxiangrobitclass.ui.FragmentLeShuaNumberOneClass;
import com.example.zhuxiangrobitclass.ui.FragmentSubjectsCategory;
import com.example.zhuxiangrobitclass.ui.FragmentUser;
import com.example.zhuxiangrobitclass.ui.RegisterFragment;
import com.example.zhuxiangrobitclass1.R;

public class HomeFragmentFactory {
	public static Fragment getInstanceByIndex(int index) {
		Fragment fragment = null;
		switch (index) {
		case R.id.btn_model:
			fragment = new FragmentSubjectsCategory();
			break;
		case R.id.btn_code:
			fragment = new FragmentLeShuaNumberOneClass();
			break;
		case R.id.btn_share:
			fragment = new RegisterFragment();
			break;
		case R.id.btn_user:
			fragment = new FragmentUser();
			break;
		}
		return fragment;
	}
}
