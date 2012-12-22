package com.cape.hupushihuo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cape.hupushihuo.ImageManager.ImageCallback;

public class MyActivity extends Activity {
	ImageButton ib = null;
	TextView shoestitle = null;
	ImageView freight_payer = null;
	TextView like = null;
	TextView sold = null;
	Button goShop = null;
	String url;
	ImageView shoesImage = null;
	ProgressBar pb = null;
	ImageView imgChecked = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_float);
		Intent intent = getIntent();
		final Good g = (Good) intent.getSerializableExtra("info");
		String title = g.getTitle();
		Log.e("title", title);

		pb = (ProgressBar) findViewById(R.id.pb);

		url = g.getUrl();
		String likeCount = g.getLikeCount();
		String saleCount = g.getSoldCount();
		String isFreight_payer = g.getFreightPayer();
		final String getTaobaoUrl = g.getUrl();
		final boolean isChecked = g.getVerified();

		shoestitle = (TextView) findViewById(R.id.title);
		shoestitle.setText(title);

		freight_payer = (ImageView) findViewById(R.id.freight_payer);
		if (isFreight_payer.equals("1")) {
			freight_payer.setVisibility(View.VISIBLE);
		}

		like = (TextView) findViewById(R.id.likecount);
		like.setText("喜欢:" + likeCount);

		sold = (TextView) findViewById(R.id.soldcount);
		sold.setText("已售出:" + saleCount + "件");

		imgChecked = (ImageView) findViewById(R.id.isChecked);

		ib = (ImageButton) findViewById(R.id.goshop);
		ib.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				ib.setBackgroundResource(R.drawable.bnt2);

				final Handler handler = new Handler() {
					@Override
					public void handleMessage(Message msg) {
						// TODO Auto-generated method stub
						switch (msg.what) {
						case 0:
							ib.setBackgroundResource(R.drawable.bnt1);
							Uri uri = Uri.parse(getTaobaoUrl);
							Intent intent = new Intent(Intent.ACTION_VIEW, uri);
							startActivity(intent);
							break;

						default:
							break;
						}
					}
				};

				new Thread() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							sleep(300);
							handler.sendEmptyMessage(0);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}.start();

			}
		});
		shoesImage = (ImageView) findViewById(R.id.shoes);
		ImageManager im = ImageManager.getInstance();
		im.downloadImages(new ImageCallback() {

			public void imageLoaded(Bitmap bitmap) {
				// TODO Auto-generated method stub
				shoesImage.setImageBitmap(bitmap);
				shoesImage.setVisibility(View.VISIBLE);
				if (isChecked) {
					imgChecked.setVisibility(View.VISIBLE);
				}
				pb.setVisibility(View.GONE);
			}
		}, g, 1);
	}
}
