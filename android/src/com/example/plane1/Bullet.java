package com.example.plane1;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Bullet {
	
	private Bitmap img_bullet;
	
	public Bitmap getImg_bullet() {
		return img_bullet;
	}


	public void setImg_bullet(Bitmap img_bullet) {
		this.img_bullet = img_bullet;
	}


	private int speed;
	private int type;
	
	private int bulletX;

	private int bulletY;
	
	private boolean isDead;
	
	public boolean isDead() {
		return isDead;
	}


	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}


	public static int TYPE_PLAYER=0;
	public static int TYPE_BULLET_1=1;
	public static int TYPE_BULLET_2=2;
	public static int TYPE_BULLET_3=3;
	public static int TYPE_BULLET_4=4;
	private static int SPEED_PLAYER=20;
	private static int SPEED_BULLET_1=20;
	private static int SPEED_BULLET_2=20;
	private static int SPEED_BULLET_3=14;
	private static int SPEED_BULLET_4=14;

	Bullet(Bitmap bullet,int type,int x,int y){
		this.type=type;
		img_bullet=bullet;
		bulletX=x;
		bulletY=y;
		isDead=false;
		if(type==TYPE_PLAYER){
			speed=SPEED_PLAYER;
			this.type=TYPE_PLAYER;
		}else if(type==TYPE_BULLET_1){
			speed=SPEED_BULLET_1;
			this.type=TYPE_BULLET_1;
		}
		else if(type==TYPE_BULLET_2){
			speed=SPEED_BULLET_2;
			this.type=TYPE_BULLET_2;
		}
		else if(type==TYPE_BULLET_3){
			speed=SPEED_BULLET_3;
			this.type=TYPE_BULLET_3;
		}
		else if(type==TYPE_BULLET_4){
			speed=SPEED_BULLET_4;
			this.type=TYPE_BULLET_4;
		}
	
	}
	
	
	public void draw (Canvas canvas,Paint paint){
		canvas.drawBitmap(img_bullet, bulletX,bulletY, paint);
	}
	
	public void logic(){
		if(type==TYPE_PLAYER){
			bulletY-=speed;
		}else if(type==TYPE_BULLET_1){
			bulletY+=speed;
		}else if(type==TYPE_BULLET_2){
			bulletY+=speed;
		}else if(type==TYPE_BULLET_3){
			bulletX-=speed;
			bulletY+=speed*1;
		}else if(type==TYPE_BULLET_4){
			bulletX+=speed;
			bulletY+=speed*1;
		}
		
	}
	public int getBulletX() {
		return bulletX;
	}


	public void setBulletX(int bulletX) {
		this.bulletX = bulletX;
	}


	public int getBulletY() {
		return bulletY;
	}


	public void setBulletY(int bulletY) {
		this.bulletY = bulletY;
	}

}
