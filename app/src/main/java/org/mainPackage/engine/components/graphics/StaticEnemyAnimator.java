package org.mainPackage.engine.components.graphics;

import org.mainPackage.enums.StaticEnemyState;
import org.mainPackage.util.SpriteLoader;

public class StaticEnemyAnimator extends GenericAnimator<StaticEnemyState> {

   
    public StaticEnemyAnimator() {
        try {
            SpriteLoader loader = new SpriteLoader("/obstacle.png");

            addAnimation(StaticEnemyState.IDLE, loader.getFramesByPixels(0, 0, 1, 32, 32), 12);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

  
}
