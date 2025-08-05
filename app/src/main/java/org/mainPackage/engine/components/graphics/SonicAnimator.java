package org.mainPackage.engine.components.graphics;

import org.mainPackage.enums.action;
import org.mainPackage.util.SpriteLoader;

import java.util.List;

/**
 * Handles Sonic's animations by loading sprite frames and delays
 * from a sprite sheet using pre-defined animation configurations.
 * 
 * Each animation state corresponds to a set of frames with a delay
 * between frames
 */
public class SonicAnimator extends GenericAnimator<action> {

    /** Path to the sprite sheet for Sonic's animations */
    private static final String SPRITE_SHEET_PATH = "/1sonic.png";

    /** Standard delay between animation frames */
    private static final int DELAY = 15;

    /** Size in pixels of each sprite frame */
    private static final int SPRITE_SIZE = 54;

    /**
     * Constructs a SonicAnimator and loads all animations
     * from the sprite sheet specified by {@link #SPRITE_SHEET_PATH}.
     * 
     * The animations and their frame counts and positions
     * are defined by the internal AnimConfig list.
     */
    public SonicAnimator() {
        try {
            SpriteLoader loader = new SpriteLoader(SPRITE_SHEET_PATH);

            List<AnimConfig> configs = List.of(
                new AnimConfig(action.idle,     0, 0, 9, DELAY),
                new AnimConfig(action.walking,  0, 1, 9, DELAY),
                new AnimConfig(action.running,  0, 2, 8, DELAY),
                new AnimConfig(action.dashing,  0, 3, 9, DELAY),
                new AnimConfig(action.jumping,  7, 4, 1, DELAY),
                new AnimConfig(action.hurt,     0, 5, 5, DELAY),
                new AnimConfig(action.skidding, 8, 2, 1, DELAY),
                new AnimConfig(action.falling,  0, 6, 4, DELAY)
            );

            configs.forEach(cfg ->
                addAnimation(
                    cfg.state(),
                    loader.getFramesByPixels(cfg.x() * SPRITE_SIZE, cfg.y() * SPRITE_SIZE, cfg.count(), SPRITE_SIZE, SPRITE_SIZE),
                    cfg.delay()
                )
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Container for animation metadata.
     *
     * @param state the animation state
     * @param x horizontal frame index in the sprite sheet
     * @param y vertical frame index in the sprite sheet
     * @param count number of frames in the animation
     * @param delay frame delay for animation speed control
     */
    private record AnimConfig(action state, int x, int y, int count, int delay) {}
}
