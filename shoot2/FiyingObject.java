package shoot2;

import java.awt.image.BufferedImage;

public abstract class FiyingObject {
    //成员变量
    protected int x;
    protected int y;
    protected BufferedImage img;
    protected int width;
    protected int height;
    protected int life;

    FiyingObject(){}

    FiyingObject(int x,int y,BufferedImage img,int life){
        this.x = x;
        this.y = y;
        this.img = img;
        this.height = img.getHeight();
        this.width = img.getWidth();
        this.life = life;
    }

    public abstract void move();
    public int getX(){return x;}
    public void setX(int x){this.x = x;}
    public int getY(){return y;}
    public void setY(int y){this.y = y;};
    public BufferedImage getImg(){return img;}
    public void setImg(BufferedImage img){this.img = img;}
    public int getWidth(){return width;}
    public void setWidth(int width){this.width = width;};
    public int getHeight(){return height;}
    public void setHeight(int height){this.height = height;}

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public void reducelife(){
        this.setLife(this.getLife() - 1);
    }
}
