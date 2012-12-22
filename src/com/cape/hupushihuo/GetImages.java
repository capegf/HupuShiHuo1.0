/*package com.cape.hupushihuo;

import java.util.ArrayList;
import java.util.Iterator;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

public class GetImages {
	Context context;

	public GetImages(Context context) {
		this.context = context;
	}

	public void start(String url, final ArrayList<ImageView> viewList) {
		new Handler() {

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				switch (msg.what) {
				case 0:

					break;

				default:
					break;
				}
			}

		}.post(new Runnable() {

			public void run() {
				// TODO Auto-generated method stub
				Iterator<ImageView> iterator = viewList.iterator();
				while (iterator.hasNext()) {
					ImageView iv = iterator.next();
					String s = (String) iv.getTag();
					String fileName = context.getName(s);
					String path = Environment.getExternalStorageDirectory()
							+ "/" + fileName;
					BitmapFactory.decodeFile(path);
				}
			}
		});
	}

	private String getName(String url) {
		String[] imgs = url.split("/");
		String fileName = imgs[6] + imgs[7];
		return fileName;
	}
}
*/