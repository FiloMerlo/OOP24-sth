package org.mainPackage.colliders;

import org.mainPackage.components.PhysicsTypes.PlayerPhysics;
import org.mainPackage.game_parts.direction;
import java.util.ArrayList;
import java.awt.Rectangle;

public class PlayerCollider extends Collider {
    private boolean colliding = false;
    private direction direction;
    public PlayerCollider(ArrayList<Rectangle> list, PlayerPhysics s, direction d, int offX, int offY) {
        super(list, s);
        direction = d;
        sensor = new Rectangle(physic.getOwner().getX() + offX, physic.getOwner().getY() + offY);
    }

    public void checkCollisions() {
        for (Rectangle t : tiles) {
            if (sensor.intersects(t) && !colliding) {
                collisionStarted();
            } else if (!sensor.intersects(t) && colliding){
                collisionEnded();
            }
        }
    }
    public void collisionStarted(){
        colliding = true;
        physic.setMovement(direction, false);
    }
    public void collisionEnded(){
        colliding = false;
        physic.setMovement(direction, true);
    }
}