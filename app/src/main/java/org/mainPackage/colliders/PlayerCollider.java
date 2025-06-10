package org.mainPackage.colliders;

import org.mainPackage.engine.components.TransformComponent;
import org.mainPackage.engine.components.PhysicsTypes.PlayerPhysics;
import org.mainPackage.game_parts.direction;
import java.util.ArrayList;
import java.awt.Rectangle;

public class PlayerCollider extends Collider {
    private boolean colliding = false;
    private direction direction;
    public PlayerCollider(ArrayList<Rectangle> list, PlayerPhysics s, direction d, int offX, int offY) {
        super(list, s);
        direction = d;
        sensor = new Rectangle(physic.getOwner().getComponent(TransformComponent.class).getX() + offX, 
        physic.getOwner().getComponent(TransformComponent.class).getY() + offY);
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