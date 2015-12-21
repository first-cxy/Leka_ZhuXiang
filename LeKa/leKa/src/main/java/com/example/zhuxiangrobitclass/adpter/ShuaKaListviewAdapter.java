package com.example.zhuxiangrobitclass.adpter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhuxiangrobitclass1.R;
import com.example.zhuxiangrobitclass.util.BitmapScale;

public class ShuaKaListviewAdapter extends BaseAdapter {
    public Context c;
    public ArrayList<String> arr = new ArrayList<String>();
	private Bitmap readBitmapById;
	private String name;
	private static final String TAG = "ShuaKaListviewAdapter"; 
	
    public ShuaKaListviewAdapter(Context c, ArrayList<String> arr) {
        this.c = c;
        this.arr = arr;

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return arr.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        View convertView = LayoutInflater.from(c).inflate(R.layout.imageadpter, null);
        ImageView imageAdater = (ImageView) convertView.findViewById(R.id.imageAdater);
//        TextView imageAdater = (TextView) convertView.findViewById(R.id.imageAdater);
        // CARD-00:01, CARD-00:02
//		if (arr.get(arg0).equals("CARD-00:01")) {
//			readBitmapById = BitmapScale.ReadBitmapById(c, R.drawable.ka1);
//		
//		} else if (arr.get(arg0).equals("CARD-00:02")) {
//			readBitmapById = BitmapScale.ReadBitmapById(c, R.drawable.ka2);
//		} else if (arr.get(arg0).equals("CARD-00:03")) {
//			readBitmapById = BitmapScale.ReadBitmapById(c, R.drawable.ka3);
//		} else if (arr.get(arg0).equals("CARD-00:04")) {
//			readBitmapById = BitmapScale.ReadBitmapById(c, R.drawable.ka4);
//		} else if (arr.get(arg0).equals("CARD-00:05")) {
//			readBitmapById = BitmapScale.ReadBitmapById(c, R.drawable.ka5);
//		} else if (arr.get(arg0).equals("CARD-00:06")) {
//			readBitmapById = BitmapScale.ReadBitmapById(c, R.drawable.ka2);
//		}
        
        Map<String,Integer> namemap = new HashMap<String, Integer>();
      
        namemap.put("A0101", R.drawable.a0101);
        namemap.put("A0102", R.drawable.a0102);
        namemap.put("A0103", R.drawable.a0103);
        namemap.put("A0104", R.drawable.a0104);
        namemap.put("A0201", R.drawable.a0201);
        namemap.put("A0202", R.drawable.a0202);
        namemap.put("A0203", R.drawable.a0203);
//        namemap.put("A0204", R.drawable.a0204);
        namemap.put("A0301", R.drawable.a0301);
        namemap.put("A0302", R.drawable.a0302);
        namemap.put("A0303", R.drawable.a0303);
        namemap.put("A0304", R.drawable.a0304);
        namemap.put("A0401", R.drawable.a0401);
        namemap.put("A0402", R.drawable.a0402);
        namemap.put("A0403", R.drawable.a0403);
        namemap.put("A0404", R.drawable.a0404);
        namemap.put("A0501", R.drawable.a0501);
        namemap.put("A0502", R.drawable.a0502);
        namemap.put("A0503", R.drawable.a0503);
//        namemap.put("A0504", R.drawable.a0504);
        namemap.put("A0601", R.drawable.a0601);
//        namemap.put("A0602", R.drawable.a0602);
//        namemap.put("A0603", R.drawable.a0603);
//        namemap.put("A0604", R.drawable.a0604);
        namemap.put("A0701", R.drawable.a0701);
        namemap.put("A0702", R.drawable.a0702);
        namemap.put("A0703", R.drawable.a0703);
        namemap.put("A0704", R.drawable.a0704);
        namemap.put("A0801", R.drawable.a0801);
        namemap.put("A0802", R.drawable.a0802);
        namemap.put("A0803", R.drawable.a0803);
//        namemap.put("A0901", R.drawable.a0901);
        namemap.put("A0902", R.drawable.a0902);
        namemap.put("A0903", R.drawable.a0903);
        namemap.put("A1001", R.drawable.a1001);
        namemap.put("A1002", R.drawable.a1002);
        namemap.put("A1003", R.drawable.a1003);
        namemap.put("A1101", R.drawable.a1101);
        namemap.put("A1102", R.drawable.a1102);
        namemap.put("A1103", R.drawable.a1103);
        namemap.put("A1104", R.drawable.a1104);
        namemap.put("A1201", R.drawable.a1201);
        namemap.put("A1202", R.drawable.a1202);
        namemap.put("A1203", R.drawable.a1203);
        namemap.put("A1301", R.drawable.a1301);
        namemap.put("A1302", R.drawable.a1302);
        namemap.put("A1303", R.drawable.a1303);
        namemap.put("A1304", R.drawable.a1304);
        namemap.put("A1401", R.drawable.a1401);
        namemap.put("A1402", R.drawable.a1402);
        namemap.put("A1403", R.drawable.a1403);
  
        namemap.put("B0101", R.drawable.b0101);
        namemap.put("B0102", R.drawable.b0102);
        namemap.put("B0103", R.drawable.b0103);
        namemap.put("B0201", R.drawable.b0201);
        namemap.put("B0202", R.drawable.b0202);
        namemap.put("B0203", R.drawable.b0203);
        namemap.put("B0301", R.drawable.b0301);
        namemap.put("B0302", R.drawable.b0302);
        namemap.put("B0303", R.drawable.b0303);
        namemap.put("B0401", R.drawable.b0401);
        namemap.put("B0402", R.drawable.b0402);
        namemap.put("B0501", R.drawable.b0501);
        namemap.put("B0601", R.drawable.b0601);
        namemap.put("B0602", R.drawable.b0602);
        namemap.put("B0701", R.drawable.b0701);
        namemap.put("B0702", R.drawable.b0702);
        namemap.put("B0801", R.drawable.b0801);
        namemap.put("B1001", R.drawable.b1001);
        namemap.put("B1002", R.drawable.b1002);
        namemap.put("B1101", R.drawable.b1101);
        namemap.put("B1201", R.drawable.b1201);
        namemap.put("B1202", R.drawable.b1202);
        namemap.put("B1301", R.drawable.b1301);
        namemap.put("B1401", R.drawable.b1401);
        namemap.put("B1402", R.drawable.b1402);        
       
        namemap.put("M1000", R.drawable.stop);
        namemap.put("M1001", R.drawable.up);
        namemap.put("M1002", R.drawable.down);
        namemap.put("M1003", R.drawable.left);
        namemap.put("M1004", R.drawable.right);
        
        namemap.put("X0101", R.drawable.x0101);
        namemap.put("X0102", R.drawable.x0102);
        namemap.put("X0103", R.drawable.x0103);
        namemap.put("X0104", R.drawable.x0104);
        namemap.put("X0201", R.drawable.x0201);
        namemap.put("X0202", R.drawable.x0202);
        namemap.put("X0203", R.drawable.x0203);
        namemap.put("X0204", R.drawable.x0204);
        namemap.put("X0301", R.drawable.x0301);
        namemap.put("X0302", R.drawable.x0302);
        namemap.put("X0303", R.drawable.x0303);
        namemap.put("X0304", R.drawable.x0304);
        namemap.put("X0401", R.drawable.x0401);
        namemap.put("X0402", R.drawable.x0402);
        
        namemap.put("K0101", R.drawable.k0101);
        namemap.put("K0102", R.drawable.k0102);
        namemap.put("K0103", R.drawable.k0103);
        namemap.put("K0104", R.drawable.k0104);
        namemap.put("K0201", R.drawable.k0201);
        namemap.put("K0202", R.drawable.k0202);
        namemap.put("K0203", R.drawable.k0203);
        namemap.put("K0204", R.drawable.k0204);
        namemap.put("K0301", R.drawable.k0301);
        namemap.put("K0302", R.drawable.k0302);
        namemap.put("K0303", R.drawable.k0303);
        namemap.put("K0304", R.drawable.k0304);
        namemap.put("K0401", R.drawable.k0401);
        namemap.put("K0402", R.drawable.k0402);
        
        namemap.put("Z0101", R.drawable.z0101);
        namemap.put("Z0102", R.drawable.z0102);
        namemap.put("Z0103", R.drawable.z0103);
        namemap.put("Z0201", R.drawable.z0201);
        namemap.put("Z0202", R.drawable.z0202);
        namemap.put("Z0203", R.drawable.z0203);
        namemap.put("Z0301", R.drawable.z0301);
        namemap.put("Z0302", R.drawable.z0302);
        namemap.put("Z0303", R.drawable.z0303);
        namemap.put("Z0401", R.drawable.z0401);
        namemap.put("Z0402", R.drawable.z0402);
               
        namemap.put("Y0101", R.drawable.y0101);
        namemap.put("Y0201", R.drawable.y0201);
        namemap.put("Y0301", R.drawable.y0301);
        namemap.put("Y0401", R.drawable.y0401);
        namemap.put("Y0501", R.drawable.y0501);
        namemap.put("Y0601", R.drawable.y0601);
        namemap.put("Y0701", R.drawable.y0701);
        namemap.put("Y0801", R.drawable.y0801);
        namemap.put("Y0901", R.drawable.y0901);
        
        
        try {
        	JSONObject jsonObject = new  JSONObject(arr.get(arg0).toString()).getJSONObject("leka");
        	name = jsonObject.getString("name");
        	String url=jsonObject.getString("url");
        } catch (JSONException e) {
        	// TODO Auto-generated catch block
        	Log.i(TAG, "JSONException"+TAG,e);
        }
        Log.i("shuakalistview", name);
        
        if(namemap.containsKey(name)){
        	int cardpicture = namemap.get(name);
        	readBitmapById=BitmapScale.ReadBitmapById(c, cardpicture);
        }else{
        	Log.e(TAG, "No relative mapping!!!Return Empty");
        	readBitmapById=BitmapScale.ReadBitmapById(c, R.drawable.empty);
        }
//        readBitmapById=BitmapScale.ReadBitmapById(c, R.drawable.ka2);
		imageAdater.setImageBitmap(readBitmapById);
//        imageAdater.setText(name);
        return convertView;
    }

}
