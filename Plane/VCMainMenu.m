//
//  VCMainMenu.m
//  Plane
//
//  Created by Gao on 14-8-30.
//  Copyright (c) 2014年 gao. All rights reserved.
//

#import "VCMainMenu.h"
#import "VCMainActivity.h"

@interface VCMainMenu ()

@end

@implementation VCMainMenu

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    //隐藏顶部时间栏
//    [[UIApplication sharedApplication] setStatusBarHidden:YES];
    
    //游戏开始界面
    _mainMenu = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"main_menu.png"]];
    _mainMenu.frame = CGRectMake(0, 0, 320, 480);
    [self.view addSubview:_mainMenu];
    
    //自定义开始游戏按钮
    _btnStart = [UIButton buttonWithType:UIButtonTypeCustom];
    _btnStart.frame = CGRectMake(131, 270, 184, 40);
    [_btnStart setImage:[UIImage imageNamed:@"btn_start1.png"] forState:UIControlStateHighlighted];
    [_btnStart addTarget:self action:@selector(startGame) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:_btnStart];
    
    //自定义退出游戏按钮
    _btnExit = [UIButton buttonWithType:UIButtonTypeCustom];
    _btnExit.frame = CGRectMake(177, 364, 139, 41);
    [_btnExit setImage:[UIImage imageNamed:@"btn_exi1t.png"] forState:UIControlStateHighlighted];
    [_btnExit addTarget:self action:@selector(exitGame) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:_btnExit];
    
}
//开始游戏
- (void)startGame
{
    NSLog(@"GameStart!");
    VCMainActivity *vcMainAct = [[VCMainActivity alloc] init];
    [self presentViewController:vcMainAct animated:NO completion:nil];
    
}
//退出游戏
- (void)exitGame
{
    NSLog(@"ExitGame!");
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
