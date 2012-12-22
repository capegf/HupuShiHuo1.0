package com.cape.hupushihuo;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import net.tsz.afinal.FinalBitmap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cape.hupushihuo.ImageManager.ImageCallback;

public class MainActivity extends Activity {
	private RelativeLayout llCcasecade = null;
	private LinearLayout lvCasecade1 = null;
	private LinearLayout lvCasecade2 = null;
	// private RelativeLayout lvCasecade3 = null;
	LayoutParams lpp = null;
	// private LazyScrollView srScrollView;
	// private Queue<ImageView> queue = null;
	private Display display;
	private int casecadeWidth;
	View vi = null;
	int start;
	int num;
	List<Good> goods;
	Good good;
	Iterator<Good> iterator;
	int page = 1;
	boolean canBottom = true;
	boolean flag = true;
	private ArrayList<View> viList = null;

	// int count = 0;
	boolean firstRun = true;
	GestureDetector gesture;
	int count = 0;
	int j = 0;
	private Vector<ImageView> viewList = new Vector<ImageView>();
	private Vector<Bitmap> bitmapList = new Vector<Bitmap>();
	// private WeakReference<ImageView> weakReference = new
	// WeakReference<ImageView>(
	// null);
	private LazyScrollView myScrollView = null;
	Bundle savedInstanceState = null;
	ProgressBar pb2;
	TextView shoestitle;
	ImageView freight_payer;
	TextView like;
	TextView sold;
	ImageView imgChecked;
	ImageView ib;
	TextView price;
	AlphaAnimation aa;
	LinearLayout l2;
	ImageManager imageManager;

	// List<Good> goods = null;

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		this.savedInstanceState = savedInstanceState;
		setContentView(R.layout.main);
		Log.e("ONCREATE", "ONCREATE");
		display = this.getWindowManager().getDefaultDisplay();
		casecadeWidth = display.getWidth() / 2;
		// queue = new LinkedList<ImageView>();
		gesture = new GestureDetector(MainActivity.this,
				new OnGestureListener() {

					public boolean onSingleTapUp(MotionEvent e) {
						// TODO Auto-generated method stub
						return false;
					}

					public void onShowPress(MotionEvent e) {
						// TODO Auto-generated method stub

					}

					public boolean onScroll(MotionEvent e1, MotionEvent e2,
							float distanceX, float distanceY) {
						// TODO Auto-generated method stub
						return false;
					}

					public void onLongPress(MotionEvent e) {
						// TODO Auto-generated method stub

					}

					public boolean onFling(MotionEvent e1, MotionEvent e2,
							float velocityX, float velocityY) {
						// TODO Auto-generated method stub
						final int FLING_MIN_DISTANCE = 100;// X或者y轴上移动的距离(像素)
						// final int FLING_MIN_VELOCITY = 200;
						// x或者y轴上的移动速度(像素/秒)
						if ((e1.getY() - e2.getY()) > FLING_MIN_DISTANCE) {

						}
						if ((e2.getY() - e1.getY()) > FLING_MIN_DISTANCE) {
						}
						return false;
					}

					public boolean onDown(MotionEvent e) {
						// TODO Auto-generated method stub
						// Log.e("Action", e.getAction() + "");

						if (e.getAction() == MotionEvent.ACTION_DOWN) {
							int mLastY = myScrollView.getScrollY();// 赋值给mLastY
							if (mLastY >= (llCcasecade.getHeight() - myScrollView
									.getHeight())) {
								if (canBottom == true) {
									canBottom = false;
									Iterator<Good> iterator = goods.iterator();
									if (iterator.hasNext() == false) {
										updateImage();
										return false;
									}
									for (int i = 0; i <= 5; i++) {
										if (iterator.hasNext()) {

											Good goods = iterator.next();

											Message msg = handler
													.obtainMessage(0);
											msg.obj = goods;
											handler.sendMessage(msg);
											iterator.remove();
										}
									}

								} else {
									return false;
								}
								canBottom = true;
							}
							// }.start();
							// }
						}
						return false;
					}
				});
		findView();
		thread.start();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return gesture.onTouchEvent(event);
	}

	@SuppressLint("NewApi")
	private void findView() {
		imageManager = ImageManager.getInstance();

		llCcasecade = (RelativeLayout) this.findViewById(R.id.llCcasecade);
		// llCcasecade.setFitsSystemWindows(true);
		lvCasecade1 = (LinearLayout) this.findViewById(R.id.casecade1);
		// lvCasecade1.setFitsSystemWindows(true);
		lvCasecade2 = (LinearLayout) this.findViewById(R.id.casecade2);
		// lvCasecade2.setFitsSystemWindows(true);
		// lvCasecade3 = (RelativeLayout) this.findViewById(R.id.casecade3);
		LayoutParams lp1 = lvCasecade1.getLayoutParams();
		myScrollView = (LazyScrollView) this.findViewById(R.id.myScrollView);
		myScrollView.setGestureDetector(gesture);
		// myScrollView.getView();
		// srScrollView.setForwardListener(onForwardListener);
		// srScrollView.setOnScrollListener(onScrollListener);
		lp1.width = casecadeWidth;
		lvCasecade1.setLayoutParams(lp1);
		LayoutParams lp2 = lvCasecade2.getLayoutParams();
		lp2.width = casecadeWidth;
		lvCasecade2.setLayoutParams(lp2);

		// LayoutParams lp3 = lvCasecade3.getLayoutParams();
		// lp3.width = casecadeWidth;
		// lvCasecade3.setLayoutParams(lp3);
		Intent intent = getIntent();
		goods = (List<Good>) intent.getSerializableExtra("list");
		// Log.e("size", goods.size() + "");

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	Thread thread = new Thread() {

		@Override
		public void run() {
			// while (firstRun) {
			Iterator<Good> iterator = goods.iterator();
			for (int i = 0; i <= 10; i++) {
				if (iterator.hasNext()) {
					Good goods = iterator.next();
					Message msg = handler.obtainMessage(0);
					msg.obj = goods;
					msg.what = 0;
					handler.sendMessage(msg);
					iterator.remove();
				}

			}
			// firstRun = false;
			// }
		}
	};
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case 0:
				getImage((Good) msg.obj, j);
				j++;
				if (j >= 2) {
					j = 0;
				}
				Log.e("hhhhh", lvCasecade1.getHeight() + "");
				recycleBitmap();
				break;

			case 1:
				// recycleMemory();

				break;
			}

		}

	};

	@SuppressLint("NewApi")
	private void getImage(final Good good, final int j) {

		final LinearLayout ll = (LinearLayout) LayoutInflater.from(
				getApplicationContext()).inflate(R.layout.item, null);

		final ProgressBar pb = (ProgressBar) ll.findViewById(R.id.pb);
		final ImageView shoeImageView = (ImageView) ll.findViewById(R.id.shoes);

		final ImageView brandImageView = (ImageView) ll
				.findViewById(R.id.brand);
		final TextView shoeNameTextView = (TextView) ll
				.findViewById(R.id.shoesname);
		final TextView shoeStyleTextView = (TextView) ll
				.findViewById(R.id.style);
		final TextView shoePriceTextView = (TextView) ll
				.findViewById(R.id.price);
		final ImageView isCheckedImageView = (ImageView) ll
				.findViewById(R.id.isChecked);
		final ImageView goTo = (ImageView) ll.findViewById(R.id.go);
		if (j == 0) {
			lvCasecade1.addView(ll);

		} else if (j == 1) {
			lvCasecade2.addView(ll);
		}
		// } else if (j == 2) {
		// lvCasecade3.addView(iv);
		// lvCasecade3.invalidate();
		// }
		// final ImageView isChecked = (ImageView)
		// iv.findViewWithTag(url);
		isCheckedImageView.setTag(good.getVerified());
		shoeImageView.setTag(good.getImg_url());
		brandImageView.setTag(good.getBrand_id());
		shoeNameTextView.setTag(good.getName());
		shoeStyleTextView.setTag(good.getCategory_name());
		shoePriceTextView.setTag(good.getPrice());
		final ImageView brand = (ImageView) brandImageView.findViewWithTag(good
				.getBrand_id());
		int i = (Integer) brandImageView.getTag();
		brand.setImageBitmap(checkBrand(i));

		final TextView style = (TextView) shoeStyleTextView
				.findViewWithTag(good.getCategory_name());
		style.setText((String) shoeStyleTextView.getTag());

		final TextView shoeName = (TextView) shoeNameTextView
				.findViewWithTag(good.getName());
		shoeName.setText((String) shoeName.getTag());

		final TextView shoePrice = (TextView) shoePriceTextView
				.findViewWithTag(good.getPrice());
		shoePrice.setText((String) shoePriceTextView.getTag());
		final ImageView isCheck = (ImageView) isCheckedImageView
				.findViewWithTag(good.getVerified());
		final ImageView iv = (ImageView) shoeImageView.findViewWithTag(good
				.getImg_url());

		// isChecked.setTag(url);
		imageManager.downloadImages(new ImageCallback() {

			public void imageLoaded(final Bitmap bitmap) {
				// TODO Auto-generated method stub

				if (iv != null && bitmap != null) {
					int oldwidth = bitmap.getWidth();
					int oldheight = bitmap.getHeight();
					LayoutParams lp = iv.getLayoutParams();
					lp.height = (oldheight * casecadeWidth) / oldwidth;
					iv.setLayoutParams(lp);
					// iv.setAlpha(0);
					iv.setVisibility(View.VISIBLE);
					pb.setVisibility(View.GONE);
					goTo.setOnTouchListener(new OnTouchListener() {

						public boolean onTouch(View v, MotionEvent event) {
							// TODO Auto-generated method stub
							switch (event.getAction()) {
							case MotionEvent.ACTION_DOWN:
								goTo.setImageResource(R.drawable.bnt2);
								break;
							case MotionEvent.ACTION_UP:
								Log.e("up", "up");
								LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
								vi = inflater.inflate(R.layout.layout_float,
										null);
								LinearLayout.inflate(getApplicationContext(),
										R.layout.layout_float, null);
								LinearLayout l = new LinearLayout(
										getApplicationContext());
								l2 = new LinearLayout(getApplicationContext());
								l.setPadding(0, 100, 0, 50);
								l2.setBackgroundColor(Color.BLACK);
								ImageView i = new ImageView(
										getApplicationContext());
								l2.setClickable(true);

								l2.setAlpha(0.6f);
								l2.setVisibility(View.GONE);
								// LinearLayout ll = (LinearLayout)
								// vi.findViewById(R.id.test);
								lpp = new LayoutParams(
										LayoutParams.WRAP_CONTENT,
										LayoutParams.WRAP_CONTENT);
								android.widget.LinearLayout.LayoutParams lll = new android.widget.LinearLayout.LayoutParams(
										LayoutParams.FILL_PARENT,
										LayoutParams.FILL_PARENT);
								l.addView(vi);
								l.setLayoutParams(lpp);

								addContentView(l2, lll);
								addContentView(l, lpp);
								pb2 = (ProgressBar) vi.findViewById(R.id.pb);
								shoestitle = (TextView) vi
										.findViewById(R.id.title);
								freight_payer = (ImageView) vi
										.findViewById(R.id.freight_payer);
								like = (TextView) vi
										.findViewById(R.id.likecount);
								sold = (TextView) vi
										.findViewById(R.id.soldcount);
								imgChecked = (ImageView) vi
										.findViewById(R.id.isChecked);

								ib = (ImageView) vi.findViewById(R.id.goshop);
								final ImageView shoesImage = (ImageView) vi
										.findViewById(R.id.shoes);
								// shoesImage.setLayoutParams(new
								// LayoutParams(width, height))
								price = (TextView) vi.findViewById(R.id.price);
								aa = new AlphaAnimation(0.0f, 1.0f);
								aa.setDuration(1000);
								vi.setAnimation(aa);

								l2.setVisibility(View.VISIBLE);

								goTo.setImageResource(R.drawable.bnt1);

								String title = good.getTitle();

								String likeCount = good.getLikeCount();
								String saleCount = good.getSoldCount();
								String isFreight_payer = good.getFreightPayer();
								final String getTaobaoUrl = good.getUrl();
								final boolean isChecked = good.getVerified();
								String aPrice = good.getPrice();
								price.setText(aPrice);
								shoestitle.setText(title);
								if (isFreight_payer.equals("1")) {
									freight_payer.setVisibility(View.VISIBLE);
								}

								like.setText("喜欢:" + likeCount);

								sold.setText("已售出:" + saleCount + "件");
								vi.setVisibility(View.VISIBLE);
								aa.startNow();
								ib.setOnTouchListener(new OnTouchListener() {

									public boolean onTouch(View v,
											MotionEvent event) {
										// TODO Auto-generated method stub
										switch (event.getAction()) {
										case MotionEvent.ACTION_DOWN:
											ib.setImageResource(R.drawable.urlbnt2);
											break;
										case MotionEvent.ACTION_UP:
											ib.setImageResource(R.drawable.urlbnt1);
											Uri uri = Uri.parse(getTaobaoUrl);
											Intent intent = new Intent(
													Intent.ACTION_VIEW, uri);
											startActivity(intent);

											break;
										default:
											break;
										}

										return false;
									}
								});

								// ImageManager im = ImageManager.getInstance();
								imageManager.downloadImages(
										new ImageCallback() {

											public void imageLoaded(
													Bitmap bitmap) {
												// TODO Auto-generated method
												// stub
												FinalBitmap finalBitmap = FinalBitmap
														.create(getApplicationContext());
												FinalBitmap bitmap2 = finalBitmap
														.configLoadingImage(bitmap);
												finalBitmap.display(
														shoeImageView, null);
												shoesImage
														.setImageBitmap(bitmap);
												shoesImage
														.setVisibility(View.VISIBLE);

												pb2.setVisibility(View.GONE);
												if (isChecked) {
													imgChecked
															.setVisibility(View.VISIBLE);
												}
											}
										}, good, 1);

								break;
							}

							return true;

						}

					});

					setImage(iv, bitmap);
					if (((Boolean) isCheckedImageView.getTag()) == true) {
						isCheck.setVisibility(View.VISIBLE);
					}
					Log.e("height", bitmap.getHeight() + "");
					aa = new AlphaAnimation(0.0f, 1.0f);
					aa.setDuration(1000);
					iv.setAnimation(aa);

					aa.startNow();

				}

			}
		}, good, 0);

	}

	private void updateImage() {

		Thread t = new Thread() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					page++;
					String url = "http://shihuo.hupu.com/item/list?sort=new&page="
							+ page;
					Document doc = Jsoup.connect(url).timeout(20000).get();
					String temp = doc
							.select("script[type=text/javascript]:not([src~=[a-zA-Z0-9./\\s]+)")
							.first().html();
					int start = temp.indexOf("eval(");
					String json = temp.substring(start + 5, temp.length() - 2);
					JsonUtil jsonUtil = new JsonUtil(json);
					goods = jsonUtil.getShoes();
					// doc.remove();
					// count = 0;
					Iterator<Good> iterator = goods.iterator();

					for (int i = 0; i <= 10; i++) {
						if (iterator.hasNext()) {
							Good goods = iterator.next();
							Message msg = handler.obtainMessage(0);
							msg.obj = goods;
							handler.sendMessage(msg);
							iterator.remove();
						}
					}
					canBottom = true;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return;
			}

		};
		t.start();
	}

	private Bitmap getBrandImage(String brandName) {
		try {

			InputStream is = getResources().getAssets().open(brandName);
			Bitmap bm = BitmapFactory.decodeStream(is);
			is.close();
			return bm;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private Bitmap checkBrand(int brandId) {
		return getBrandImage(brandId + ".gif");
	}

	// private void recycleMemory() {
	// int showCount = 10;
	//
	// for (int i = 0; i < viewList.size() - 6; i++) {
	// ImageView iv = (ImageView) viewList.(i);
	// iv.setImageDrawable(null);
	// // viewList.remove(i);
	// }

	// viewList = new ArrayList();

	// for (int i = 0; i < drawableList.size() - 50; i++) {
	//
	// Drawable drawable = (Drawable) drawableList.get(i);
	// // 杩欓噷灏卞紑濮嬮噴鏀綽itmap 鎵�崰鐨勫唴瀛樹簡
	// // 浠巐ist涓幓闄�
	// drawable = null;
	// drawableList.remove(i);
	// }

	// bitmapList = new ArrayList();
	// }

	private void setImage(ImageView iv, Bitmap bitmap) {
		viewList.add(iv);
		bitmapList.add(bitmap);
		iv.setImageBitmap(bitmap);

		// bitmap.recycle();
		// Log.e("viewList", viewList.size() + "");
	}

	private void recycleBitmap() {
		if (bitmapList.size() > 12) {
			for (int i = 0; i < bitmapList.size() - 6; i++) {
				Bitmap bitmap = (Bitmap) bitmapList.get(i);
				// 杩欓噷灏卞紑濮嬮噴鏀綽itmap 鎵�崰鐨勫唴瀛樹簡
				if (!bitmap.isRecycled()) {

					System.out.println("recycle ");
					bitmap = null;
					bitmapList.remove(i);
				}
			}
			System.gc(); // 提醒系统及时回收
			// for (int i = 0; i < viewList.size() - 5; i++) {
			// ImageView iv = (ImageView) viewList.get(i);
			//
			// // 浠巐ist涓幓闄�
			// viewList.remove(i);
			// }
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.e("ONPAUSE", "ONPAUSE");

		Iterator<ImageView> iterator = viewList.iterator();
		while (iterator.hasNext()) {
			final ImageView iv = iterator.next();
			getImgBack(new ImgCallBack() {

				public void imgLoaded(Bitmap bitmap) {
					// TODO Auto-generated method stub
					iv.setImageBitmap(bitmap);
					bitmapList.add(bitmap);

					// Log.e("OK", "OK");
				}
			}, iv);

		}

		recycleBitmap();
		// System.exit(1);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		// onPause();
		super.onDestroy();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		Log.e("ONREASTART", "ONREASTART");
		// recycleBitmap();
		// ExecutorService executorService = Executors.newFixedThreadPool(5);
		// executorService.submit(new Runnable() {
		//
		// public void run() {
		// TODO Auto-generated method stub

		// }
		// });

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.e("ONSTART", "ONSTART");

		// TODO Auto-generated method stub
		// Message msg = handler.obtainMessage(1);
		// msg.obj = bitmap;
		// handler.sendMessage(msg);
	}

	public String getName(String url) {
		String[] imgs = url.split("/");
		String fileName = imgs[6] + imgs[7];
		return fileName;
	}

	private void getBitmapBack(final ImageView iv) {

		// Thread t = new Thread() {
		//
		// @Override
		// public void run() {
		// TODO Auto-generated method stub

		String s = (String) iv.getTag();
		String fileName = MainActivity.this.getName(s);
		String path = Environment.getExternalStorageDirectory().getPath()
				+ "/hupushihuo" + "/" + fileName;
		Bitmap bitmap = BitmapFactory.decodeFile(path);
		iv.setImageBitmap(bitmap);
		bitmapList.add(bitmap);
	}

	private interface ImgCallBack {
		public void imgLoaded(Bitmap bitmap);
	}

	public void getImgBack(final ImgCallBack imgCallBack, final ImageView iv) {

		final Handler ha = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				switch (msg.what) {
				case 0:

					imgCallBack.imgLoaded((Bitmap) msg.obj);

					break;

				default:
					break;
				}

			}
		};

		Thread t = new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String s = (String) iv.getTag();

				String fileName = MainActivity.this.getName(s);
				String path = Environment.getExternalStorageDirectory()
						.getPath() + "/hupushihuo" + "/" + fileName;
				Bitmap bitmap = BitmapFactory.decodeFile(path);
				Message msg = ha.obtainMessage(0);
				msg.obj = bitmap;
				ha.sendMessage(msg);

			}
		};
		t.start();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (vi != null && vi.isShown()) {
				pb2.setVisibility(View.VISIBLE);

				vi.setVisibility(View.GONE);
				l2.setVisibility(View.GONE);
				imgChecked.setVisibility(View.GONE);
				freight_payer.setVisibility(View.GONE);
				aa.reset();
				vi.setAnimation(aa);
			} else {
				System.exit(1);
			}
			return true;

		}
		return true;
	}

}
