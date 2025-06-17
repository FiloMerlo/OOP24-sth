package org.mainPackage.colliders;

import org.mainPackage.engine.components.TransformComponent;
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

        float sharedX = 0, sharedY = 0;
        direction xPushDir, yPushDir;
        for (Rectangle2D.Float tile : tiles) {
            if (sensor.intersects(tile)){
                /*detect possible compenetrations*/
                if (sensor.getX() > tile.getX()){
                    physic.setMovement(direction.left, false);
                    sharedX = (float)(tile.getX() + tile.getWidth() - sensor.getX());
                    xPushDir = direction.right;
                } else {
                    physic.setMovement(direction.right, false);
                    sharedX = (float)(sensor.getX() + sensor.getWidth() - tile.getX());
                    xPushDir = direction.left;
                }
                if (sensor.getY() > tile.getY()) {
                    physic.setMovement(direction.up, false);
                    sharedY = (float)(tile.getY() + tile.getHeight() - sensor.getY());
                    yPushDir = direction.down;
                } else {
                    physic.setMovement(direction.down, false);
                    sharedY = (float)(sensor.getY() + sensor.getHeight() - tile.getY());
                    yPushDir = direction.up;
                }
                /*move out of the tile you're compenetrating with
                while(sharedX > 0 || sharedY > 0) {
                    physic.getOwner().getComponent(TransformComponent.class).moveX(0.1f * xPushDir.getValue());
                    physic.getOwner().getComponent(TransformComponent.class).moveY(0.1f * yPushDir.getValue());
                }*/

                if (sensor.getX() >= tile.getX() + tile.getWidth()){
                    physic.setMovement(direction.left, false);
                }
                else if(sensor.getX() + sensor.getWidth() >= tile.getX()){
                    physic.setMovement(direction.right, false);
                }
                else if (sensor.getY() <= tile.getY() + tile.getHeight()){
                    physic.setMovement(direction.up, false);
                }
                else if (sensor.getY() + sensor.getHeight() >= tile.getY()){
                    physic.setMovement(direction.down, false);
                }
            }
        }
    }
}