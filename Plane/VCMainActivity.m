//
//  VCMainActivity.m
//  Plane
//
//  Created by Gao on 14-8-30.
//  Copyright (c) 2014年 gao. All rights reserved.
//

#import "VCMainActivity.h"

@interface VCMainActivity ()

@end

@implementation VCMainActivity

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
        
        //初始化飞机是否被选中
        isSelect = NO;
        //初始分数为0
        score = 0;
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    //背景视图
    _bgView1 = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"bg.png"]];
    _bgView1.frame = CGRectMake(0, -2520, 320, 1500);
    [self.view addSubview:_bgView1];
    _bgView2 = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"bg.png"]];
    _bgView2.frame = CGRectMake(0, -1020, 320, 1500);
    [self.view addSubview:_bgView2];
    //飞机视图
    _plane = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"plane.png"]];
    _plane.frame = CGRectMake(137.5, 420, 45, 60);
    _plane.userInteractionEnabled = YES;
    [self.view addSubview:_plane];
    //生命视图
    for (int i = 0; i < 3; i++) {
        _life = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"life"]];
        _life.frame = CGRectMake(30*i, 5, 26, 25);
        [self.view addSubview:_life];
    }
    //显示分数视图
    UILabel *lable = [[UILabel alloc] initWithFrame:CGRectMake(215, 5, 42, 25)];
    lable.text = @"分数:";
    lable.textAlignment = NSTextAlignmentCenter;
    lable.textColor = [UIColor whiteColor];
    lable.font = [UIFont systemFontOfSize:18];
    [self.view addSubview:lable];
    _scoreLabel = [[UILabel alloc] initWithFrame:CGRectMake(257, 5, 63, 25)];
    _scoreLabel.text = @"0";
    _scoreLabel.textAlignment = NSTextAlignmentCenter;
    _scoreLabel.textColor = [UIColor whiteColor];
    _scoreLabel.font = [UIFont systemFontOfSize:18];
    [self.view addSubview:_scoreLabel];
    
    //开启背景下移动画
//    [self moveAnimation];//有加速减速 不符合要求
    
    //使用定时器做背景下移
    _timer = [NSTimer scheduledTimerWithTimeInterval:0.01 target:self selector:@selector(updateTimer) userInfo:nil repeats:YES];
    
}
//动画函数 (这里没有使用到这个函数,因为动画的效果不符合需求)
- (void)moveAnimation
{
    _bgView1.frame = CGRectMake(0, -2520, 320, 1500);
    _bgView2.frame = CGRectMake(0, -1020, 320, 1500);

    //开始一段动画
    [UIView beginAnimations:@"bgMove" context:nil];  //参数1:动画名字 参数2:动画内容
    //设置动画时长
    [UIView setAnimationDuration:10.0];
    //设置动画延时启动时间
    [UIView setAnimationDelay:0.0];
    //设置代理对象
    [UIView setAnimationDelegate:self];
    //当动画结束时,调用此函数
    [UIView setAnimationDidStopSelector:@selector(moveAnimation)];
    //用来改变动画的内容
    _bgView1.frame = CGRectMake(0, -1020, 320, 1500);
    _bgView2.frame = CGRectMake(0, 480, 320, 1500);
    //提交动画
    [UIView commitAnimations];
}
//定时器函数
- (void)updateTimer
{
    //设置背景下移效果
    CGRect rect1 = _bgView1.frame;
    rect1.origin.y += 1.5;
    _bgView1.frame = rect1;
    
    CGRect rect2 = _bgView2.frame;
    rect2.origin.y += 1.5;
    _bgView2.frame = rect2;
    
    if (_bgView1.frame.origin.y == 480) {
        _bgView1.frame = CGRectMake(0, -2520, 320, 1500);
    }
    else if (_bgView2.frame.origin.y == 480) {
        _bgView2.frame = CGRectMake(0, -2520, 320, 1500);
    }
    
    //设置分数
    score++;
    _scoreLabel.text = [NSString stringWithFormat:@"%d",score];
    
    //每隔一段时间间隔发射一组子弹
    static int time = -1;
    time++;
    if (time%15 == 0) {
        for (int i = 0; i < 4; i++) {
            UIImageView *bullet = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"bullet_player.png"]];
            bullet.frame = CGRectMake(_plane.frame.origin.x+8+6*(i+i/2), _plane.frame.origin.y-11, 5, 11);
            [self.view addSubview:bullet];
            [NSTimer scheduledTimerWithTimeInterval:0.01 target:self selector:@selector(bulletFire:) userInfo:bullet repeats:YES];
        }
    }
    
    //产生第1组敌机(普通机enemy3)
    if (time%1200 == 0) {
        for (int i = 0; i < 3; i++) {
            UIImageView *enemy = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"enemy3.png"]];
            enemy.frame = CGRectMake(40+(25+82.5)*i, -33-abs(i-1)*40, 25, 33);
            enemy.tag = 103;
            [self.view addSubview:enemy];
            [NSTimer scheduledTimerWithTimeInterval:0.01 target:self selector:@selector(enemyFire:) userInfo:enemy repeats:YES];
        }
    }
    //产生第2组敌机(三弹机enemy2)
    if (time%1200 == 50 || time%1200 == 250 || time%1200 == 550 || time%1200 == 950) {
        for (int i = 0; i < 2; i++) {
            UIImageView *enemy = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"enemy2.png"]];
            enemy.frame = CGRectMake(70+i*130, -25, 50, 25);
            enemy.tag = 102;
            [self.view addSubview:enemy];
            [NSTimer scheduledTimerWithTimeInterval:0.01 target:self selector:@selector(enemyFire:) userInfo:enemy repeats:YES];
        }
    }
    //产生第3组敌机(冲撞机enemy1 + 普通机enemy3)
    if (time%1200 == 200) {
        for (int i = 0; i < 4; i++) {
            if (i%3==0) {
                UIImageView *enemy = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"enemy1.png"]];
                enemy.frame = CGRectMake(21+84*i, -31, 26, 31);
                enemy.tag = 101;
                [self.view addSubview:enemy];
                [NSTimer scheduledTimerWithTimeInterval:0.01 target:self selector:@selector(enemyFire:) userInfo:enemy repeats:YES];
            }
            else {
                UIImageView *enemy = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"enemy3.png"]];
                enemy.frame = CGRectMake(20+85*i, -80, 25, 33);
                enemy.tag = 103;
                [self.view addSubview:enemy];
                [NSTimer scheduledTimerWithTimeInterval:0.01 target:self selector:@selector(enemyFire:) userInfo:enemy repeats:YES];
            }
        }
    }
    //产生第4组敌机(三弹机enemy2)
        //和第2组相同
    //产生第5组敌机(普通机enemy3)
    if (time%1200 == 400) {
        for (int i = 0; i < 4; i++) {
            int h = 0;
            if (i%3 == 0) {
                h = 40;
            }
            UIImageView *enemy = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"enemy3.png"]];
            enemy.frame = CGRectMake(20+85*i, -33-h, 25, 33);
            enemy.tag = 103;
            [self.view addSubview:enemy];
            [NSTimer scheduledTimerWithTimeInterval:0.01 target:self selector:@selector(enemyFire:) userInfo:enemy repeats:YES];
        }
    }
    //产生第6组敌机(冲撞机enemy1)
    if (time%1200 == 500) {
        for (int i = 0; i < 2; i++) {
            UIImageView *enemy = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"enemy1.png"]];
            enemy.frame = CGRectMake(100+94*i, -31, 26, 31);
            enemy.tag = 101;
            [self.view addSubview:enemy];
            [NSTimer scheduledTimerWithTimeInterval:0.01 target:self selector:@selector(enemyFire:) userInfo:enemy repeats:YES];
        }
    }
    //产生第7组敌机(三弹机enemy2)
        //和第2组相同
    //产生第8组敌机(普通机enemy3)
    if (time%1200 == 700) {
        for (int i = 0; i < 5; i++) {
            int h = 40;
            if (i%4 == 0) {
                h = 80;
            }
            else if (i%4 == 2) {
                h = 0;
            }
            UIImageView *enemy = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"enemy3.png"]];
            enemy.frame = CGRectMake(9.5+69*i, -33-h, 25, 33);
            enemy.tag = 103;
            [self.view addSubview:enemy];
            [NSTimer scheduledTimerWithTimeInterval:0.01 target:self selector:@selector(enemyFire:) userInfo:enemy repeats:YES];
        }
    }
    //产生第9组敌机(冲撞机enemy1)
    if (time%1200 == 800) {
        for (int i = 0; i < 2; i++) {
            UIImageView *enemy = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"enemy1.png"]];
            enemy.frame = CGRectMake(80+134*i, -31, 26, 31);
            enemy.tag = 101;
            [self.view addSubview:enemy];
            [NSTimer scheduledTimerWithTimeInterval:0.01 target:self selector:@selector(enemyFire:) userInfo:enemy repeats:YES];
        }
    }
    //产生第10组敌机(普通机enemy3)
    if (time%1200 == 850) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2; j++) {
                UIImageView *enemy = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"enemy3.png"]];
                enemy.frame = CGRectMake(5+i*57+j*(285-114*i), -193+i*80, 25, 33);
                enemy.tag = 103;
                [self.view addSubview:enemy];
                [NSTimer scheduledTimerWithTimeInterval:0.01 target:self selector:@selector(enemyFire:) userInfo:enemy repeats:YES];
            }
        }
    }
    //产生第11组敌机(三弹机enemy2)
        //和第2组相同
    //产生第12组敌机(冲撞机enemy1)
    if (time%1200 == 1100) {
        for (int i = 0; i < 2; i++) {
            UIImageView *enemy = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"enemy1.png"]];
            enemy.frame = CGRectMake(60+174*i, -31, 26, 31);
            enemy.tag = 101;
            [self.view addSubview:enemy];
            [NSTimer scheduledTimerWithTimeInterval:0.01 target:self selector:@selector(enemyFire:) userInfo:enemy repeats:YES];
        }
    }
}
//子弹发射
- (void)bulletFire:(NSTimer *)timer
{
    UIImageView *bullet = timer.userInfo;
    CGRect rect = bullet.frame;
    rect.origin.y -= 4;
    bullet.frame = rect;
    //如果子弹离开屏幕 去除子弹视图 关闭控制那颗子弹的定时器
    if (bullet.frame.origin.y <= -11) {
        [bullet removeFromSuperview];
        [timer invalidate];
        bullet = nil;//赋为空 ARC下不知道会不会释放内存 子弹产生太多 用完最好释放掉
    }
}

//敌机飞行
- (void)enemyFire:(NSTimer *)timer
{
    UIImageView *enemy = timer.userInfo;
    CGRect rect = enemy.frame;

    //冲撞机不发子弹 速度快 会变向
    if (enemy.tag == 101) {
        //左侧敌机
        if (rect.origin.x<=134) {
            static int dir = 0;
            if (dir == 0 ) {
                rect.origin.x -= 3;
                if (rect.origin.x <= -56) {
                    dir = 1;
                }
            }
            else {
                rect.origin.x += 3;
                if (rect.origin.x >= 124) {
                    dir = 0;
                }
            }
        }
        //右侧敌机
        else {
            static int dir = 0;
            if (dir == 0 ) {
                rect.origin.x += 3;
                if (rect.origin.x >= 350) {
                    dir = 1;
                }
            }
            else {
                rect.origin.x -= 3;
                if (rect.origin.x <= 170) {
                    dir = 0;
                }
            }
        }
        rect.origin.y += 4;
    }
    //三弹机速度较慢
    else if (enemy.tag == 102) {
        rect.origin.y += 2;
    }
    //普通敌机速度中等
    else if (enemy.tag == 103) {
        rect.origin.y += 3;
    }

    enemy.frame = rect;
    //如果敌机离开屏幕 去除敌机视图 关闭控制敌机飞行的定时器
    if (enemy.frame.origin.y >= 480) {
        [enemy removeFromSuperview];
        [timer invalidate];
        enemy = nil;
    }
}

//当开始点击屏幕的瞬间触发此函数
- (void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event
{
    UITouch *touch = [touches anyObject];
    CGPoint pt = [touch locationInView:self.view];
    if (pt.x>=_plane.frame.origin.x&&pt.x<=_plane.frame.origin.x+45 && pt.y>=_plane.frame.origin.y&&pt.y<=_plane.frame.origin.y+60) {
        isSelect = YES;
        //获取开始点位置
        _mPtLast = pt;
    }
}

//当手指在屏幕上移动时(手指没有离开屏幕)
//触摸屏幕的过程中,只有手指的位置(坐标)发送变化,函数被调用
- (void)touchesMoved:(NSSet *)touches withEvent:(UIEvent *)event
{
    if (isSelect) {
        UITouch *touch = [touches anyObject];
        CGPoint pt = [touch locationInView:self.view];
        //计算当前点与上一次点的位置的差值
        //x方向的偏移量
        float xOffset = pt.x - _mPtLast.x ;
        //y方向的偏移量
        float yOffset = pt.y - _mPtLast.y ;
        
        CGPoint newPT = _plane.frame.origin ;
        newPT.x += xOffset ;
        newPT.y += yOffset ;
        
        _plane.frame = CGRectMake(newPT.x, newPT.y, 45, 60) ;
        //将当前的点的位置赋值给上一次的变量
        _mPtLast = pt ;
    }
}

//当手指离开屏幕时触发此函数
- (void)touchesEnded:(NSSet *)touches withEvent:(UIEvent *)event
{
    isSelect = NO;
}

//隐藏顶部时间栏
- (BOOL)prefersStatusBarHidden
{
    return YES;
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender
{
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
