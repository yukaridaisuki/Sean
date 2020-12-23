package shoot2;

public class Bullet extends FiyingObject{

    private int speed;

    Bullet(){}

    Bullet(int x,int y){
        super(x,y,Main.bee,1);
        speed = 3;
    }

    @Override
    public void move() {
        this.setY(this.getY() - speed);
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
