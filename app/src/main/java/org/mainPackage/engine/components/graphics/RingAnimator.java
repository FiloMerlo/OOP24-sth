package org.mainPackage.engine.components.graphics;

import org.mainPackage.util.SpriteLoader;

/**
 * Handles the animation of collectible rings in the game.
 * 
 * Rings have a single looping animation state: SPINNING.
 * The GenericAnimator internally applies a looping strategy.
 */
public class RingAnimator extends GenericAnimator<RingAnimator.State> {

    /** Enum representing the only state of the ring: spinning */
    public enum State { SPINNING }

    /** Sprite sheet path for the ring animation */
    private static final String SPRITE_PATH = "/rings2.png";

    /** Number of frames in the ring spinning animation */
    private static final int FRAME_COUNT = 8;

    /** Size in pixels of each sprite frame */
    private static final int SPRITE_SIZE = 16;

    /** Frame delay between animation frames */
    private static final int DELAY = 15;

    /**
     * Constructs a RingAnimator and loads the SPINNING animation
     * from the sprite sheet.
     */
    public RingAnimator() {
        try {
            SpriteLoader loader = new SpriteLoader(SPRITE_PATH);
            addAnimation(
                State.SPINNING,
                loader.getFramesByPixels(0, 0, FRAME_COUNT, SPRITE_SIZE, SPRITE_SIZE),
                DELAY
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
