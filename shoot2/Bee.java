package shoot2;

public class Bee extends FiyingObject implements Award{

    private int speed_x;
    private int speed_y;

    public int awardType = (int)(Math.random() * 2);

    Bee(){
        super((int)(Math.random() * (Main.WIDTH - Main.bee.getWidth())),
                Main.bee.getHeight(),
                Main.bee,1);
        speed_x = 2;
        speed_y = 2;
    }

    @Override
    public void move() {
        this.setX(this.getX() + speed_x);
        this.setY(this.getY() + speed_y);
        if(this.getX() == (Main.WIDTH - Main.bee.getWidth())){
            speed_x = -2;
        }
        if(this.getX() == 0){
            speed_x = 2;
        }
    }

    @Override
    public int awardtype() {
        return awardType;
    }
}
