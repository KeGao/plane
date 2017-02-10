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
		//���û��ǰ��¶������ƶ�����
		if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
			//�ж��û��Ƿ����˰�ť
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
			//���û���̧����
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			//̧���ж��Ƿ�����ť����ֹ�û��ƶ�����
			if (pointX > btn_startX && pointX < btn_startX + img_btn_start.getWidth()) {
				if (pointY > btn_startY && pointY < btn_startY + img_btn_start.getHeight()) {
					//��ԭButton״̬Ϊδ����״̬
					isPress1 = false;
					//�ı䵱ǰ��Ϸ״̬Ϊ��ʼ��Ϸ
					PlaneSurfaceView.gameState = PlaneSurfaceView.GAMEING;
				}
			}
			
			if (pointX > btn_exitX && pointX < btn_exitX + img_btn_exit.getWidth()) {
				if (pointY > btn_exitY && pointY < btn_exitY + img_btn_exit.getHeight()) {
					//��ԭButton״̬Ϊδ����״̬
					isPress2 = false;
					isExit=true;
					//�ı䵱ǰ��Ϸ״̬Ϊ��ʼ��Ϸ
				
				}
			}
		}
	}

}
