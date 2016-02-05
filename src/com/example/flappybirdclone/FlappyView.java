package com.example.flappybirdclone;

import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;

public class FlappyView extends View {
	private static final Paint bg = new Paint();
	private static final Paint text = new Paint();
	Birdie birdie;
	public float g;
	public State state = State.OUT_TUBE;
	private List<Tube> tubes = new LinkedList<Tube>();
	public float V = 50;
	public int k;
	private int score;
	private int w1;

	public FlappyView(Context context) {
		super(context);
		bg.setColor(0xff75c1ff);
		text.setColor(0xffff0000);
		text.setTextSize(200);
		text.setTextAlign(Align.CENTER);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawRect(0, 0, getWidth(), getHeight(), bg);
		birdie.draw(canvas);

		for (Tube t : tubes)
			t.draw(canvas);
		if (state == State.LOSE)
			canvas.drawText("LOSE", getWidth() / 2, getHeight() / 2, text);

		invalidate();
	}

	private void restart() {
		state = State.OUT_TUBE;
		tubes = new LinkedList<Tube>();
		birdie.y = getHeight();
		birdie.vy = 0;
		score = 0;
		new MyTimer(100000000000000l, 5).start();
	}

	class MyTimer extends CountDownTimer {
		int k = 0;

		public MyTimer(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onTick(long millisUntilFinished) {

			birdie.vy += g;

			birdie.y += birdie.vy;
			if (birdie.y + birdie.bird.getHeight() > FlappyView.this
					.getHeight()) {
				state = State.LOSE;
				cancel();
			}
			if (birdie.y < 0) {
				birdie.vy = 0;
				birdie.vy += 8;

			}
			for (Tube t : tubes) {
				t.left -= V;
				t.right -= V;
				if (state == State.OUT_TUBE) {
					if (birdie.x >= t.left && birdie.x <= t.right) {
						state = State.IN_TUBE;
					}
				}

				if (state == State.IN_TUBE) {
					if (birdie.y >= t.bottom || birdie.y <= t.top
							&& (w1 / 5.0f >= t.left) && (w1 / 5.0f <= t.right)) {
						state = State.LOSE;
					}
				}
			}

			if (k == 300) {
				float r1 = (float) (Math.random() * getWidth() / 2 + getWidth() / 10);
				float r2 = (float) (Math.random() * getHeight() * 3 / 4);

				tubes.add(new Tube(getWidth(), getWidth() + getWidth() / 20,
						r2, r2 + r1));
			}
			k++;
			// for (Tube t : tubes) {
			// if (t.right < 0)
			// tubes.remove(t);
			// }
		}

		// запоминать в какую трубу влетаем и сравнивать с ней?
		@Override
		public void onFinish() {
			start();

		}

	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {

		super.onSizeChanged(w, h, oldw, oldh);
		Bitmap bird = BitmapFactory.decodeResource(getResources(),
				R.drawable.ic_launcher);
		w1 = w;
		birdie = new Birdie(w / 5.0f, h / 2.0f, 0, 0, bird);
		g = h / 1000.0f;
		text.setTextSize(w / 6);
		new MyTimer(1000000000000l, 30).start();

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN)
			if (state == State.LOSE)
				restart();
			else
				birdie.vy -= 50;
		return true;
	}

}
