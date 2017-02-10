package com.example.plane1;



import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Enemy {
	private Bitmap img_enemy;
	public Bitmap getImg_enemy() {
		return img_enemy;
	}

	public void setImg_enemy(Bitmap img_enemy) {
		this.img_enemy = img_enemy;
	}

	private int speed;
	private int type;
	private boolean isDead;


    private boolean move_direction;
	private int enemyX;
	
	private int enemyY;
	private int SPEED_ENEMY1=13;
	private int SPEED_ENEMY2=10;
	private int SPEED_ENEMY3=12;
	private int SPEED_ENEMY4=12;

    static int TYPE_ENEMY1=0;
    static int TYPE_ENEMY2=1;
    static int TYPE_ENEMY3=3;
    static int TYPE_ENEMY4=4;
	Enemy(Bitmap enemy,int type,int x,int y){
		img_enemy=enemy;
		if(type==TYPE_ENEMY1){
			speed=SPEED_ENEMY1;
			this.type=TYPE_ENEMY1;
		}else if(type==TYPE_ENEMY2){
			speed=SPEED_ENEMY2;
			this.type=TYPE_ENEMY2;
		}else if(type==TYPE_ENEMY3){
			move_direction=true;
			speed=SPEED_ENEMY3;
			this.type=TYPE_ENEMY3;
		}else if(type==TYPE_ENEMY4){
			move_direction=false;
			speed=SPEED_ENEMY4;
			this.type=TYPE_ENEMY4;
		}
		enemyX=x;
		enemyY=y;
		isDead=false;
		
	}
	
	public void logic(){
		if(type==TYPE_ENEMY1){
			enemyY+=speed;
		}else if(type==TYPE_ENEMY2){
			enemyY+=speed;
		}
		else if(type==TYPE_ENEMY3){
			if(enemyY>30){
				speed=15;
				if(enemyX<-80+img_enemy.getWidth()){
					
					move_direction=false;
				}else if(enemyX>PlaneSurfaceView.screenW/2-50){
					
					move_direction=true;
				}
				
				if(move_direction){
					enemyX+=-speed;
					
				}else{
					enemyX+=speed;
				}
			    enemyY+=speed;
			
			}else{
				enemyY+=speed;
				
			}
		}else if(type==TYPE_ENEMY4){
			if(enemyY>30){
				speed=15;
				if(enemyX>PlaneSurfaceView.screenW+20){
					
					move_direction=true;
				}else if(enemyX<PlaneSurfaceView.screenW-150){
					
					move_direction=false;
				}
				
				if(move_direction){
					enemyX+=-speed;
					
				}else{
					enemyX+=speed;
				}
			    enemyY+=speed;
			
			}else{
				enemyY+=speed;
				
			}
		}
		
	}
	
	public void draw(Canvas canvas,Paint paint){
	     canvas.drawBitmap(img_enemy, enemyX,enemyY ,paint);	
	     
	  
	}
	
	
	public boolean isCollsionWith(Bullet bullet) {
		int bulletX = bullet.getBulletX();
		int bulletY = bullet.getBulletY();
		int w2 = bullet.getImg_bullet().getWidth();
		int h2 = bullet.getImg_bullet().getHeight();
		if (enemyX >= bulletX && enemyX>= bulletX + w2) {
			return false;
		} else if (enemyX<= bulletX && enemyX + img_enemy.getWidth() <= bulletX) {
			return false;
		} else if (enemyY >= bulletY && enemyY >= bulletY + h2) {
			return false;
		} else if (enemyY<= bulletY && enemyY + img_enemy.getHeight()<= bulletY) {
			return false;
		}
		//·¢ÉúÅö×²£¬ÈÃÆäËÀÍö
		isDead = true;
		bullet.setDead(true);
		return true;
	}
	

	public boolean isDead() {
		return isDead;
	}

	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}
	public int getEnemyX() {
		return enemyX;
	}

	public void setEnemyX(int enemyX) {
		this.enemyX = enemyX;
	}

	public int getEnemyY() {
		return enemyY;
	}

	public void setEnemyY(int enemyY) {
		this.enemyY = enemyY;
	}

	public int getType() {
		return type;
	}

}
