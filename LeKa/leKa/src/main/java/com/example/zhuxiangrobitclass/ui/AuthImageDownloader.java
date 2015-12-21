package com.example.zhuxiangrobitclass.ui;


import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;

import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.download.ImageDownloader.Scheme;

public class AuthImageDownloader extends BaseImageDownloader {
	private static final int MAX_REDIRECT_COUNT = 5;
	private String TAG = "AuthImageDownloader";
	public AuthImageDownloader(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public AuthImageDownloader(Context context, int connectTimeout,
			int readTimeout) {
		super(context, connectTimeout, readTimeout);
	}
 
	@Override
	public InputStream getStream(String imageUri, Object extra) throws IOException {
		switch (Scheme.ofUri(imageUri)) {
			case HTTP:
			case HTTPS:
				return getStreamFromNetwork(imageUri, extra);
			case FILE:
				return getStreamFromFile(imageUri, extra);
			case CONTENT:
				return getStreamFromContent(imageUri, extra);
			case ASSETS:
				return getStreamFromAssets(imageUri, extra);
			case DRAWABLE:
				return getStreamFromDrawable(imageUri, extra);
			case UNKNOWN:
			default:
				return getStreamFromOtherSource(imageUri, extra);
		}
	}
	protected InputStream getStreamFromNetwork(String imageUri, Object extra)
			throws IOException {
//		HttpURLConnection conn = connectTo(imageUri);
// 
//		int redirectCount = 0;
//		while (conn.getResponseCode() / 100 == 3
//				&& redirectCount < MAX_REDIRECT_COUNT) {
//			conn = connectTo(conn.getHeaderField("Location"));
//			redirectCount++;
//		}
 
		UsernamePasswordCredentials upc = new UsernamePasswordCredentials("admin","liujiwei");  //这一句使用用户名密码建立了一个数据
		AuthScope as = new AuthScope(null,-1);
		
		BasicCredentialsProvider bcp = new BasicCredentialsProvider();   //下面这一段我是抄的=。= 
		bcp.setCredentials(as, upc);
		
		DefaultHttpClient dhc= new DefaultHttpClient();
		dhc.setCredentialsProvider(bcp);   //给client设置了一个验证身份的部分
                /*-------------------------分割线---------------------------------*/
		HttpGet hg= new HttpGet(imageUri);
		Log.i(TAG, imageUri);
		HttpResponse hr=null;
//		int redirectCount = 0;
//		while (((HttpURLConnection) hr).getResponseCode() / 100 == 3
//				&& redirectCount < MAX_REDIRECT_COUNT
//				) {
//			redirectCount++;
//		}
		hr = dhc.execute(hg);
		return new BufferedInputStream(hr.getEntity().getContent(), BUFFER_SIZE);
		
//		return new BufferedInputStream(conn.getInputStream(), BUFFER_SIZE);
	}
	/**
	 * 获取带有用户验证信息的HttpURLConnection
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	private HttpURLConnection connectTo(String url) throws IOException {
		String encodedUrl = Uri.encode(url, ALLOWED_URI_CHARS);
		HttpURLConnection conn = (HttpURLConnection) new URL(encodedUrl)
				.openConnection();
                //这句话为urlconnection加入身份验证信息
//		String userPassword = "admin" + ":" + "liujiwei";
//		String authorization = "Basic " + Base64.encodeToString(userPassword.getBytes(),Base64.DEFAULT);
//		conn.setRequestProperty("Authorization",
//				"Basic" + authorization );
		conn.setConnectTimeout(connectTimeout);
		conn.setReadTimeout(readTimeout);
		conn.connect();
		return conn;
	}
}
