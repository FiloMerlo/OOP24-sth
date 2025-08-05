package org.mainPackage.engine.components.graphics;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;

import org.mainPackage.engine.components.Component;

/**
 * GenericAnimator is a reusable animation component
 *
 * It manages frame transitions, delays, and animation strategies (looping or progressive)
 * based on the current state.
 *
 * @param <T> the type of the animation state, usually an enum
 */
public abstract class GenericAnimator<T> implements Component {

    /**
     * AnimationData stores the frames, delay, and strategy for an animation state.
     *
     * @param frames   the array of sprite frames
     * @param delay    the number of ticks between frame updates
     * @param strategy the animation update strategy loop or stop at the end
     */
    private record AnimationData<T>(
        BufferedImage[] frames,
        int delay,
        BiConsumer<GenericAnimator<T>, AnimationData<T>> strategy
    ) {}

    private final Map<T, AnimationData<T>> animations = new HashMap<>();

    protected T currentState;
    protected int frameIndex = 0;
    protected int tick = 0;

    @Override
    public void update(final float deltaTime) {
        Optional.ofNullable(animations.get(currentState))
                .ifPresent(anim -> anim.strategy().accept(this, anim));
    }

    /**
     * Strategy for looping animations
     * Frames repeat in a cycle
     */
    private static <T> BiConsumer<GenericAnimator<T>, AnimationData<T>> loopingStrategy() {
        return (animator, anim) -> {
            animator.tick++;
            if (animator.tick >= anim.delay()) {
                animator.tick = 0;
                animator.frameIndex = (animator.frameIndex + 1) % anim.frames().length;
            }
        };
    }

    /**
     * Strategy for non-looping animations
     * Plays frames once and stops at the last one
     */
    private static <T> BiConsumer<GenericAnimator<T>, AnimationData<T>> defaultStrategy() {
        return (animator, anim) -> {
            animator.tick++;
            if (animator.frameIndex < anim.frames().length - 1) {
                if (animator.tick >= anim.delay()) {
                    animator.tick = 0;
                    animator.frameIndex++;
                }
            }
        };
    }

    /**
     * Determines whether the state should use a looping strategy.
     *
     * @param state the animation state
     * @return true if the state should loop, false otherwise
     */
    private boolean shouldLoop(final T state) {
        final String name = state.toString().toLowerCase();
        return name.equals("idle") || name.equals("spinning");
    }

    /**
     * Adds a new animation for a given state.
     *
     * @param state  the animation state
     * @param frames the frames of the animation
     * @param delay  the delay between frame changes
     */
    public void addAnimation(final T state, final BufferedImage[] frames, final int delay) {
        final BiConsumer<GenericAnimator<T>, AnimationData<T>> strategy =
                shouldLoop(state) ? loopingStrategy() : defaultStrategy();
        animations.put(state, new AnimationData<>(frames, delay, strategy));

        if (currentState == null) {
            currentState = state;
        }
    }

    /**
     * Returns the current frame of the animation, if available.
     *
     * @return an Optional containing the current BufferedImage frame
     */
    public Optional<BufferedImage> getCurrentFrame() {
        return Optional.ofNullable(animations.get(currentState))
                .filter(anim -> anim.frames().length > 0)
                .map(anim -> anim.frames()[frameIndex]);
    }

    /**
     * Sets the current animation state.
     * Resets frame index and tick if the state has changed.
     *
     * @param newState the new animation state
     */
    public void setState(final T newState) {
        if (!newState.equals(currentState)) {
            currentState = newState;
            frameIndex = 0;
            tick = 0;
        }
    }
}
