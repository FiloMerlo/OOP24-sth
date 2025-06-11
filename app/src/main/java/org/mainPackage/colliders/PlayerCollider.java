package org.mainPackage.colliders;

import org.mainPackage.engine.components.PhysicsTypes.EnemyPhysics;
import org.mainPackage.engine.components.PhysicsTypes.PlayerPhysics;
import org.mainPackage.game_parts.direction;
import java.util.ArrayList;
import java.awt.Rectangle;

public class PlayerCollider extends Collider {
    public PlayerCollider(ArrayList<Rectangle> list, PlayerPhysics s) {
        super(list, s);
    }
    public PlayerCollider(ArrayList<Rectangle> list, EnemyPhysics phy) {
        super(list, phy);
    }

    public void checkCollisions() {
        for (direction dir : direction.values()) {
            physic.setMovement(dir, true);
        }

        for (Rectangle tile : tiles) {
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