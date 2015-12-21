/**
 * 
 */
package com.example.zhuxiangrobitclass.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Handler;

import com.example.zhuxiangrobitclass.ui.AuthImageDownloader;
import com.example.zhuxiangrobitclass1.R;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

/**
 * @author fengxb 2014��10��15��
 */
public class ImageLoadConfog {
    public Context c;

    public ImageLoadConfog(Context c) {
        this.c = c;
    }

    public ImageLoaderConfiguration getImageLoaderConfiguration() {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(c)
                .memoryCacheExtraOptions(480, 800)
                // default = device screen dimensions
                // .diskCacheExtraOptions(480, 800, null)
                .taskExecutorForCachedImages(null).threadPoolSize(3)
                // default
                .threadPriority(Thread.NORM_PRIORITY - 2)
                // default
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                // default
                .denyCacheImageMultipleSizesInMemory().memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)
                // .memoryCacheSizePercentage(13)
                // default
                // default
                // .diskCacheSize(50 * 1024 * 1024).diskCacheFileCount(100)
                // .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
                .imageDownloader(new AuthImageDownloader(c)) // default
                .defaultDisplayImageOptions(geteDisPlayImageOption()) // default
              //  .writeDebugLogs()
                .build();
        return config;
    }

    public static DisplayImageOptions geteDisPlayImageOption() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
        // .showImageOnLoading(R.drawable.ic_stub) // resource or drawable
                .showImageForEmptyUri(R.drawable.ic_empty) // resource or drawable
                .showImageOnFail(R.drawable.ic_error) // resource or drawable
                .resetViewBeforeLoading() // default
              	.cacheInMemory()
              	.cacheOnDisc()
                .preProcessor(null).postProcessor(null).extraForDownloader(null)
                //.considerExifParams(false) // default
//                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) // default
//                .bitmapConfig(Bitmap.Config.ARGB_8888) // default
                .displayer(new RoundedBitmapDisplayer(20))
//                .decodingOptions(null).displayer(new SimpleBitmapDisplayer()) // default
                .handler(new Handler()) // default
                .build();

        return options;
    }

//    public void newVInitImageView() {
//        DisplayImageOptions options = new DisplayImageOptions.Builder()
//        // .cacheInMemory(false)
//        // .cacheOnDisc(true)
//                .imageScaleType(ImageScaleType.IN_SAMPLE_INT).bitmapConfig(Bitmap.Config.RGB_565)// ��ֹ�ڴ�����ģ�ͼƬ̫����������������������
//                // ��Bitmap.Config.ARGB_8888
//                // .showImageOnLoading(R.drawable.ic_launcher) // Ĭ��ͼƬ
//                .showImageForEmptyUri(R.drawable.icon_youku_home) // url���Օ���ʾ��ͼƬ���Լ�����drawable�����
//
//                .showImageOnFail(R.drawable.icon_youku_home)// ����ʧ����ʾ��ͼƬ
//                .displayer(new RoundedBitmapDisplayer(5)) // Բ�ǣ�����Ҫ��ɾ��
//                .build();
//
//        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(c).memoryCacheExtraOptions(480, 800)// �������ڴ��ͼƬ�Ŀ�͸߶�
//                // .discCacheExtraOptions(480, 800, null) // CompressFormat.PNG���ͣ�70������0-100��
//                .memoryCache(new WeakMemoryCache()).memoryCacheSize(2 * 1024 * 1024) // ���浽�ڴ���������
//                .discCacheSize(50 * 1024 * 1024). // ���浽�ļ����������
//                discCacheFileCount(1000) // �ļ�����
//                .defaultDisplayImageOptions(options). // �����options����һЩ��������
//                build();
//        ImageLoader.getInstance().init(config); // ��ʼ��
//    }
}
