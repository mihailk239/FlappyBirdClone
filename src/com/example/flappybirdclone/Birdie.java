package com.example.flappybirdclone;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Birdie {
	private static final Paint paint = new Paint();
	float x,y;
	float vx,vy;
	Bitmap bird;
	public Birdie(float x, float y, float vx, float vy, Bitmap bird) {
		super();
		this.x = x;
		this.y = y;
		this.vx = vx;
		this.vy = vy;
		this.bird = bird;
	}
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		canvas.drawBitmap(bird, x, y, paint);
	}
	
}
