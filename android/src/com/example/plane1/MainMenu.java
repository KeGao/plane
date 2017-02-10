package com.example.plane1;



import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

public class MainMenu {
	private Bitmap img_menu;
	private Bitmap img_btn_start;
	private Bitmap img_btn_exit;
	
	
	private int btn_startX;
	private int btn_startY;
	
	private int btn_exitX;
	private int btn_exitY;
	
	private boolean isPress1;
	private boolean isPress2;
	
	private boolean isExit;
	
	MainMenu(Bitmap menu,Bitmap btn_start,Bitmap btn_exit)
	{
		img_menu=menu;
		img_btn_start=btn_start;
		img_btn_exit=btn_exit;
		
		btn_startX=130;
		btn_startY=270;
		btn_exitX=176;
		btn_exitY=364;
		
		isPress1=false;
		isPress2=false;
		isExit=false;
	}
	
	public void logic(){
		if(isExit){
		System.exit(0);
		}
	}
	
	public void draw(Canvas canvas,Paint paint)
	{
		canvas.drawBitmap(img_menu, 0, 0, paint);
		
		if(isPress1){
		    canvas.drawBitmap(img_btn_start, btn_startX,btn_startY, paint);
		}
		if(isPress2){
			  canvas.drawBitmap(img_btn_exit, btn_exitX,btn_exitY, paint);
		}
		
	}
	
	public void onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		int pointX = (int) event.getX();
		int pointY = (int) event.getY();
		//当用户是按下动作或移动动作
		if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
			//判定用户是否点击了按钮
			if (pointX > btn_startX && pointX < btn_startX + img_btn_start.getWidth()) {
				if (pointY > btn_startY && pointY < btn_startY + img_btn_start.getHeight()) {
					isPress1= true;
				} else {
					isPress1 = false;
				}
			} else {
				isPress1 = false;
			}
			
			
			if (pointX > btn_exitX && pointX <btn_exitX + img_btn_exit.getWidth()) {
				if (pointY > btn_exitY && pointY < btn_exitY + img_btn_exit.getHeight()) {
					isPress2= true;
				} else {
					isPress2 = false;
				}
			} else {
				isPress2 = false;
			}
			//当用户是抬起动作
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			//抬起判断是否点击按钮，防止用户移动到别处
			if (pointX > btn_startX && pointX < btn_startX + img_btn_start.getWidth()) {
				if (pointY > btn_startY && pointY < btn_startY + img_btn_start.getHeight()) {
					//还原Button状态为未按下状态
					isPress1 = false;
					//改变当前游戏状态为开始游戏
					PlaneSurfaceView.gameState = PlaneSurfaceView.GAMEING;
				}
			}
			
			if (pointX > btn_exitX && pointX < btn_exitX + img_btn_exit.getWidth()) {
				if (pointY > btn_exitY && pointY < btn_exitY + img_btn_exit.getHeight()) {
					//还原Button状态为未按下状态
					isPress2 = false;
					isExit=true;
					//改变当前游戏状态为开始游戏
				
				}
			}
		}
	}

}
