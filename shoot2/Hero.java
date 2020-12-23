package shoot2;

import java.lang.management.BufferPoolMXBean;
import java.util.ArrayList;

public class Hero extends FiyingObject{

    private int life;
    private int score;
    private ArrayList<Bullet> bullets;
    private int award;


    Hero(){
        super(100,100,Main.hero0,1);
        life = 3;
        award = 0;
    }

    private int count = 0;
    public void move() {
        count++;
        if(count % 2 == 0){
            this.setImg(Main.hero0);
        }else{
            this.setImg(Main.hero1);
        }
    }
    Bullet fly;
    public ArrayList<Bullet> shoot(){
        int y_step = 10;
        if (award == 0) {
            fly = new Bullet(this.getX() + this.getWidth() / 2,this.getY() - y_step);
            bullets.add(fly);
            return bullets;
        } else {
            bullets.remove(0);
            fly = new Bullet(this.getX() + this.getWidth() / 4,this.getY() - y_step);
            bullets.add(fly);
            fly = new Bullet(this.getX() + this.getWidth() * 3 / 4,this.getY() - y_step);
            bullets.add(fly);
            return bullets;
        }
    }
    public int getLife() {
        return life;
    }

    public void addLife() {
        this.setLife(this.getLife() + 1);
    }

    public int getScore() {
        return score;
    }

    @Override
    public void setLife(int life) {
        this.life = life;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addScore(int score) {
        this.setScore(this.getScore() + score);
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public void setBullets(ArrayList<Bullet> bullets) {
        this.bullets = bullets;
    }

    public int getAward() {
        return award;
    }

    public void setAward(int award) {
        this.award = award;
    }
}
