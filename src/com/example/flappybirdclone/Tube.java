package com.example.flappybirdclone;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Tube {
	float left, right, top, bottom;
	private static final Paint paint = new Paint();

	public Tube(float left, float right, float top, float bottom) {
		super();
		paint.setColor(0xffff00ff);
		this.left = left;
		this.right = right;
		this.top = top;
		this.bottom = bottom;
	}
	public void draw(Canvas canvas) {
		canvas.drawRect(left, 0, right, top, paint );
		canvas.drawRect(left, bottom, right, canvas.getHeight(), paint);
	}
}
