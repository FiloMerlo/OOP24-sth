package entities;

import java.awt.Graphics;

public abstract class Entity {
    protected int xPos, yPos;
    protected int xSpeed, ySpeed;
    protected int groundSpeed, groundAngle;
    protected int widthR;
    protected int heightR;

    public Entity(int xPos, int yPos, int xSpeed, int ySpeed, int groundSpeed, int groundAngle, int widthR, int heightR) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.groundSpeed = groundSpeed;
        this.groundAngle = groundAngle;
        this.widthR = widthR;
        this.heightR = heightR;
    }

    public int getGroundSpeed() { return groundSpeed; }
    public int getGroundAngle() { return groundAngle; }
    public int getXSpeed() { return xSpeed; }
    public int getYSpeed() { return ySpeed; }
    public int getXpos() { return xPos; }
    public int getYpos() { return yPos; }
    public int getWidthR() { return widthR; }
    public int getHeightR() { return heightR; }

    public abstract void update();
    public abstract void draw(Graphics g, int offsetX, int offsetY);
}
