package entities;

import java.awt.Graphics;

public enum Action { IDLE, WALKING, RUNNING, FALLING, DAMAGE, CROUCH }
public enum Direction { LEFT, RIGHT }

public class Sonic extends Entity {
    private Action playerAction = Action.IDLE;
    private Direction playerDirection = Direction.RIGHT;

    private boolean left;
    private boolean right;
    private boolean down;

    public Sonic(int x, int y, int xSpeed, int ySpeed) {
        super(x, y, xSpeed, ySpeed, 0, 0, 16, 16);
    }

    public boolean getLeft() { return left; }
    public boolean getRight() { return right; }
    public boolean getDown() { return down; }

    public void setLeft(boolean bool) { left = bool; }
    public void setRight(boolean bool) { right = bool; }
    public void setDown(boolean bool) { down = bool; }

    public void setAction(Action a) { this.playerAction = a; }
    public void setDirection(Direction d) { this.playerDirection = d; }

    public Action getAction() { return playerAction; }
    public Direction getDirection() { return playerDirection; }

    public void update() {
        updatePos();
        updateAction();
    }

    public void draw(Graphics g, int offsetX, int offsetY) {
    }
     
    private void updatePos() {
        if (left && !right) {
            xPos -= xSpeed;
            playerDirection = Direction.LEFT;
        } else if (right && !left) {
            xPos += xSpeed;
            playerDirection = Direction.RIGHT;
        }
    }

    private void updateAction() {
        if (!left && !right) {
            playerAction = Action.IDLE;
        } else if (xSpeed <= 2) {
            playerAction = Action.WALKING;
        } else {
            playerAction = Action.RUNNING;
        }
    }
}
