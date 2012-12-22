package com.cape.hupushihuo;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;

public class LoadingActivity extends Activity {

	String url = "http://shihuo.hupu.com/item/list?sort=new&page=1";
	public static final String MY_EXTERANAL_STORGE = Environment
			.getExternalStorageDirectory() + "/hupushihuo";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loadingactivity);
		File file = new File(MY_EXTERANAL_STORGE);
		if (!file.exists()) {
			file.mkdir();
		}
		final Handler handler = new Handler(new Callback() {

			public boolean handleMessage(Message msg) {
				// TODO Auto-generated method stub
				switch (msg.what) {
				case 0:
					Intent intent = new Intent();
					intent.setClass(LoadingActivity.this, MainActivity.class);
					List<Good> goods = (List<Good>) msg.obj;
					intent.putExtra("list", (Serializable) goods);
					setIntent(intent);
					startActivity(intent);
					finish();
					break;

				default:
					break;
				}
				return false;
			}
		});

		Runnable runnable = new Runnable() {
			Message msg = new Message();

			public void run() {
				// TODO Auto-generated method stub
				msg.what = 0;
				try {

					Document doc = Jsoup.connect(url).timeout(20000).get();
					String temp = doc
							.select("script[type=text/javascript]:not([src~=[a-zA-Z0-9./\\s]+)")
							.first().html();
					int start = temp.indexOf("eval(");
					String[] s = new String[2];
					String json = temp.substring(start + 5, temp.length() - 2);
					JsonUtil jsonUtil = new JsonUtil(json);
					List<Good> goods = jsonUtil.getShoes();
					Message msg = new Message();
					msg.what = 0;
					msg.obj = goods;
					handler.sendMessage(msg);

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		};

		Thread t = new Thread(runnable);
		t.start();
	}
}
