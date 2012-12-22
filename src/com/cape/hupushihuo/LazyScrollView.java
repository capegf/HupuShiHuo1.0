package com.cape.hupushihuo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class LazyScrollView extends ScrollView {

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		super.onTouchEvent(ev);
		return gestureDetector.onTouchEvent(ev);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		gestureDetector.onTouchEvent(ev);
		super.dispatchTouchEvent(ev);
		return true;
	}

	public LazyScrollView(Context context) {
		super(context);
	}

	public LazyScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public LazyScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	GestureDetector gestureDetector;

	public void setGestureDetector(GestureDetector gestureDetector) {
		this.gestureDetector = gestureDetector;
	}

	// @Override
	// public void fling(int velocityY) {
	// super.fling(velocityY / 4);
	// }
}