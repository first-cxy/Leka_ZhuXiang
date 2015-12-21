package com.example.zhuxiangrobitclass.db;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

/**
 * 
 * 数据库辅助工�?
 * 
 */
public class DBHelper {
	private String dbFile;
	private String dbPath;
	private String dbOldFile;
	private String dataDirectory;
	private Context context;
	private static DBHelper dbHelper = null;

	public DBHelper(String dbFile) {
		this.dbFile = dbFile;
	}

	private DBHelper(Context context) {
		this.context = context;
		dataDirectory = Environment.getDataDirectory().getAbsolutePath();
		// dbPath=dataDirectory+"/"+dataDirectory+"/cn.ricard.mobile.activity/databases";
		// dbFile=dbPath+"/"+Value.DB_NAME;
		dbOldFile = dataDirectory + "/" + dataDirectory
				+ "/cn.ricard.mobile.activity/databases/richart_db";
		File file = new File(dbOldFile);
		if (file.exists()) {
			file.delete();
		}
		dbFile = dataDirectory + "/" + dataDirectory
				+ "/cn.ricard.mobile.activity/databases/richart_db1";
		Log.e("dbFile", dbFile);
		if (!dbExists()) {
//			copyDB();
		}
	}

	/**
	 * 单例
	 * 
	 * @param context
	 * @return
	 */
	public static DBHelper getInstance(Context context) {
		if (dbHelper == null) {
			dbHelper = new DBHelper(context);
			return dbHelper;
		} else {
			return dbHelper;
		}
	}

	/**
	 * 单例
	 * 
	 * @param context
	 * @return
	 */
	public static DBHelper getInstance(String dbFile) {
		if (dbHelper == null) {
			dbHelper = new DBHelper(dbFile);
			return dbHelper;
		} else {
			return dbHelper;
		}
	}

	/**
	 * 数据库是否存�?
	 * 
	 * @return
	 */
	private boolean dbExists() {
		File dataBaseFile = new File(dbFile);
		if (dataBaseFile.exists()) {
			// Log.e("path", dataBaseFile.getAbsolutePath());
			return true;
		} else {
			try {
				File dbPathFile = new File(dbPath);
				if (!dbPathFile.exists()) {
					dbPathFile.mkdir();
					Log.e("mkdir", dbPathFile.getAbsolutePath());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		}
	}

//	/**
//	 * 拷贝数据库文�?
//	 */
//	private void copyDB() {
//		Log.e("SQLiteDatabase:", "copy");
//		try {
//			InputStream is = context.getResources().openRawResource(
//					R.raw.richart_db1);
//			FileOutputStream fos = new FileOutputStream(dbFile);
//			byte[] buffer = new byte[8192];
//			int count = 0;
//			while ((count = is.read(buffer)) > 0) {
//				fos.write(buffer, 0, count);
//			}
//			fos.close();
//			is.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * 打开数据�?
	 * 
	 * @return
	 */
	public SQLiteDatabase openDB() {
		try {
			Log.e("SQLiteDatabase:", "open");
			SQLiteDatabase sdb = SQLiteDatabase.openOrCreateDatabase(dbFile,
					null);
			return sdb;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 关闭数据�?
	 * 
	 * @param sdb
	 */
	public void closeDB(SQLiteDatabase sdb) {
		try {
			Log.e("SQLiteDatabase:", "close");
			sdb.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @return
	 */
	public String getDBFile() {
		return this.dbFile;
	}
}
