package com.example.zhuxiangrobitclass.util;

import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.RotateAnimation;

public class Utils {
	
//	private static boolean isInRunning = false;
//	private static boolean isOutRunning = false;
	private static boolean isRunning = false;
	

	/**
	 *	�?��进场动画
	 */
	public static void startInRotateAnimation(ViewGroup viewGroup, long startOffset) {
		for (int i = 0; i < viewGroup.getChildCount(); i++) {
			viewGroup.getChildAt(i).setEnabled(true);		// 设置VieGroup�?��的孩子状态Endbled为True
		}
		
		RotateAnimation anim = new RotateAnimation(
				-180f, 0f, 
				Animation.RELATIVE_TO_SELF, 0.5f, 	// x轴上的�?
				Animation.RELATIVE_TO_SELF, 1.0f);	// y轴上的�?
		anim.setDuration(500);			// �?��动画的事�?
		anim.setStartOffset(startOffset);		
		anim.setFillAfter(true);		// 动画停止在最后状�?
		anim.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				isRunning = true;
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				isRunning = false;
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}});
		viewGroup.startAnimation(anim);
	}
	
	/**
	 * �?��出场动画
	 * @param viewGroup
	 * @param startOffset
	 */
	public static void startOutRotateAnimation(ViewGroup viewGroup, long startOffset) {
		for (int i = 0; i < viewGroup.getChildCount(); i++) {
			viewGroup.getChildAt(i).setEnabled(false);		
		}
		
		RotateAnimation anim = new RotateAnimation(
				0f, -180f, 
				Animation.RELATIVE_TO_SELF, 0.5f, 	
				Animation.RELATIVE_TO_SELF, 1.0f);	
		anim.setDuration(500);			
		anim.setStartOffset(startOffset);		
		anim.setFillAfter(true);		
		
		anim.setAnimationListener(new AnimationListener() {

			/**
			 * 当动画开始时
			 */
			@Override
			public void onAnimationStart(Animation animation) {
				isRunning = true;
			}

			/**
			 * 当动画结束时
			 */
			@Override
			public void onAnimationEnd(Animation animation) {
				isRunning = false;
			}

			/**
			 * 当动画开始之�?
			 */
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}});
		viewGroup.startAnimation(anim);
	}
	

	/**
	 * 获取动画是否正在执行
	 * @return
	 */
	public static boolean isRunningAnimation() {
//		return isInRunning || isOutRunning;
		return isRunning;
	}
}
