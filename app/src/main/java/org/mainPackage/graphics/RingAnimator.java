package org.mainPackage.graphics;

import org.mainPackage.util.SpriteLoader;

public class RingAnimator extends GenericAnimator<RingAnimator.State> {

    public enum State { SPINNING }

    public RingAnimator() {
        try {
            SpriteLoader loader = new SpriteLoader("PC Computer - Sonic Mania - General Objects.png");
            addAnimation(State.SPINNING, loader.getFramesByPixels(0, 0, 16, 16, 16), 5);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
