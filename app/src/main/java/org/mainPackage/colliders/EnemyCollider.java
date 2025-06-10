package org.mainPackage.colliders;

import java.awt.Rectangle;
import java.util.ArrayList;

import org.mainPackage.engine.components.PhysicsTypes.EnemyPhysics;
import org.mainPackage.engine.components.PhysicsTypes.PlayerPhysics;
import org.mainPackage.game_parts.action;

public class EnemyCollider extends Collider{
    private PlayerPhysics sonicPh;
    public EnemyCollider(ArrayList<Rectangle> list, EnemyPhysics phy, PlayerPhysics s){
        super(list, phy);
        sonicPh = s;
        sensor = physic.getHitbox();
    }

    @Override
    public void checkCollisions(){
        /*check collisions with the player*/
        if (sensor.intersects(sonicPh.getHitbox())){
            playerCollision();
        }
        else {
            /*check collisions with the surroundings*/
            for (Rectangle tile : tiles) {
                if (sensor.intersects(tile)){
                    physic.setMovement(physic.getDirection(), false);
                }
            }
        }
    }
    public void playerCollision(){
        if(sonicPh.getAction() == action.jumping){
            physic.die();
        } else if (sonicPh.isHurt() == false){
            sonicPh.takeDamage();
        }
    }
}
