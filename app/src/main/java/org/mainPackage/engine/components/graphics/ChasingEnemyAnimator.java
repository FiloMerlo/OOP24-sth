package org.mainPackage.engine.components.graphics;

import org.mainPackage.enums.ChasingEnemyState;
import org.mainPackage.util.SpriteLoader;

public class ChasingEnemyAnimator extends GenericAnimator<ChasingEnemyState> {

   public ChasingEnemyAnimator() {
        try {
            SpriteLoader loader = new SpriteLoader("goblin.png");

            addAnimation(ChasingEnemyState.IDLE, loader.getFramesByPixels(0, 0, 4, 64, 64), 10);
            addAnimation(ChasingEnemyState.WALK, loader.getFramesByPixels(0, 64, 6, 64, 64), 8);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
   
}
