package graphics;

import entities.StaticEnemyState;
import util.SpriteLoader;

public class StaticEnemyAnimator extends GenericAnimator<StaticEnemyState> {

    public StaticEnemyAnimator() {
        try {
            SpriteLoader loader = new SpriteLoader("Custom Edited - Sonic the Hedgehog Customs - Objects.png");

            addAnimation(StaticEnemyState.IDLE, loader.getFramesByPixels(0, 0, 6, 32, 32), 12);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
