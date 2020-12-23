package shoot2;

public class BigPlane extends FiyingObject implements Enemy,Award{
    private int speed;
    private int score;
    public int awardType = (int)(Math.random()*2);

    BigPlane(){
        super((int)(Math.random()*(Main.WIDTH - Main.bigplane.getWidth())),
                -Main.bigplane.getHeight(),Main.bigplane,5);
        speed = 2;
        score = 20;
    }

    @Override
    public void move() {
        this.setY(this.getY() + speed);
    }


    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }

    @Override
    public int getscore() {
        return score;
    }

    @Override
    public int awardtype() {
        return awardType;
    }
}
