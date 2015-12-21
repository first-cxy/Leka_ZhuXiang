package com.example.zhuxiangrobitclass.adpter;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.zhuxiangrobitclass1.R;
import com.example.zhuxiangrobitclass.mApplication;
import com.example.zhuxiangrobitclass.ui.RobitNumberTwoClass;
import com.example.zhuxiangrobitclass.ui.imageView.PhotoView;
import com.example.zhuxiangrobitclass.util.ImageLoadConfog;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

public class ClassToAdapter extends PagerAdapter {
    private ArrayList<ImageView> lis = new ArrayList<ImageView>();
    public Activity c;
    private LayoutInflater inflater;

    private mApplication mApplication;
    public ClassToAdapter(Activity c, ArrayList<ImageView> list) {
        this.lis = list;
        this.c = c;
        inflater = c.getLayoutInflater();
        mApplication = (com.example.zhuxiangrobitclass.mApplication) c.getApplication();
    }

    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
//        ((ViewPager) arg0).removeView((View) arg2);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.example.zhuxiangrobitclass.adpter.MyAdapter#getCount()
     */
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        // return ClassNumberCategory.mGridViewDate.size();
        return RobitNumberTwoClass.strDrawables.length;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int arg1) {

        View imageLayout = inflater.inflate(R.layout.pro_imageshow, container, false);
        final PhotoView photoView = (PhotoView) imageLayout.findViewById(R.id.image);
        final ProgressBar spinner = (ProgressBar) imageLayout.findViewById(R.id.loading);
        // final String imgUrl =(String) ClassNumberCategory.mGridViewDate.get(arg1).get("URL");
        // final String mImgUrl=baseurl+imgUrl;
        final String mImgUrl = RobitNumberTwoClass.strDrawables[arg1];
        
        ImageLoader.getInstance().displayImage(mImgUrl, photoView, ImageLoadConfog.geteDisPlayImageOption(),
//        ImageLoader.getInstance().displayImage(mImgUrl, photoView, ImageLoadConfog.geteDisPlayImageOption(),
                new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {
                        spinner.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
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

                        spinner.setVisibility(View.GONE);
                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        spinner.setVisibility(View.GONE);
                    }
                });
        ((ViewPager) container).addView(imageLayout, 0);
        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}