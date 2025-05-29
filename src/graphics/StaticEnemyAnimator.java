package entities;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import util.SpriteLoader;

public class StaticEnemyAnimator {
    private HashMap<StaticEnemyState, BufferedImage[]> animations = new HashMap<>();
    private int frameIndex = 0;
    private int frameDelay = 10;
    private int tick = 0;
    private SpriteLoader spriteLoader;

    public StaticEnemyAnimator() {
        try {
            spriteLoader = new SpriteLoader("Custom Edited - Sonic the Hedgehog Customs - Objects.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
        loadAnimations();
    }

    private void loadAnimations() {
         
        animations.put(StaticEnemyState.IDLE, spriteLoader.getFramesByPixels(0, 0, 6, 32, 32));
    }

    public BufferedImage getFrame(StaticEnemyState state) {
        BufferedImage[] frames = animations.get(state);
        tick++;
        if (tick >= frameDelay) {
            tick = 0;
            frameIndex = (frameIndex + 1) % frames.length;
        }
        return frames[frameIndex];
    }
}
