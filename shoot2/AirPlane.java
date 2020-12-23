package shoot2;

import java.lang.Math;

public class AirPlane extends FiyingObject implements Enemy{
    private int speed;
    private int score;

    AirPlane(){
        super((int)(Math.random() * (Main.WIDTH - Main.airplane.getWidth())),
                -Main.airplane.getHeight(),
                Main.airplane,1);
        speed = 4;
        score = 10;
    }

    @Override
    public void move() {
        this.setY(this.getY() + speed);
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public int getscore() {
        return score;
    }
}
