package graphics;

import entities.Component;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public abstract class GenericAnimator<T> implements Component {
    protected HashMap<T, BufferedImage[]> animations = new HashMap<>();
    protected T currentState;
    protected int frameIndex = 0;
    protected int frameDelay = 10;
    protected int tick = 0;

    public void setState(T newState) {
        if (currentState != newState) {
            currentState = newState;
            frameIndex = 0;
            tick = 0;
        }
    }

    public BufferedImage getCurrentFrame() {
        BufferedImage[] frames = animations.get(currentState);
        if (frames == null || frames.length == 0) return null;

        tick++;
        if (tick >= frameDelay) {
            tick = 0;
            frameIndex = (frameIndex + 1) % frames.length;
        }

        return frames[frameIndex];
    }

    public void addAnimation(T state, BufferedImage[] frames, int delay) {
        animations.put(state, frames);
        if (currentState == null) {
            currentState = state;
        }
    }

    @Override
    public void update() {
       
    }
}
