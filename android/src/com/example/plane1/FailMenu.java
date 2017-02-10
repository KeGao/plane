package com.example.plane1;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

public class FailMenu {
	
	private Bitmap img_fail_menu;
	private Bitmap img_play_again;
	private boolean isPress;
	
	private int btn_player_againX;
	private int btn_player_againY;
	
	FailMenu(Bitmap fail_menu ,Bitmap play_again){
		img_play_again=play_again;
		img_fail_menu=fail_menu;
		btn_player_againX=180;
		btn_player_againY=382;
		isPress=false;
	}
	
	
	public void draw(Canvas canvas,Paint paint){
		
		canvas.drawBitmap(img_fail_menu, 0,1, paint);
		canvas.drawText("Your Score: "+PlaneSurfaceView.player.getScore(), 100, 350, paint);
		if(isPress){
			canvas.drawBitmap(img_play_again,btn_player_againX,btn_player_againY, paint);
		}else{
			
		}
	}
	public void onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		int pointX = (int) event.getX();
		int pointY = (int) event.getY();
		//���û��ǰ��¶������ƶ�����
		if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
			//�ж��û��Ƿ����˰�ť
			
			
			if (pointX > btn_player_againX && pointX <btn_player_againX + img_play_again.getWidth()) {
				if (pointY > btn_player_againY && pointY < btn_player_againY + img_play_again.getHeight()) {
					isPress= true;
					
				} else {
					isPress = false;
				}
			} else {
				isPress = false;
			}
			//���û���̧����
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			//̧���ж��Ƿ�����ť����ֹ�û��ƶ�����
			
			if (pointX > btn_player_againX && pointX < btn_player_againX + img_play_again.getWidth()) {
				if (pointY > btn_player_againY && pointY < btn_player_againY + img_play_again.getHeight()) {
					//��ԭButton״̬Ϊδ����״̬
					isPress = false;
					//�ı䵱ǰ��Ϸ״̬Ϊ��ʼ��Ϸ
					PlaneSurfaceView.gameState = PlaneSurfaceView.GAMEING;
					PlaneSurfaceView.player.setLife(3);
					PlaneSurfaceView.player.setScore(0);
					PlaneSurfaceView.enemies.removeAllElements();
					PlaneSurfaceView.enemyBullets.removeAllElements();
					PlaneSurfaceView.playerBullets.removeAllElements();
					PlaneSurfaceView.timer=0;
				}
			}else{
				
			}
			
		}
	}
}
