package org.mainPackage.colliders;
import java.awt.Rectangle;
import java.util.ArrayList;

import org.mainPackage.engine.components.PhysicsTypes.PlayerPhysics;
import org.mainPackage.engine.components.PhysicsTypes.RingPhysics;

public class RingCollider extends Collider{
    private PlayerPhysics sonicPh;
    public RingCollider(ArrayList<Rectangle> list, RingPhysics rP, PlayerPhysics s) {
        super(list, rP);
        sonicPh = s;
        sensor = physic.getHitbox();
    }

    public void checkCollisions(){
        if (sonicPh.getHitbox().intersects(sensor)) {
            pickUp();
        } else {
            for (Rectangle tile : tiles) {
                if (sensor.intersects(tile)) {
                    ((RingPhysics)physic).bounce();//bounce
                }
            }
        }
    }
    public void pickUp(){
        sonicPh.gotRing();
        physic.die();
    }
}
