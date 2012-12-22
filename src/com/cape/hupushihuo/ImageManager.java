package com.cape.hupushihuo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class ImageManager {
	public static final String MY_EXTERANAL_STORGE = Environment
			.getExternalStorageDirectory() + "/hupushihuo";
	private static ImageManager imageManager = null;
	ExecutorService executorService = Executors.newFixedThreadPool(5);
	HttpUtil httpUtil = null;

	// Handler handler = null;

	// public Map<String, SoftReference<Drawable>> imageCache = new
	// HashMap<String, SoftReference<Drawable>>();

	public ImageManager() {
		// TODO Auto-generated constructor stub
	}

	public static ImageManager getInstance() {
		if (imageManager == null) {
			imageManager = new ImageManager();
		}
		return imageManager;
	}

	public void downloadImages(final ImageCallback imageCallback,
			final Good good, final int type) {

		final Handler handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				switch (msg.what) {
				case 1:
					imageCallback.imageLoaded((Bitmap) msg.obj);

					break;

				}

			}
		};

		executorService.submit(new Runnable() {

			public void run() {
				// TODO Auto-generated method stub
				String url = null;
				if (type == 1) {
					url = good.getImg_big_url();
				} else {
					url = good.getImg_url();
				}
				final String fileName = getName(url);
				Log.e("FIle_name", fileName);
				File imageFile = new File(MY_EXTERANAL_STORGE + "/" + fileName);
				Bitmap bitmap = writeToExternal(imageFile, url);
				Message msg = handler.obtainMessage(1);
				msg.obj = bitmap;
				handler.sendMessage(msg);
			}
		});

	}

	public interface ImageCallback {
		public void imageLoaded(Bitmap bitmap);
	}

	private Bitmap writeToExternal(File file, String url) {
		String path = file.getPath();
		Bitmap bit = null;

		if (!file.exists()) {
			httpUtil = new HttpUtil();
			try {
				FileOutputStream fos = null;
				int rt = 0;
				byte[] b = new byte[1024 * 10];

				InputStream is = httpUtil.httpConn(url);

				fos = new FileOutputStream(file);
				while ((rt = is.read(b)) != -1) {
					fos.write(b, 0, rt);
					fos.flush();
				}
				is.close();
				fos.close();
				// BitmapFactory.decodeByteArray(b, 0, rt);
				bit = BitmapFactory.decodeFile(path);
				// softReference = new SoftReference<Drawable>(d);
				// imageCache.put(path, softReference);
				if (bit == null) {
					file.delete();
				}

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (OutOfMemoryError e) {
				// TODO: handle exception
				// Log.e("草", "我要挂了！！");
				// softReference.clear();
			}
			return bit;
		} else {
			try {
				// if (imageCache.containsKey(path)) {
				// softReference = imageCache.get(path);
				// if (softReference.get() != null) {
				// Log.e("内存中存在", "ddddd");
				// return softReference.get();
				// }
				// }
				bit = BitmapFactory.decodeFile(path);

				if (bit != null) {
					return bit;
				} else {
					Log.e("Warn", "File is null");
					file.delete();
				}
				// softReference = new SoftReference<Drawable>(d);
				// imageCache.put(path, softReference);
				return bit;
			} catch (OutOfMemoryError e) {
				// softReference.clear();
			}
			return null;
		}

	}

	public static String getName(String url) {
		String[] imgs = url.split("/");
		String fileName = imgs[6] + imgs[7];
		return fileName;
	}

	public void removeCallBack() {

	}
}
