package org.mainPackage.engine.components.graphics;

import org.mainPackage.enums.StaticEnemyState;
import org.mainPackage.util.SpriteLoader;

/**
 * Handles animation for static enemies
 * Currently only supports a single idle frame, but uses
 * the animation system for future flexibility.
 */
public class StaticEnemyAnimator extends GenericAnimator<StaticEnemyState> {

    /** Path to the sprite sheet */
    private static final String SPRITE_PATH = "/obstacle.png";

    /** Width and height of each sprite frame */
    private static final int SPRITE_SIZE = 32;

    /** Delay between animation frames */
    private static final int DELAY = 12;

    /**
     * Constructs a StaticEnemyAnimator and loads the idle animation frame.
     */
    public StaticEnemyAnimator() {
        try {
            SpriteLoader loader = new SpriteLoader(SPRITE_PATH);

            addAnimation(
                StaticEnemyState.IDLE,
                loader.getFramesByPixels(0, 0, 1, SPRITE_SIZE, SPRITE_SIZE),
                DELAY
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
