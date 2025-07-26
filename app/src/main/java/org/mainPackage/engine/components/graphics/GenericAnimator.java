package org.mainPackage.engine.components.graphics;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Optional;
import org.mainPackage.engine.components.Component;

/**
 * GenericAnimator is a reusable animation component that maps a state of type T
 * to an array of animation frames. It updates frame indices over time
 * and returns the correct frame for the current state.
 *
 * @param <T> the type representing animation states 
 */
public abstract class GenericAnimator<T> implements Component {
    /**
     * A map of animation states to their corresponding frames.
     */
    protected HashMap<T, BufferedImage[]> animations = new HashMap<>();

    /**
     * The current animation state.
     */
    protected T currentState;

    /**
     * The current index of the frame being displayed.
     */
    protected int frameIndex = 0;

    /**
     * The number of ticks to wait before changing to the next frame.
     */
    protected int frameDelay = 10;

    /**
     * Internal counter used to track frame update timing.
     */
    protected int tick = 0;

    /**
     * Updates the animation frame based on the delay. Should be called once per game tick.
     *
     * @param deltaTime The time elapsed since the last frame (not used here but required by interface).
     */
    @Override
    public void update(float deltaTime) {
        Optional<BufferedImage[]> framesOpt = Optional.ofNullable(animations.get(currentState));
        if (framesOpt.isEmpty() || framesOpt.get().length == 0) return;

        tick++;
        if (tick >= frameDelay) {
            tick = 0;
            frameIndex = (frameIndex + 1) % framesOpt.get().length;
        }
    }

    /**
     * Returns the current frame of the animation for the current state.
     *
     * @return an Optional containing the BufferedImage of the current frame, or Optional.empty() if no frame exists.
     */
    public Optional<BufferedImage> getCurrentFrame() {
        return Optional.ofNullable(animations.get(currentState))
            .filter(frames -> frames.length > 0)
            .map(frames -> frames[frameIndex]);
    }

    /**
     * Adds an animation sequence for a given state with associated frames and frame delay.
     *
     * @param state the animation state
     * @param frames the array of frames for that state
     * @param delay the number of ticks to wait before switching frames
     */
    public void addAnimation(T state, BufferedImage[] frames, int delay) {
        animations.put(state, frames);
        if (currentState == null) {
            currentState = state;
        }
        this.frameDelay = delay;
    }

    /**
     * Sets the current animation state to a new state.
     * Resets the frame index and tick counter.
     * 
     * @param newState the new animation state to set
     */

      public void setState(T newState) {
        if (newState != currentState) {
            currentState = newState;
            frameIndex = 0;
            tick = 0;
        }
    }
}
