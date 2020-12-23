package shoot2;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends JPanel {
    public static BufferedImage bg = null;
    public static BufferedImage hero0 = null;
    public static BufferedImage hero1;
    public static BufferedImage bee = null;
    public static BufferedImage bullet = null;
    public static BufferedImage airplane;
    public static BufferedImage bigplane;
    public static BufferedImage start;
    public static BufferedImage pause;
    public static BufferedImage gameover;
    ArrayList<FiyingObject> flyings;
    ArrayList<Bullet> bullets;

    Main(){
        flyings = new ArrayList<>();
    }

    static{
        try {
            bg = ImageIO.read(Main.class.getResourceAsStream("img/background.png"));
            hero0 = ImageIO.read((Main.class.getResourceAsStream("img/hero0.png")));
            hero1 = ImageIO.read(Main.class.getResourceAsStream("img/hero1.png"));
            bee = ImageIO.read(Main.class.getResourceAsStream("img/bee.png"));
            bullet = ImageIO.read(Main.class.getResourceAsStream("img/bullet.png"));
            airplane = ImageIO.read(Main.class.getResourceAsStream("img/airplane.png"));
            bigplane = ImageIO.read(Main.class.getResourceAsStream("img/bigplane.png"));
            start = ImageIO.read(Main.class.getResourceAsStream("img/start.png"));
            pause = ImageIO.read(Main.class.getResourceAsStream("img/pause.png"));
            gameover = ImageIO.read(Main.class.getResourceAsStream("img/gameover.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //定义四个固定状态
    private static final int START = 0;
    private static final int RUNNING = 1;
    private static final int PAUSE = 2;
    private static final int GAME_OVER = 3;
    //定义一个初始状态
    private int state = START;


    Hero hero = new Hero();
    public void paint(Graphics g){
        super.paint(g);
        //绘制背景
        g.drawImage(bg,0,0,this);
        g.drawImage(hero.getImg(),hero.getX(),hero.getY(),this);
        paintFlyingObject(g);

    }

    //定时器timer
    //把定时器变成成员变量 -> 为了方便别的方法也能使用
    //import javax.swing.*;->不是这个包下的
    //java.until.Timer timer = new java.until.Timer();
    private Timer timer = new Timer();
    //定义一个成员方法专门做定时且重复的事 - 定时器
    public void action() {
        /*schedule - 行程表
         * 参数一：要做的重复的事 - 执行重复的代码
         * 参数二：定时器开始的时间（毫秒ms）1秒 = 1000毫秒
         * 参数三：时间间隔周期（隔多少秒再重复一次 ms）*/
        //匿名内部类
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                arrayFlyingObject();
                FlyingObjectStep();
                outOfBoundsFlyingObject();
                shootAction();
                bulletStep();
                flyingRush();
                hero.move();

                repaint();
            }
        }, 1000, 20);
        //添加监听器 - 局部内部类 -> 匿名内部类
        MouseAdapter adapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(state == START) {
                    state = RUNNING;
                }
                repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if(state == PAUSE){
                    state = RUNNING;
                }
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if(state == RUNNING){
                    state = PAUSE;
                }
                repaint();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                if(state == RUNNING){
                    int mouse_x = e.getX();
                    int mouse_y = e.getY();
                    hero.setX(mouse_x);
                    hero.setY(mouse_y);
                    repaint();
                }
            }
        };
        this.addMouseMotionListener(adapter);
        this.addMouseListener(adapter);
    }

    //判断子弹越界


    //判断飞行物的越界
    private void outOfBoundsFlyingObject(){
        //所有飞行物存在flying中
        for (int i = 0; i < flyings.size(); i++) {
            //获得集合中每一个飞行物对象
            FiyingObject fly = flyings.get(i);
            if(fly.getY() >= HEIGHT){
                flyings.remove(i);
                i--;
            }
        }
        for (int i = 0; i < bullets.size(); i++) {
            Bullet blt = bullets.get(i);
            if(blt.getY() <= 0){
                bullets.remove(i);
                i--;
            }
        }
    }

    //飞行物的移动
    private void FlyingObjectStep(){
        //所有飞行物对象都存在flying中
        for (int i = 0; i < flyings.size(); i++) {
            FiyingObject fly = flyings.get(i);
            fly.move();
        }
    }

    int flyingIndex = 0;
    private void arrayFlyingObject(){
        //开始计时
        flyingIndex++;
        //定义统一变量 -> 向上造型
        FiyingObject fly;
        //对二十取余，等于0时生成飞行物
        if(flyingIndex % 40 == 0){
            //随机生成概率 -> 在20中进行随机
            int ran = (int)(Math.random()*15);
            if(ran == 0){
                fly = new Bee();
            }else if(ran == 1 || ran == 2 || ran == 3){
                fly = new BigPlane();
            }else{
                fly = new AirPlane();
            }
            flyings.add(fly);
        }
    }

    //发射的子弹集合
    int shootIndex = 0;
    private void shootAction(){
        shootIndex++;
        if(shootIndex == 20){
            ArrayList<Bullet> bs = hero.shoot();
            for (int i = 0; i < bs.size(); i++) {
                bullets.add(bs.get(i));
            }
        }
    }

    //子弹的移动方法
    private void bulletStep(){
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).move();
        }
    }

    //飞行物和子弹的碰撞
    private void flyingRush(){
        for (int i = 0; i < bullets.size(); i++) {
            for (int j = 0; j < flyings.size(); j++) {
                Bullet b = bullets.get(i);
                FiyingObject f = flyings.get(j);
                if((b.getX() >= f.getX()) &&
                        (b.getX() <= f.getX() + f.getWidth()) &&
                        (b.getY() >= f.getY()) &&
                        (b.getY() <= f.getY() + f.getHeight())
                                ){
                    f.reducelife();
                    if(f.life == 0){
                        if(f instanceof Enemy){
                            Enemy enemy = (Enemy)f;
                            hero.addScore(enemy.getscore());
                        }
                        if(f instanceof Award){
                            Award award = (Award)f;
                            hero.setAward(award.awardtype());
                            if(hero.getAward() == 0){
                                hero.addLife();
                            }
                        }
                        flyings.remove(j);
                        j--;
                    }
                    bullets.remove(i);
                    i--;
                }
            }
        }
    }

    //画状态
    private void printState(Graphics g){
        switch (state){
            case START:
                g.drawImage(start,0,0,this);
                break;
            case PAUSE:
                g.drawImage(pause,0,0,this);
                break;
            case GAME_OVER:
                g.drawImage(gameover,0,0,this);
                break;
        }
    }

    //画出全部飞行物
    private void paintFlyingObject(Graphics g){
        for (int i = 0; i < flyings.size(); i++) {
            FiyingObject fly = flyings.get(i);
            g.drawImage(fly.getImg(),fly.getX(),fly.getY(),this);
        }
    }

    public static final int WIDTH = 400;
    public static final int HEIGHT = 600;
    public static void main(String[] args) {
        JFrame window  = new JFrame();
        window.setSize(WIDTH,HEIGHT);
        window.setDefaultCloseOperation(3);
        JPanel main = new Main();
        window.add(main);
        window.setVisible(true);
        window.setLocationRelativeTo(null);
        if(main instanceof Main){
            ((Main)main).action();
        }
    }
}
