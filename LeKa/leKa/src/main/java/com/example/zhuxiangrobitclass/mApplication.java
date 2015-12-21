/**
 * 
 */
package com.example.zhuxiangrobitclass;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.util.Log;

import com.example.zhuxiangrobitclass.ui.AuthImageDownloader;
import com.example.zhuxiangrobitclass.util.ImageLoadConfog;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * @author fengxb 2014年10月11日
 */
public class mApplication extends Application {

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Application#onCreate()
     */
    public static  ImageLoaderConfiguration config ;
    
	private String TAG ="mApplication";
    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
//        ImageLoadConfog imageLoadConfog = new ImageLoadConfog(this);
        config = new ImageLoaderConfiguration.Builder(this)
        .memoryCacheExtraOptions(480, 800)
        .discCacheExtraOptions(1024, 1024, CompressFormat.JPEG, 75)
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
        //.memoryCacheSizePercentage(13)
        // default
        // default
        .discCacheSize(50 * 1024 * 1024).discCacheFileCount(100)
        .discCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
        .imageDownloader(new AuthImageDownloader(this)) // default
        .defaultDisplayImageOptions(ImageLoadConfog.geteDisPlayImageOption()) // default
        //
        //.enableLogging()
        // .writeDebugLogs()
        .build();
//        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this)); // 初始化
     //   ImageLoader.getInstance().init(config); // 初始化
        
//        ImageLoader.getInstance().init(imageLoadConfog.getImageLoaderConfiguration()); // 初始化
    }



	public static mApplication mmaApplication;

    public static mApplication getMapplecation() {
        if (mmaApplication != null) {
            mmaApplication = new mApplication();
        }
        return mmaApplication;

    }

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Application#onConfigurationChanged(android.content.res.Configuration)
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        super.onConfigurationChanged(newConfig);
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Application#onLowMemory()
     */
    @Override
    public void onLowMemory() {
        // TODO Auto-generated method stub
        super.onLowMemory();
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Application#onTrimMemory(int)
     */
    @SuppressLint("NewApi")
    @Override
    public void onTrimMemory(int level) {
        // TODO Auto-generated method stub
        super.onTrimMemory(level);
    }

    public static String baseUrl ;

    public static String deviceType = "ev3";
    
    public static boolean ServerConnected = false;
    
    public boolean isServerConnected() {
		return ServerConnected;
	}

	public void setServerConnected(boolean serverConnected) {
		this.ServerConnected = serverConnected;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	/**
     * @return the baseUrl
     */
    public String getBaseUrl() {
    	SharedPreferences BaseUrlSetting = getSharedPreferences("BaseUrlSetting", 0);
    	String baseUrl = BaseUrlSetting.getString("baseUrl", "http://192.168.199.105/happyhomework/");
    	return baseUrl;
    }

    /**
     * @param baseUrl
     *            the baseUrl to set
     */
    public void setBaseUrl(String baseUrl) {
    	SharedPreferences BaseUrlSetting = getSharedPreferences("BaseUrlSetting", 0);
    	SharedPreferences.Editor editor = BaseUrlSetting.edit();
    	//http://admin:liujiwei@leka.5zixi.cn/happyhomework/index.php/user_manager/login?username=liujiwei&password=1028
    	if (baseUrl == null ) {
    		this.baseUrl = "http://192.168.199.105/happyhomework/";
    		Log.i("mApplication set", baseUrl+"");
        }else{
        	if(baseUrl.equals("leka.maxtain.com")){
        		this.baseUrl ="http://"+baseUrl+"/";
        	}else if(baseUrl.equals("leka.5zixi.cn")){
        		this.baseUrl ="http://"+baseUrl+"/happyhomework/";
        	}else if(baseUrl.equals("202.204.62.241:9999")){
        		this.baseUrl ="http://"+baseUrl+"/happyhomework/";
        	}else {
        		this.baseUrl ="http://"+baseUrl+"/";
        	}
        	Log.i("mApplication set", this.baseUrl+"");
        }
    	editor.putString("baseUrl", this.baseUrl);
    	editor.commit();
    }
    
    public  String getIsOpen() {
		SharedPreferences IsOpenSetting = getSharedPreferences("IsOpenSetting", 0);
    	String isOpen = IsOpenSetting.getString("isOpen", "F");
		return isOpen;
	}
	public  void setIsOpen(String isOpen) {
		SharedPreferences IsOpenSetting = getSharedPreferences("IsOpenSetting", 0);
		SharedPreferences.Editor editor = IsOpenSetting.edit();
		editor.putString("isOpen", isOpen);
		editor.commit();
//		this.islogin = islogin;
	}
    
//    private static String islogin = "F";
	public  String getIslogin() {
		SharedPreferences IsLoginSetting = getSharedPreferences("IsLoginSetting", 0);
    	String islogin = IsLoginSetting.getString("islogin", "F");
		return islogin;
	}
	public  void setIslogin(String islogin) {
		SharedPreferences IsLoginSetting = getSharedPreferences("IsLoginSetting", 0);
		SharedPreferences.Editor editor = IsLoginSetting.edit();
		editor.putString("islogin", islogin);
		editor.commit();
//		this.islogin = islogin;
	}

//	private static String username;
	public String getUsername() {
		SharedPreferences UsernameSetting = getSharedPreferences("UsernameSetting", 0);
    	String username = UsernameSetting.getString("username", "");
		return username;
	}
	public void setUsername(String username) {
		SharedPreferences UsernameSetting = getSharedPreferences("UsernameSetting", 0);
		SharedPreferences.Editor editor = UsernameSetting.edit();
		editor.putString("username", username);
		editor.commit();
//		this.username = username;
	}

//	public static String userid = "-1";
//	public String getUserid() {
//		return userid;
//	}
//	public void setUserid(String userid) {
//		this.userid = userid;
//	}
    
	private static String status = "";
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	private static String mode = "cancel";
	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}
	
	
//	public String getRegcode() {
//		SharedPreferences RegcodeSetting = getSharedPreferences("RegcodeSetting", 0);
//    	String regcode = RegcodeSetting.getString("regcode", "");
//		return regcode;
//	}
//
//	public void setRegcode(String regcode) {
//		SharedPreferences RegcodeSetting = getSharedPreferences("RegcodeSetting", 0);
//		SharedPreferences.Editor editor = RegcodeSetting.edit();
//		editor.putString("regcode", regcode);
//		editor.commit();
////		this.regcode = regcode;
//	}
	
	public String getRole() {
		SharedPreferences RoleSetting = getSharedPreferences("RoleSetting", 0);
    	String role = RoleSetting.getString("role", "C");
		return role;
	}

	public void setRole(String role) {
		SharedPreferences RoleSetting = getSharedPreferences("RoleSetting", 0);
		SharedPreferences.Editor editor = RoleSetting.edit();
		editor.putString("role", role);
		editor.commit();
//		this.regcode = regcode;
	}
	
	public String getKemus() {
		SharedPreferences KemusSetting = getSharedPreferences("KemusSetting", 0);
    	String kemus = KemusSetting.getString("kemus", "");
		return kemus;
	}

	public void setKemus(String kemus) {
		SharedPreferences KemusSetting = getSharedPreferences("KemusSetting", 0);
		SharedPreferences.Editor editor = KemusSetting.edit();
		editor.putString("kemus", kemus);
		editor.commit();
//		this.regcode = regcode;
	}
	
}
