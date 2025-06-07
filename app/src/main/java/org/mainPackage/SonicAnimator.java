package graphics;

import entities.action;
import util.SpriteLoader;

public class SonicAnimator extends GenericAnimator<action> {

    public SonicAnimator() {
        try {
            SpriteLoader loader = new SpriteLoader("/PC Computer - Sonic Mania - Sonic the Hedgehog copy.png");

            addAnimation(action.idle, loader.getFramesByPixels(0, 0, 4, 48, 48), 15);
            addAnimation(action.walking, loader.getFramesByPixels(0, 48, 6, 48, 48), 8);
            addAnimation(action.running, loader.getFramesByPixels(0, 96, 8, 48, 48), 6);
            addAnimation(action.jumping, loader.getFramesByPixels(0, 144, 4, 48, 48), 4);
            addAnimation(action.falling, loader.getFramesByPixels(192, 144, 2, 48, 48), 10);
            addAnimation(action.skidding, loader.getFramesByPixels(0, 192, 2, 48, 48), 12);
            addAnimation(action.dashing, loader.getFramesByPixels(288, 96, 3, 48, 48), 3);
            addAnimation(action.hurt, loader.getFramesByPixels(0, 336, 4, 48, 48), 20);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
