package com.example.zhuxiangrobitclass.net;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.util.Log;

import com.example.zhuxiangrobitclass.util.Encrypter;

/**
 * httpclient请求工具
 * 
 */
public class HttpClientHelper {
	// 请求超时时间
	private final static int REQUEST_TIMEOUT = 20000;
	// 读取超时时间
	private final static int READ_TIMEOUT = 30000;
	// 默认编码
	private final static String ENCODING = HTTP.UTF_8;

	private static final String TAG = "HttpClientHelper";
	
	public static String getRaw(String url) {
		HttpGet request = new HttpGet(url);
		HttpClient httpClient = HttpClientHandler.httpClient;
		HttpResponse response = null;
		try {
			response = httpClient.execute(request);
			Log.d("HttpClientHelper", "get,url = " + url
					+ "response.getStatusLine().getStatusCode() = "
					+ response.getStatusLine().getStatusCode());
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String entityStr = EntityUtils.toString(response.getEntity());
				Log.d("HttpClientHelper", "get,entityStr = " + entityStr);

				return entityStr;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String post(String url, Map<String, String> params) {
		if (params == null) {
			params = new HashMap<String, String>();
		}
		// params.put("token", SettingConstant.TOKEN);
		List<NameValuePair> param = new ArrayList<NameValuePair>(); // 参数
		// 添加参数
		Iterator<Entry<String, String>> iterator = params.entrySet().iterator();
		try {
			Encrypter encrypter = new Encrypter();
			String value = null;
			while (iterator.hasNext()) {
				Entry<String, String> entry = iterator.next();
				value = entry.getValue();
				if (value == null)
					value = "";
				param.add(new BasicNameValuePair(entry.getKey(), encrypter
						.encode(value)));
			}

			HttpPost request = new HttpPost(url);
			Log.d("Httppost", "url=" + url + ";params=" + params);
			HttpEntity entity = null;
			HttpClient client = HttpClientHandler.httpClient;

			entity = new UrlEncodedFormEntity(param, ENCODING);
			request.setEntity(entity);
			HttpResponse response = client.execute(request);
			Log.d("Httppost", "post,getStatusLine="
					+ response.getStatusLine().getStatusCode());
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String decodeResult = encrypter.decode(EntityUtils
						.toString(response.getEntity()));
				Log.d("Httppost", "decodeResult=" + decodeResult);
				return decodeResult;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String postRaw(String url, Map<String, String> params) {
		List<NameValuePair> param = new ArrayList<NameValuePair>(); // 参数
		// 添加参数
		Iterator<Entry<String, String>> iterator = params.entrySet().iterator();
		try {
			String value = null;
			while (iterator.hasNext()) {
				Entry<String, String> entry = iterator.next();
				value = entry.getValue();
				if (value == null)
					value = "";
				param.add(new BasicNameValuePair(entry.getKey(), value));
			}

			HttpPost request = new HttpPost(url);
			Log.d("Httppost", "url=" + url + ";params=" + params);
			HttpEntity entity = null;
			HttpClient client = HttpClientHandler.httpClient;

			entity = new UrlEncodedFormEntity(param, ENCODING);
			request.setEntity(entity);
			HttpResponse response = client.execute(request);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				return EntityUtils.toString(response.getEntity());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean download(String url, String path, String fileName) {
		Log.d("HttpClientHelper", "download,url = " + url);
		HttpGet request = new HttpGet(url);
		HttpClient httpClient = HttpClientHandler.httpClient;
		HttpResponse response = null;
		OutputStream os = null;
		try {
			response = httpClient.execute(request);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();
				InputStream in = entity.getContent();
				File pathFile = new File(path);
				if (!pathFile.exists()) {
					pathFile.mkdirs();
				}
				File imageFile = new File(path + "/" + fileName);
				if (!imageFile.exists()) {
					Log.d("Test", "download,imageFile = " + imageFile.getPath());
					imageFile.createNewFile();
				}

				byte[] bytes = new byte[1024];
				os = new FileOutputStream(imageFile);
				int len = 0;
				while ((len = in.read(bytes)) != -1) {
					os.write(bytes, 0, len);
				}
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (os != null)
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return false;
	}

	public static class HttpClientHandler {
		public static HttpClient httpClient;
		static {
			HttpParams params = new BasicHttpParams();
			ConnManagerParams.setTimeout(params, 10000);
			HttpConnectionParams.setConnectionTimeout(params, REQUEST_TIMEOUT);
			HttpConnectionParams.setSoTimeout(params, READ_TIMEOUT);

			SchemeRegistry schReg = new SchemeRegistry();
			schReg.register(new Scheme("http", PlainSocketFactory
					.getSocketFactory(), 80));
			schReg.register(new Scheme("https", SSLSocketFactory
					.getSocketFactory(), 443));

			ClientConnectionManager conMgr = new ThreadSafeClientConnManager(
					params, schReg);
			httpClient = new DefaultHttpClient(conMgr, params);
		}
	}

	public static String getwithAuth(String netUrl,String usr,String psw) throws Exception{
		
		
		URL url = new URL(netUrl);
		UsernamePasswordCredentials upc = new UsernamePasswordCredentials(usr,psw);  //这一句使用用户名密码建立了一个数据
		AuthScope as = new AuthScope(null,-1);
		
		BasicCredentialsProvider bcp = new BasicCredentialsProvider();   //下面这一段我是抄的=。= 
		bcp.setCredentials(as, upc);
		
		DefaultHttpClient dhc= new DefaultHttpClient();
		dhc.setCredentialsProvider(bcp);   //给client设置了一个验证身份的部分
                /*-------------------------分割线---------------------------------*/
		
		HttpGet hg= new HttpGet(netUrl);
		HttpResponse hr=null;
		hr = dhc.execute(hg);
		
		String line=null;
		StringBuilder builder = new StringBuilder();
		BufferedReader reader=null;
		reader = new BufferedReader(new InputStreamReader(hr.getEntity().getContent() ));  //获取数据
				
		while((line = reader.readLine()) != null) builder.append(line);

		String strContent=builder.toString();
		reader.close();

		Log.d(TAG, strContent);
		return strContent;
	}
	
	public static void closeAllConnection() {
		ClientConnectionManager conMgr = null;
		if ((conMgr = HttpClientHandler.httpClient.getConnectionManager()) != null) {
			conMgr.shutdown();
		}
	}

	public static String downloadUrl(String myurl) throws IOException {
		InputStream is = null;
		HttpURLConnection conn = null;
		try {
			URL url = new URL(myurl);
			conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(10000 /* milliseconds */);
			conn.setConnectTimeout(15000 /* milliseconds */);
			conn.setRequestMethod("POST");
			conn.setDoInput(true);
			conn.connect();
			// Starts the query
			int response = conn.getResponseCode();
			InputStream in = new BufferedInputStream(conn.getInputStream());
			// Log.d("DEBUG_TAG", "The response is: " + response);
			// is = conn.getInputStream();

			// Convert the InputStream into a string
			String contentAsString = readIt(in);
			// String contentAsString = readIt(is);
			return contentAsString;

			// Makes sure that the InputStream is closed after the app is
			// finished using it.
		} finally {
			if (is != null) {
				is.close();
			}

			if (conn != null) {
				conn.disconnect();
			}
		}
	}
	
	public String executeGet(String url) {
		String resultStr = null;
		BufferedReader reader = null;
		Log.e(TAG, url);
		try {
			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet();
			// request.setURI(new URI( url));
			// HttpResponse response = client.execute(request);
			// reader = new BufferedReader(new InputStreamReader(response
			// .getEntity().getContent()));
			URL uri = new URL(url);
			HttpURLConnection con = (HttpURLConnection) uri.openConnection();
			con.setConnectTimeout(3 * 1000);// 设置连接超时
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setRequestMethod("GET");
			con.setUseCaches(true);
			// StringBuffer strBuffer = new StringBuffer("");
			// String line = null;
			// while ((line = reader.readLine()) != null) {
			// strBuffer.append(line);
			// }
			// result = strBuffer.toString();
			InputStreamReader in = new InputStreamReader(con.getInputStream());
			BufferedReader bufReader = new BufferedReader(in);
			String lineStr = "";
			resultStr = "";
			while ((lineStr = bufReader.readLine()) != null) {
				resultStr += lineStr;
			}

		} catch (Exception e) {
			Log.i(TAG, "Exception",e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
					reader = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return resultStr;
	}
	
	public String executeActionGet(String myurl) {
		InputStream is = null;
		HttpURLConnection conn = null;
		try {
			URL url = new URL(myurl);
			conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(10000 /* milliseconds */);
			conn.setConnectTimeout(15000 /* milliseconds */);
			conn.setRequestMethod("GET");
			conn.setDoInput(true);
			// Starts the query
			conn.connect();
			
			int response = conn.getResponseCode();
			// Log.d("DEBUG_TAG", "The response is: " + response);
			is = conn.getInputStream();

			// Convert the InputStream into a string
			String contentAsString = readIt(is);
			return contentAsString;
		}catch(Exception e){
			Log.i(TAG, "Exception",e);
			return null;
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(conn != null){
				conn.disconnect();
			}
		}
		
	}

	// Reads an InputStream and converts it to a String.
	public static String readIt(InputStream stream) throws IOException,
			UnsupportedEncodingException {
		StringWriter writer = new StringWriter();
		IOUtils.copy(stream, writer, "UTF-8");
		return writer.toString();
	}
}