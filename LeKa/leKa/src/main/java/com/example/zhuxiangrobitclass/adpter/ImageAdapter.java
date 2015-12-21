package com.example.zhuxiangrobitclass.adpter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageSwitcher;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
	/* �������� */
	int mGalleryItemBackground;
	private Context mContext;
	private List<String> lis;
	private ImageSwitcher mSwitcher;

	/**
	 * ImageAdapter�Ĺ����?
	 * 
	 * @param c
	 * @param list
	 */
	public ImageAdapter(Context context, List<String> list) {
		mContext = context;
		lis = list;

		/*
		 * ʹ��styleable.xml����Gallery���� ����TypedArray��С��Ҳ��֪����ʲô,�۲����ȥ������?����Դ�� /**
		 * Container for an array of values that were retrieved with {@link
		 * Resources.Theme#obtainStyledAttributes(AttributeSet, int[], int,
		 * int)} or {@link Resources#obtainAttributes}. Be sure to call {@link
		 * #recycle} when done with them.
		 * 
		 * The indices used to retrieve values from this structure correspond to
		 * the positions of the attributes given to obtainStyledAttributes.
		 * ������,������˼���ͽ������? Container for an array of values that were retrieved
		 * ��˵:��Ŵ������ж�ȡ�����?������Щֵ���Է���ʹ�õ�Ŷ)������
		 */
		// TypedArray a = obtainStyledAttributes(R.styleable.Gallery);
		// mGalleryItemBackground = a.getResourceId(
		// R.styleable.Gallery_android_galleryItemBackground, 0);

		// �ö����styleable�����ܹ�����ʹ��
		// Դ����:Be sure to call {@link #recycle} when done with them.
		// TypedArray a = obtainStyledAttributes(R.styleable.Gallery);
		// mGalleryItemBackground = a.getResourceId(
		// R.styleable.Gallery_android_galleryItemBackground, 0);
		//
		// a.recycle();
	}

	public int getCount() {
		return lis.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	/* ����Ҫ��д�ķ���getView,������View���� */
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView i = new ImageView(mContext);
		// ʹ��ͼƬ�������벢����ͼƬ
		Bitmap bm = BitmapFactory.decodeFile(lis.get(position).toString());
		i.setImageBitmap(bm);

		/**
		 * �趨ImageView��ߣ�������FIX_XY,��Ȼ��ö�٣��ü�����С��þ�û����ö����?
		 * ������ScaleType�г�FIT_XY���������ѡ�Ч��ͼ�ֱ�����£�
		 */
		i.setScaleType(ImageView.ScaleType.FIT_XY);
		// �����趨Layout�Ŀ��?
		/**
		 * ����ط�С���и����ʣ�������Galleryȫ����ʱ���������?��ʲô�취���Բ��������컹��������ȫ������ָ����С�?лл
		 * i.setLayoutParams( new Gallery.LayoutParams(
		 * WindowManager.LayoutParams.MATCH_PARENT,
		 * WindowManager.LayoutParams.MATCH_PARENT));
		 */
		i.setLayoutParams(new Gallery.LayoutParams(136, 88));

		// �趨Gallery����ͼ
		i.setBackgroundResource(mGalleryItemBackground);

		// ����imageView����
		return i;
	}

}