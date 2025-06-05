package graphics;

import java.awt.image.BufferedImage;
import java.util.HashMap;

import entities.ChasingEnemyState;
import util.SpriteLoader;

public class ChasingEnemyAnimator {
    private HashMap<ChasingEnemyState, BufferedImage[]> animations = new HashMap<>();
    private int frameIndex = 0;
    private int frameDelay = 10;
    private int tick = 0;
    private ChasingEnemyState currentState = ChasingEnemyState.IDLE;

    public ChasingEnemyAnimator() throws Exception {
        SpriteLoader loader = new SpriteLoader("Custom Edited - Sonic the Hedgehog Customs - Black Arms.png");

        animations.put(ChasingEnemyState.IDLE, loader.getFramesByPixels(0, 0, 4, 32, 32));  
        animations.put(ChasingEnemyState.WALK, loader.getFramesByPixels(0, 32, 6, 32, 32)); 
    }

    public void setState(ChasingEnemyState newState) {
        if (currentState != newState) {
            currentState = newState;
            frameIndex = 0;
            tick = 0;
        }
    }

    public BufferedImage getCurrentFrame() {
        BufferedImage[] frames = animations.get(currentState);
        tick++;
        if (tick >= frameDelay) {
            tick = 0;
            frameIndex = (frameIndex + 1) % frames.length;
        }
        return frames[frameIndex];
    }
}
