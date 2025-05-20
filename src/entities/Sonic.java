package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import util.SpriteLoader;

/**
 * rappresenta il personaggio principale controllato dal giocatore.
 */
public class Sonic extends Entity {
    private action playerAction = action.idle;
    private SonicAnimator animator;
    private boolean left, right, up, down;
    private boolean isJumping, isRunning, isInAir = false;
    private boolean isHurt = false;
    private boolean wasMovingLeft = false;
    private boolean wasMovingRight = false;

    public Sonic(int xPos, int yPos, int xSpeed, int ySpeed, int groundSpeed, int groundAngle, int widthR, int heightR) {
        super(xPos, yPos, xSpeed, ySpeed, groundSpeed, groundAngle, widthR, heightR);
        animator = new SonicAnimator();
    }

    @Override
    public void update() {
        updatePos();
        updateAction();
    }

    public void updatePos() {
        boolean changedDirection = false;

        if (left && !right) {
            changedDirection = wasMovingRight && isRunning;
            xPos -= xSpeed;
        } else if (!left && right) {
            changedDirection = wasMovingLeft && isRunning;
            xPos += xSpeed;
        }

        wasMovingLeft = left;
        wasMovingRight = right;

        if (changedDirection) {
            playerAction = action.skidding;
        }

        if (up && !isJumping) {
            yPos -= 10;
            isJumping = true;
            isInAir = true;
        }

        if (isJumping) {
            yPos += 5;
            if (yPos >= groundY()) {
                yPos = groundY();
                isJumping = false;
                isInAir = false;
            }
        }
    }

    private void updateAction() {
        if (isHurt) {
            playerAction = action.hurt;
        } else if (playerAction == action.skidding) {
            // resta skidding
        } else if (isJumping) {
            playerAction = action.jumping;
        } else if (isInAir) {
            playerAction = action.falling;
        } else if (left || right) {
            playerAction = isRunning ? action.running : action.walking;
        } else {
            playerAction = action.idle;
        }
    }

    @Override
    public void draw(Graphics g, int offsetX, int offsetY) {
        BufferedImage currentFrame = animator.getFrame(playerAction);
        g.drawImage(currentFrame, xPos + offsetX, yPos + offsetY, null);
    }

    public void setLeft(boolean left) { this.left = left; }
    public void setRight(boolean right) { this.right = right; }
    public void setUp(boolean up) { this.up = up; }
    public void setDown(boolean down) { this.down = down; }
    public void startRunning() { isRunning = true; }
    public void stopRunning() { isRunning = false; }
    public void stopJumping() { isJumping = false; }
    public void setHurt(boolean hurt) { this.isHurt = hurt; }
    public boolean isHurt() { return isHurt; }

    public void takeDamage(RingManager ringManager) {
        if (isHurt) {
            ringManager.scatterRings(xPos, yPos, 10);
            isHurt = false;
        }
    }

    public action getPlayerAction() { return playerAction; }

    private int groundY() {
        return 300;
    }
}
