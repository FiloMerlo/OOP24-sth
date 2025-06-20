package org.mainPackage.engine.components.graphics;

import org.mainPackage.enums.action;
import org.mainPackage.util.SpriteLoader;

public class SonicAnimator extends GenericAnimator<action> {

    public SonicAnimator() {
        try {
            SpriteLoader loader = new SpriteLoader("/1sonic.png");

            addAnimation(action.idle, loader.getFramesByPixels(0, 0, 9, 54, 54), 2);
            addAnimation(action.walking, loader.getFramesByPixels(0, 54, 9, 54, 54), 2);
            addAnimation(action.running, loader.getFramesByPixels(0, 54*2, 8, 54, 54), 6);
            addAnimation(action.dashing, loader.getFramesByPixels(0, 54*3, 9, 54, 54), 4);
            addAnimation(action.jumping, loader.getFramesByPixels(7*54, 54*4, 2, 54, 54), 3);
            addAnimation(action.hurt, loader.getFramesByPixels(0, 54*5, 5, 54, 54), 2);
            addAnimation(action.skidding, loader.getFramesByPixels(54*8, 54*2, 1, 54, 54), 12);
            addAnimation(action.falling, loader.getFramesByPixels(0, 54*6, 4, 54, 54), 20);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
