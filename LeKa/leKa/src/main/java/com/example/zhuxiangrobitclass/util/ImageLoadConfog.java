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
 * @author fengxb 2014年10月15日
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
//                .imageScaleType(ImageScaleType.IN_SAMPLE_INT).bitmapConfig(Bitmap.Config.RGB_565)// 防止内存溢出的，图片太多就这这个。还有其他设置
//                // 如Bitmap.Config.ARGB_8888
//                // .showImageOnLoading(R.drawable.ic_launcher) // 默认图片
//                .showImageForEmptyUri(R.drawable.icon_youku_home) // url爲空會显示该图片，自己放在drawable里面的
//
//                .showImageOnFail(R.drawable.icon_youku_home)// 加载失败显示的图片
//                .displayer(new RoundedBitmapDisplayer(5)) // 圆角，不需要请删除
//                .build();
//
//        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(c).memoryCacheExtraOptions(480, 800)// 缓存在内存的图片的宽和高度
//                // .discCacheExtraOptions(480, 800, null) // CompressFormat.PNG类型，70质量（0-100）
//                .memoryCache(new WeakMemoryCache()).memoryCacheSize(2 * 1024 * 1024) // 缓存到内存的最大数据
//                .discCacheSize(50 * 1024 * 1024). // 缓存到文件的最大数据
//                discCacheFileCount(1000) // 文件数量
//                .defaultDisplayImageOptions(options). // 上面的options对象，一些属性配置
//                build();
//        ImageLoader.getInstance().init(config); // 初始化
//    }
}
