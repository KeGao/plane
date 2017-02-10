package com.example.plane1;




import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

public class Player {

	private Bitmap img_plane;
	private Bitmap img_life;
	private Bitmap img_hide_plane;
	private boolean isWuDi;
	 //飞机无敌时间
	public static long wudiTimer;
	private int playerX;
	private long score;
	
	private int life;
	
	public int getLife() {
		return life;
	}


	public void setLife(int life) {
		this.life = life;
	}


	public long getScore() {
		return score;
	}


	public void setScore(long score) {
		this.score = score;
	}


	public int getPlayerX() {
		return playerX;
	}


	public void setPlayerX(int playerX) {
		this.playerX = playerX;
	}


	public int getPlayerY() {
		return playerY;
	}


	public void setPlayerY(int playerY) {
		this.playerY = playerY;
	}


	private int playerY;
	private int speed;
	
	Player(Bitmap plane,Bitmap hide_plane,Bitmap img_life){
		this.img_life=img_life;
		img_hide_plane=hide_plane;
		img_plane=plane;
		isWuDi=true;
		life=3;
		playerX=150;
		playerY=220;
		speed=5;
		wudiTimer=0;
	}
	
	
	public void logic(){
		
	}
	
	public void draw(Canvas canvas,Paint paint){
		
		
		if(isWuDi){
			
			canvas.drawBitmap(img_hide_plane, playerX,playerY ,paint);
		}else{
			canvas.drawBitmap(img_plane, playerX,playerY ,paint);
		}
		
		for(int i=0;i<life;i++){
			canvas.drawBitmap(img_life, i*img_life.getWidth(),0 ,paint);
		}
		
		paint.setColor(Color.WHITE);
		 paint.setTextSize(18);
		 canvas.drawText("score: "+score,180,20 , paint);
		
	}
	
	
	public void onTouchEvent(MotionEvent event) {
		int eventAction=event.getAction();
		int x=(int) event.getX();
		int y=(int) event.getY();
		if(eventAction==MotionEvent.ACTION_MOVE){
			if(playerX>x-img_plane.getWidth())
			{
				playerX-=speed;
			}else if(playerX<x-img_plane.getWidth()){
				playerX+=speed;
			}else{
				
			}
			
			if(playerY>y)
			{
				playerY-=speed;
			}else if(playerY<y){
				playerY+=speed;
			}else{
				
			}
		}
	}
	
	
	

	//判断碰撞(主角与敌机)
	public boolean isCollsionWith(Enemy en) {
		//是否处于无敌时间
		if(!isWuDi){
			int x2 = en.getEnemyX();
			int y2 = en.getEnemyY();
			int w2 = en.getImg_enemy().getWidth();
			int h2 = en.getImg_enemy().getHeight();
			if (playerX>= x2 && playerX >= x2 + w2) {
				return false;
			} else if (playerX <= x2 && playerX+ img_plane.getWidth() <= x2) {
				return false;
			} else if (playerY >= y2 && playerY >= y2 + h2) {
				return false;
			} else if (playerY <= y2 && playerY + img_plane.getHeight() <= y2) {
				return false;
			}
			en.setDead(true);
			isWuDi=true;
			life--;
			return true;
			//处于无敌状态，无视碰撞
		}else{
			if(wudiTimer>100){
				isWuDi=false;
				wudiTimer=0;
			}
			wudiTimer++;
			return false;
		}
	}
	
	
	//判断碰撞(主角与敌机子弹)
	public boolean isCollsionWith(Bullet bullet) {
		//是否处于无敌时间
		if(!isWuDi){
			int bulletX = bullet.getBulletX();
			int bulletY = bullet.getBulletY();
			int w2 = bullet.getImg_bullet().getWidth();
			int h2 = bullet.getImg_bullet().getHeight();
			if (playerX >= bulletX && playerX >= bulletX + w2) {
				return false;
			} else if (playerX <= bulletX && playerX + img_plane.getWidth() <= bulletX) {
				return false;
			} else if (playerY >= bulletY && playerY >= bulletY + h2) {
				return false;
			} else if (playerY <= bulletY && playerY + img_plane.getHeight() <= bulletY) {
				return false;
			}
			//碰撞即进入无敌状态
			
			bullet.setDead(true);
			isWuDi=true;
			life--;
			
			return true;
			//处于无敌状态，无视碰撞
		}else{
			if(wudiTimer>100){
				isWuDi=false;
				wudiTimer=0;
			}
			wudiTimer++;
			return false;
		}
		
	}
}
