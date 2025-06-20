package org.mainPackage.engine.components.graphics;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Optional;

import org.mainPackage.engine.components.Component;

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
        Optional<BufferedImage[]> framesOpt = Optional.ofNullable(animations.get(currentState));
        if (framesOpt.isEmpty() || framesOpt.get().length == 0) return;

        tick++;
        if (tick >= frameDelay) {
            tick = 0;
            frameIndex = (frameIndex + 1) % framesOpt.get().length;
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

    public void setState(T newState) {
        if (newState != currentState) {
            currentState = newState;
            frameIndex = 0;
            tick = 0;
        }
    }
}
