//
//  VCMainActivity.h
//  Plane
//
//  Created by Gao on 14-8-30.
//  Copyright (c) 2014年 gao. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface VCMainActivity : UIViewController
{
    //游戏背景视图
    UIImageView *_bgView1;
    UIImageView *_bgView2;
    //玩家飞机视图
    UIImageView *_plane;
    //生命视图
    UIImageView *_life;
    //分数显示面板
    UILabel *_scoreLabel;
    //游戏分数
    int score;
    //定时器(控制背景移动)
    NSTimer *_timer;
    //飞机是否被选中
    BOOL isSelect;
    //上次点击的坐标
    CGPoint _mPtLast;

}

@end
