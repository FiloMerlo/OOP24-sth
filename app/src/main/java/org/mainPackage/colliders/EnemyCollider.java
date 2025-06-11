package org.mainPackage.colliders;

import java.awt.Rectangle;
import java.util.ArrayList;

import org.mainPackage.engine.components.PhysicsTypes.EnemyPhysics;
import org.mainPackage.engine.components.PhysicsTypes.PlayerPhysics;
import org.mainPackage.game_parts.action;

public class EnemyCollider extends PlayerCollider{
    private PlayerPhysics sonicPh;
    public EnemyCollider(ArrayList<Rectangle> list, EnemyPhysics phy, PlayerPhysics s){
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
        } else {
            sonicPh.getHurt();
        }
    }
}
