package org.mainPackage.engine.components.graphics;

import org.mainPackage.enums.ChasingEnemyState;
import org.mainPackage.util.SpriteLoader;

/**
 * Animator dedicated to the chasing enemy character.
 * 
 * Loads and manages animations for different enemy states
 * such as idle and walking, using frames extracted from a sprite sheet.
 */
public class ChasingEnemyAnimator extends GenericAnimator<ChasingEnemyState> {

    /** Path to the sprite sheet for the chasing enemy */
    private static final String SPRITE_SHEET_PATH = "/goblin.png";

    /** Delay between frames for the idle animation */
    private static final int IDLE_DELAY = 10;

    /** Delay between frames for the walking animation */
    private static final int WALK_DELAY = 8;

    /** Size of each sprite frame in pixels */
    private static final int SPRITE_SIZE = 64;

    /**
     * Constructs a ChasingEnemyAnimator by loading the idle and walk animations
     * from the sprite sheet specified by {@link #SPRITE_SHEET_PATH}.
     */
    public ChasingEnemyAnimator() {
        try {
            SpriteLoader loader = new SpriteLoader(SPRITE_SHEET_PATH);

            addAnimation(
                ChasingEnemyState.IDLE,
                loader.getFramesByPixels(0, 0, 4, SPRITE_SIZE, SPRITE_SIZE),
                IDLE_DELAY
            );

            addAnimation(
                ChasingEnemyState.WALK,
                loader.getFramesByPixels(0, SPRITE_SIZE, 6, SPRITE_SIZE, SPRITE_SIZE),
                WALK_DELAY
            );

        } catch (Exception e) {
            e.printStackTrace();
           
        }
    }
}
