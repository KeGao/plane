package com.example.plane1;

import java.util.Map;
import java.util.Vector;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Looper;

import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.widget.EditText;

public class PlaneSurfaceView extends SurfaceView implements Runnable, Callback {

	private SharedPreferences spf;
	private Editor editor;
	private SurfaceHolder sfh;
	private Paint paint;
	private Thread th;
	private boolean flag;
	private Canvas canvas;
	private Resources res = this.getResources();
	static int screenH;
	static int screenW;

	// 计时器
	public static long timer;

	private int position;

	public static Vector<Bullet> playerBullets;
	public static Vector<Bullet> enemyBullets;
	public static Vector<Enemy> enemies;

	private GameBg gameBg;
	private MainMenu mainMenu;
	public static Player player;
	private FailMenu failMenu;

	// 游戏状态类型
	final static int GAME_MENU = 0;
	final static int GAMEING = 1;
	private final static int GAME_PAUSE = 2;
	private final static int GAME_WIN = 3;
	private final static int GAME_FAIL = 4;

	// 游戏当前状态
	static int gameState = GAME_MENU;

	private EditText name;
	// 图片资源
	private Bitmap img_menu;
	private Bitmap img_btn_start;
	private Bitmap img_btn_exit;
	private Bitmap img_bg;
	private Bitmap img_plane;
	private Bitmap img_bullet_player;
	private Bitmap img_bullet_enemy_1;
	private Bitmap img_enemy_1;
	private Bitmap img_life;
	private Bitmap img_btn_player_again;
	private Bitmap img_fail_menu;
	private Bitmap img_enemy_2;
	private Bitmap img_enemy_3;
	private Bitmap img_hide_plane;
	private Context context;

	public PlaneSurfaceView(Context context) {
		super(context);
		this.context = context;
		sfh = this.getHolder();
		sfh.addCallback(this);
		paint = new Paint();
		paint.setColor(Color.WHITE);
		/*
		 * paint.setAntiAlias(true); setFocusable(true);
		 * setFocusableInTouchMode(true);
		 */
		// 设置背景常亮
		this.setKeepScreenOn(true);
	}

	// 初始化资源
	private void initGame() {

		spf = context.getSharedPreferences("score", Context.MODE_PRIVATE);
		editor = spf.edit();
		img_menu = BitmapFactory.decodeResource(res, R.drawable.main_menu);
		img_btn_start = BitmapFactory
				.decodeResource(res, R.drawable.btn_start1);
		img_btn_exit = BitmapFactory.decodeResource(res, R.drawable.btn_exi1t);
		img_bg = BitmapFactory.decodeResource(res, R.drawable.bg);
		img_plane = BitmapFactory.decodeResource(res, R.drawable.plane);
		img_bullet_player = BitmapFactory.decodeResource(res,
				R.drawable.bullet_player);
		img_bullet_enemy_1 = BitmapFactory.decodeResource(res,
				R.drawable.bullet1);
		img_enemy_1 = BitmapFactory.decodeResource(res, R.drawable.enemy3);
		img_enemy_2 = BitmapFactory.decodeResource(res, R.drawable.enemy2);
		img_enemy_3 = BitmapFactory.decodeResource(res, R.drawable.enemy1);
		;
		img_life = BitmapFactory.decodeResource(res, R.drawable.life);
		img_btn_player_again = BitmapFactory.decodeResource(res,
				R.drawable.btn_fail);
		img_fail_menu = BitmapFactory.decodeResource(res, R.drawable.fail_menu);
		img_hide_plane = BitmapFactory.decodeResource(res,
				R.drawable.hide_plane);
		mainMenu = new MainMenu(img_menu, img_btn_start, img_btn_exit);
		gameBg = new GameBg(img_bg);
		player = new Player(img_plane, img_hide_plane, img_life);
		failMenu = new FailMenu(img_fail_menu, img_btn_player_again);
		timer = 0;
		playerBullets = new Vector<Bullet>();
		enemyBullets = new Vector<Bullet>();
		enemies = new Vector<Enemy>();
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		screenW = this.getWidth();
		screenH = this.getHeight();

		initGame();
		flag = true;
		th = new Thread(this);
		th.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		flag = false;
		Log.i("MAIN", "dffffffffff");
	}

	@Override
	public void run() {
		while (flag) {

			long start = System.currentTimeMillis();
			logic();
			draw();
			long end = System.currentTimeMillis();

			if (start - end < 50) {
				try {
					Thread.sleep(50 - (start - end));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	private void logic() {

		switch (gameState) {
		case GAME_MENU:
			mainMenu.logic();
			break;
		case GAMEING:
			gameBg.logic();
			player.logic();

			// 生命值用完
			if (player.getLife() <= 0) {

				Map map = spf.getAll();
				int i = 0;
				for (i = 1; i < map.size() / 2 + 1; i++) {

					Long score = spf.getLong("" + i, 0);
					if (score < player.getScore()) {
						for (int j = map.size() / 2; j >= i; j--) {
							Long k1 = spf.getLong("" + j, 0);
							editor.putLong("" + (j + 1), k1);

							String k2 = spf.getString("" + (j + 7), null);
							editor.putString("" + (j + 8), k2);

						}

						editor.putLong("" + i, player.getScore());
						editor.putString("" + (i + 7), null);
						position = i;
						editor.commit();
						break;

					} else {

					}

				}
				if (i >= (map.size() / 2 + 1)) {

					if (map.size() / 2 >= 5) {
						position = 0;
					} else {
						editor.putLong("" + i, player.getScore());
						editor.putString("" + (i + 7), null);
						editor.commit();
						position = i;
					}
				}

				if (position > 0) {
					Looper.prepare();
					name = new EditText(getContext());
					AlertDialog.Builder dlg = new AlertDialog.Builder(
							getContext())
							.setTitle("please input your name")
							.setIcon(android.R.drawable.ic_dialog_info)
							.setView(name)
							.setPositiveButton("OK",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int whichButton) {
											String str_name = name.getText().toString();
													if(str_name.equals("")){
														str_name="unknown";
													}
													
											editor.putString(""
													+ (position + 7), str_name);
											editor.remove("6");
											editor.remove("13");

											editor.commit();

											String message = "";
											for (int j = 1; j <= spf.getAll()
													.size() / 2; j++) {
												String name = spf.getString(""
														+ (j + 7), "?");
												long score = spf.getLong(
														"" + j, 0);
												message += "" + j + " " + name
														+ ":" + " " + score
														+ "\n";
											}
											new AlertDialog.Builder(
													getContext())
													.setTitle("TOP5  High Scores")
													.setMessage(message)
													.setPositiveButton(
															"OK",
															new DialogInterface.OnClickListener() {

																@Override
																public void onClick(
																		DialogInterface dialog,
																		int which) {
																	// TODO
																	// Auto-generated
																	// method
																	// stub

																	gameState = GAME_FAIL;

																	th = new Thread(
																			PlaneSurfaceView.this);
																	player.setLife(3);
																	th.start();
																}

															}).show();
										}
									})
							.setNegativeButton("Cancel",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int whichButton) {
											editor.putString(""
													+ (position + 7), "unkown");
											editor.remove("6");
											editor.remove("13");

											editor.commit();

											String message = "";
											for (int j = 1; j <= spf.getAll()
													.size() / 2; j++) {
												String name = spf.getString(""
														+ (j + 7), "?");
												long score = spf.getLong(
														"" + j, 0);
												message += "" + j + " " + name
														+ ":" + " " + score
														+ "\n";
											}
											new AlertDialog.Builder(
													getContext())
													.setTitle("TOP5  High Scores")
													.setMessage(message)
													.setPositiveButton(
															"OK",
															new DialogInterface.OnClickListener() {

																@Override
																public void onClick(
																		DialogInterface dialog,
																		int which) {
																	
																	gameState = GAME_FAIL;

																	th = new Thread(
																			PlaneSurfaceView.this);
																	player.setLife(3);
																	th.start();
																}

															}).show();
										}
									});
					dlg.show();

					Looper.loop();
				} else {
					gameState = GAME_FAIL;
				}

			}

			// 判断碰撞(主角与敌机)
			for (int i = 0; i < enemies.size(); i++) {
				if (player.isCollsionWith(enemies.get(i))) {

				}
			}

			// 敌机与子弹的碰撞检测
			for (int i = 0; i < enemies.size(); i++) {
				Enemy enemy = enemies.get(i);
				for (int j = 0; j < playerBullets.size(); j++) {
					if (enemy.isCollsionWith(playerBullets.get(j))) {

						player.setScore(player.getScore() + 50);
						break;

					}
				}

			}

			// 飞机与敌机子弹的碰撞检测

			for (int j = 0; j < enemyBullets.size(); j++) {
				if (player.isCollsionWith(enemyBullets.get(j))) {
					break;
				}
			}

			// 判断飞机zhad是否超越边界
			for (int i = 0; i < playerBullets.size(); i++) {
				int y = playerBullets.get(i).getBulletY();

				if (y < 0 || playerBullets.get(i).isDead()) {
					playerBullets.remove(i);
				}
			}

			// 判断敌机炸弹是否超越边界
			for (int i = 0; i < enemyBullets.size(); i++) {
				int y = enemyBullets.get(i).getBulletY();

				if (y > screenH || enemyBullets.get(i).isDead()) {

					enemyBullets.remove(i);

				}
			}

			for (int i = 0; i < enemies.size(); i++) {
				int y = enemies.get(i).getEnemyY();
				if (y > screenH || enemies.get(i).isDead()) {
					enemies.remove(i);
				}
			}

			// 执行飞机炸弹逻辑
			for (int i = 0; i < playerBullets.size(); i++) {
				playerBullets.get(i).logic();
			}

			// 执行敌机炸弹逻辑
			for (int i = 0; i < enemyBullets.size(); i++) {
				enemyBullets.get(i).logic();
			}

			// 执行敌机逻辑
			for (int i = 0; i < enemies.size(); i++) {
				enemies.get(i).logic();

			}

			// 生成飞机炸弹
			timer++;
			if (timer % 3 == 0) {
				int playerX = player.getPlayerX();
				int playerY = player.getPlayerY();

				Bullet bullet1 = new Bullet(img_bullet_player,
						Bullet.TYPE_PLAYER, playerX + img_plane.getWidth() / 2
								- 5, playerY - 10);
				Bullet bullet2 = new Bullet(img_bullet_player,
						Bullet.TYPE_PLAYER, playerX + img_plane.getWidth() / 2
								- 10, playerY - 10);
				Bullet bullet3 = new Bullet(img_bullet_player,
						Bullet.TYPE_PLAYER, playerX + img_plane.getWidth() / 2
								+ 5, playerY - 10);
				Bullet bullet4 = new Bullet(img_bullet_player,
						Bullet.TYPE_PLAYER, playerX + img_plane.getWidth() / 2
								+ 10, playerY - 10);
				playerBullets.add(bullet1);
				playerBullets.add(bullet2);
				playerBullets.add(bullet3);
				playerBullets.add(bullet4);

			}

			// 生成敌机炸弹
			for (int i = 0; i < enemies.size(); i++) {
				Enemy enemy = enemies.get(i);
				int type = enemy.getType();

				if (type == Enemy.TYPE_ENEMY1) {
					if (enemy.getEnemyY() > 2) {
						if (timer % 10 == 0) {
							Bullet bullet = new Bullet(img_bullet_enemy_1,
									Bullet.TYPE_BULLET_1, enemy.getEnemyX()
											+ img_enemy_1.getWidth() / 2 - 4,
									enemy.getEnemyY() + 35);
							enemyBullets.add(bullet);
						}
					}
				} else if (type == Enemy.TYPE_ENEMY2) {
					if (timer % 15 == 0) {
						Bullet bullet1 = new Bullet(img_bullet_enemy_1,
								Bullet.TYPE_BULLET_2, enemy.getEnemyX()
										+ img_enemy_1.getWidth() / 2 - 4,
								enemy.getEnemyY() + 35);
						enemyBullets.add(bullet1);
						Bullet bullet2 = new Bullet(img_bullet_enemy_1,
								Bullet.TYPE_BULLET_3, enemy.getEnemyX()
										+ img_enemy_1.getWidth() / 2 - 4,
								enemy.getEnemyY() + 35);
						enemyBullets.add(bullet2);
						Bullet bullet3 = new Bullet(img_bullet_enemy_1,
								Bullet.TYPE_BULLET_4, enemy.getEnemyX()
										+ img_enemy_1.getWidth() / 2 - 4,
								enemy.getEnemyY() + 35);
						enemyBullets.add(bullet3);
					}

				}
			}

			// 生成敌机1
			if ((timer - 20) % 100 == 0 || timer == 20) {
				for (int i = 0; i < 3; i++) {
					Enemy enemy1 = new Enemy(img_enemy_1, Enemy.TYPE_ENEMY1,
							60 * i, -i * 100);
					Enemy enemy2 = new Enemy(img_enemy_1, Enemy.TYPE_ENEMY1,
							screenW - 60 * i - 35, -i * 100);
					enemies.add(enemy1);
					enemies.add(enemy2);
				}

			}
			// 生成敌机2
			if ((timer - 60) % 100 == 0 || timer == 60) {

				Enemy enemy1 = new Enemy(img_enemy_2, Enemy.TYPE_ENEMY2, 80, 0);
				Enemy enemy2 = new Enemy(img_enemy_2, Enemy.TYPE_ENEMY2,
						screenW - 120, 0);
				enemies.add(enemy1);
				enemies.add(enemy2);

			}

			// 生成敌机3
			if ((timer - 90) % 100 == 0 || timer == 90) {

				Enemy enemy1 = new Enemy(img_enemy_3, Enemy.TYPE_ENEMY3, 60, 0);
				Enemy enemy2 = new Enemy(img_enemy_3, Enemy.TYPE_ENEMY4,
						screenW - 60, 0);
				enemies.add(enemy1);
				enemies.add(enemy2);

			}
			break;
		case GAME_PAUSE:

			break;
		case GAME_WIN:

			break;
		case GAME_FAIL:

			break;

		default:
			break;
		}

	}

	private void draw() {
		try {
			canvas = sfh.lockCanvas();
			if (canvas != null) {
				canvas.drawColor(Color.WHITE);
				switch (gameState) {
				case GAME_MENU:
					mainMenu.draw(canvas, paint);
					break;
				case GAMEING:
					gameBg.draw(canvas, paint);
					player.draw(canvas, paint);

					for (int i = 0; i < playerBullets.size(); i++) {
						playerBullets.get(i).draw(canvas, paint);
					}

					for (int i = 0; i < enemyBullets.size(); i++) {
						enemyBullets.get(i).draw(canvas, paint);
					}

					for (int i = 0; i < enemies.size(); i++) {
						enemies.get(i).draw(canvas, paint);
					}
					break;
				case GAME_PAUSE:

					break;
				case GAME_WIN:

					break;
				case GAME_FAIL:
					failMenu.draw(canvas, paint);
					break;

				default:
					break;
				}
			}
		} catch (Exception e) {

		} finally {
			if (canvas != null)
				sfh.unlockCanvasAndPost(canvas);
		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		switch (gameState) {
		case GAME_MENU:
			// 菜单的触屏事件处理
			mainMenu.onTouchEvent(event);
			break;
		case GAMEING:
			player.onTouchEvent(event);
			break;
		case GAME_PAUSE:
			break;
		case GAME_WIN:
			break;
		case GAME_FAIL:
			failMenu.onTouchEvent(event);
			break;
		}

		return true;
	}
}
