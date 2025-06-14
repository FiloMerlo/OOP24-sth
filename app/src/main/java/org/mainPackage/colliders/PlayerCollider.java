package org.mainPackage.colliders;

import org.mainPackage.engine.components.PhysicsTypes.EnemyPhysics;
import org.mainPackage.engine.components.PhysicsTypes.PlayerPhysics;
import org.mainPackage.enums.direction;

import java.util.ArrayList;
import java.awt.geom.Rectangle2D;

public class PlayerCollider extends Collider {
    public PlayerCollider(ArrayList<Rectangle2D.Float> list, PlayerPhysics s, Rectangle2D.Float rect) {
        super(list, s, rect);
    }
    public PlayerCollider(ArrayList<Rectangle2D.Float> list, EnemyPhysics phy, Rectangle2D.Float rect) {
        super(list, phy, rect);
    }

    public void checkCollisions() {
        for (direction dir : direction.values()) {
            physic.setMovement(dir, true);
        }

        for (Rectangle2D.Float tile : tiles) {
            if (sensor.intersects(tile)){
                if (sensor.getX() >= tile.getX() + tile.getWidth()){
                    physic.setMovement(direction.left, false);
                }
                else if(sensor.getX() + sensor.getWidth() <= tile.getX()){
                    physic.setMovement(direction.right, false);
                }
                else if (sensor.getY() + sensor.getHeight() <= tile.getY()){
                    physic.setMovement(direction.up, false);
                }
                else if (sensor.getY() >= tile.getY() + tile.getHeight()){
                    physic.setMovement(direction.down, false);
                }
            }
        }
    }
}