import java.awt.image.BufferedImage;
import java.util.HashMap;
import util.SpriteLoader;

/**
 * gestisce l'animazione di sonic.
 */
public class SonicAnimator {
    private HashMap<action, BufferedImage[]> animations = new HashMap<>();
    private int frameIndex = 0;
    private int frameDelay = 10;
    private int tick = 0;
    private SpriteLoader spriteLoader;

    public SonicAnimator() {
        try {
            spriteLoader = new SpriteLoader("/PC Computer - Sonic Mania - Sonic the Hedgehog.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
        loadAnimations();
    }

    private void loadAnimations() {
        animations.put(action.idle, spriteLoader.getFramesByPixels(0, 0, 4, 48, 48));
        animations.put(action.walking, spriteLoader.getFramesByPixels(0, 96, 6, 48, 48));
        animations.put(action.running, spriteLoader.getFramesByPixels(0, 144, 8, 48, 48));
        animations.put(action.jumping, spriteLoader.getFramesByPixels(0, 240, 4, 48, 48));
        animations.put(action.skidding, spriteLoader.getFramesByPixels(0, 288, 2, 48, 48));
        animations.put(action.dashing, spriteLoader.getFramesByPixels(96, 192, 2, 48, 48));
        animations.put(action.falling, spriteLoader.getFramesByPixels(96, 240, 2, 48, 48));
        animations.put(action.hurt, spriteLoader.getFramesByPixels(0, 384, 4, 48, 48));
    }

    public BufferedImage getFrame(action currentAction) {
        BufferedImage[] frames = animations.get(currentAction);
        tick++;
        if (tick >= frameDelay) {
            tick = 0;
            frameIndex = (frameIndex + 1) % frames.length;
        }
        return frames[frameIndex];
    }
}
