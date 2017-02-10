package com.example.plane1;



import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * @author Himi
 *
 */
public class GameBg {
	//游戏背景的图片资源
	//为了循环播放，这里定义两个位图对象，
	//其资源引用的是同一张图片
	private Bitmap bmpBackGround;

	//游戏背景坐标
	private int bgX, bgY;
	//背景滚动速度
	private int speed = 3;

	//游戏背景构造函数
	public GameBg(Bitmap bmpBackGround) {
		this.bmpBackGround = bmpBackGround;
	
		//先背景底部正好填满整个屏幕
		bgY = -Math.abs(bmpBackGround.getHeight() - PlaneSurfaceView.screenH);
		
	}
	//游戏背景的绘图函数
	public void draw(Canvas canvas, Paint paint) {
		//绘制两张背景
		canvas.drawBitmap(bmpBackGround, bgX, bgY, paint);
		
	}
	//游戏背景的逻辑函数
	public void logic() {
		bgY += speed;
		if (bgY >0) {
			bgY = -Math.abs(bmpBackGround.getHeight() - PlaneSurfaceView.screenH);
		}
		
	}
}
