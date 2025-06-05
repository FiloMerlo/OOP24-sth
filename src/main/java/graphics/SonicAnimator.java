package graphics;

import java.awt.image.BufferedImage;
import java.util.HashMap;

import entities.action;
import util.SpriteLoader;

public class SonicAnimator {
    private HashMap<action, BufferedImage[]> animations = new HashMap<>();
    private HashMap<action, Integer> frameIndices = new HashMap<>();
    private HashMap<action, Integer> frameTicks = new HashMap<>();
    private HashMap<action, Integer> frameDelays = new HashMap<>();
    private SpriteLoader spriteLoader;

    public SonicAnimator() {
        try {
            spriteLoader = new SpriteLoader("/PC Computer - Sonic Mania - Sonic the Hedgehog copy.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
        loadAnimations();
        initializeFrameData();
    }

    private void loadAnimations() {
        animations.put(action.idle, spriteLoader.getFramesByPixels(0, 0, 4, 48, 48));
        animations.put(action.walking, spriteLoader.getFramesByPixels(0, 48, 6, 48, 48));
        animations.put(action.running, spriteLoader.getFramesByPixels(0, 96, 8, 48, 48));
        animations.put(action.jumping, spriteLoader.getFramesByPixels(0, 144, 4, 48, 48));
        animations.put(action.falling, spriteLoader.getFramesByPixels(192, 144, 2, 48, 48));
        animations.put(action.skidding, spriteLoader.getFramesByPixels(0, 192, 2, 48, 48));
        animations.put(action.dashing, spriteLoader.getFramesByPixels(288, 96, 3, 48, 48));
        animations.put(action.hurt, spriteLoader.getFramesByPixels(0, 336, 4, 48, 48));
    }

    private void initializeFrameData() {
        for (action act : action.values()) {
            frameIndices.put(act, 0);
            frameTicks.put(act, 0);
        }

        frameDelays.put(action.idle, 15);
        frameDelays.put(action.walking, 8);
        frameDelays.put(action.running, 6);
        frameDelays.put(action.jumping, 4);
        frameDelays.put(action.falling, 10);
        frameDelays.put(action.skidding, 12);
        frameDelays.put(action.dashing, 3);
        frameDelays.put(action.hurt, 20);
    }

    public BufferedImage getFrame(action currentAction) {
        BufferedImage[] frames = animations.get(currentAction);
        if (frames == null || frames.length == 0) return null;

        int currentTick = frameTicks.get(currentAction);
        int currentFrameIndex = frameIndices.get(currentAction);
        int currentDelay = frameDelays.get(currentAction);

        currentTick++;

        if (currentTick >= currentDelay) {
            currentTick = 0;
            currentFrameIndex = (currentFrameIndex + 1) % frames.length;
        }

        frameTicks.put(currentAction, currentTick);
        frameIndices.put(currentAction, currentFrameIndex);

        return frames[currentFrameIndex];
    }

    public void resetAnimation(action actionToReset) {
        frameIndices.put(actionToReset, 0);
        frameTicks.put(actionToReset, 0);
    }

    public void resetAllAnimations() {
        for (action act : action.values()) {
            resetAnimation(act);
        }
    }

    public void setFrameDelay(action actionToModify, int newDelay) {
        frameDelays.put(actionToModify, Math.max(1, newDelay));
    }

    public BufferedImage getCurrentFrame(action currentAction) {
        BufferedImage[] frames = animations.get(currentAction);
        if (frames == null || frames.length == 0) return null;
        return frames[frameIndices.get(currentAction)];
    }

    public boolean hasCompletedCycle(action currentAction) {
        return frameIndices.get(currentAction) == 0 && frameTicks.get(currentAction) == 0;
    }

    public int getFrameCount(action currentAction) {
        BufferedImage[] frames = animations.get(currentAction);
        return frames != null ? frames.length : 0;
    }
}