package com.example.plane1;



import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * @author Himi
 *
 */
public class GameBg {
	//��Ϸ������ͼƬ��Դ
	//Ϊ��ѭ�����ţ����ﶨ������λͼ����
	//����Դ���õ���ͬһ��ͼƬ
	private Bitmap bmpBackGround;

	//��Ϸ��������
	private int bgX, bgY;
	//���������ٶ�
	private int speed = 3;

	//��Ϸ�������캯��
	public GameBg(Bitmap bmpBackGround) {
		this.bmpBackGround = bmpBackGround;
	
		//�ȱ����ײ���������������Ļ
		bgY = -Math.abs(bmpBackGround.getHeight() - PlaneSurfaceView.screenH);
		
	}
	//��Ϸ�����Ļ�ͼ����
	public void draw(Canvas canvas, Paint paint) {
		//�������ű���
		canvas.drawBitmap(bmpBackGround, bgX, bgY, paint);
		
	}
	//��Ϸ�������߼�����
	public void logic() {
		bgY += speed;
		if (bgY >0) {
			bgY = -Math.abs(bmpBackGround.getHeight() - PlaneSurfaceView.screenH);
		}
		
	}
}
