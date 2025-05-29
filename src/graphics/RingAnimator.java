package entities;

import util.SpriteLoader;
import java.awt.image.BufferedImage;

public class RingAnimator {
    private BufferedImage[] ringFrames;

    public RingAnimator() {
        try {
            SpriteLoader loader = new SpriteLoader("PC Computer - Sonic Mania - General Objects.png");
            ringFrames = loader.getFramesByPixels(0, 0, 6, 16, 16);  // Adjust if your ring frames differ
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BufferedImage[] getFrames() {
        return ringFrames;
    }
}
