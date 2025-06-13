package org.mainPackage.colliders;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import org.mainPackage.engine.components.PhysicsTypes.EnemyPhysics;
import org.mainPackage.engine.components.PhysicsTypes.PlayerPhysics;
import org.mainPackage.enums.action;

public class EnemyCollider extends PlayerCollider{
    private PlayerPhysics sonicPh;
    public EnemyCollider(ArrayList<Rectangle2D.Float> list, EnemyPhysics phy, PlayerPhysics s){
        super(list, phy);
        sonicPh = s;
        sensor = physic.getHitbox();
    }

    @Override
    public void checkCollisions(){
        /*check collisions with surroundings*/
        super.checkCollisions();
        /*check collisions with the player*/
        if (sensor.intersects(sonicPh.getHitbox())){
            playerCollision();
        }
    }
    
    public void playerCollision(){
        if(sonicPh.getAction() == action.jumping){
            physic.die();
            sonicPh.smallJump();
        } else {
            sonicPh.getHurt();
        }
    }
}
