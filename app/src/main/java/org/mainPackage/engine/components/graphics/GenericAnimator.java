package org.mainPackage.engine.components.graphics;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Optional;

import org.mainPackage.engine.components.Component;
import org.mainPackage.enums.action;

/**
 * Reusable animator for any animation state type T.
 */
public abstract class GenericAnimator<T> implements Component {
    protected HashMap<T, BufferedImage[]> animations = new HashMap<>();
    protected T currentState;
    protected int frameIndex = 0;
    protected int frameDelay = 10;
    protected int tick = 0;

    @Override
public void update(float deltaTime) {
    BufferedImage[] frames = animations.get(currentState);
    if (frames == null || frames.length == 0) return;

    // Cast currentState to your enum type 'action'
    action IDLE_STATE = action.idle;

    if (currentState.equals(IDLE_STATE)) {
        // Loop continuously for idle animation
        tick++;
        if (tick >= frameDelay) {
            tick = 0;
            frameIndex = (frameIndex + 1) % frames.length;
        }
    } else {
        // For other animations, stop on the last frame
        if (frameIndex < frames.length - 1) {
            tick++;
            if (tick >= frameDelay) {
                tick = 0;
                frameIndex++;
            }
        }
    }
}

    public Optional<BufferedImage> getCurrentFrame() {
        return Optional.ofNullable(animations.get(currentState))
            .filter(frames -> frames.length > 0)
            .map(frames -> frames[frameIndex]);
    }

    public void addAnimation(T state, BufferedImage[] frames, int delay) {
        animations.put(state, frames);
        if (currentState == null) currentState = state;
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
