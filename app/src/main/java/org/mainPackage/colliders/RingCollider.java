package org.mainPackage.colliders;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import org.mainPackage.engine.components.PhysicsTypes.RingPhysics;
import org.mainPackage.engine.components.PhysicsTypes.PlayerPhysics;

public class RingCollider extends Collider{
    private PlayerPhysics sonicPh;
    public RingCollider(ArrayList<Rectangle2D.Float> list, RingPhysics rP, Rectangle2D.Float rect, PlayerPhysics s) {
        super(list, rP, rect);
        sonicPh = s;
        sensor = physic.getHitbox();
    }

    public void checkCollisions(){
        if (sonicPh.getHitbox().intersects(sensor)) {
            pickUp();
        } else {
            for (Rectangle2D.Float tile : tiles) {
                if (sensor.intersects(tile)) {
                    ((RingPhysics)physic).bounce(tile);//bounce
                }
            }
        }
    }
    public void pickUp(){
        sonicPh.gotRing();
        physic.die();
    }
}
