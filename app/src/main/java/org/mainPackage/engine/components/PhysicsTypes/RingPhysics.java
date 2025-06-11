package org.mainPackage.engine.components.PhysicsTypes;
import java.awt.Point;

import java.awt.Rectangle;
import java.util.ArrayList;

import org.mainPackage.colliders.RingCollider;
import org.mainPackage.engine.components.PhysicsComponent;
import org.mainPackage.engine.components.TransformComponent;
import org.mainPackage.engine.entities.api.Entity;
import org.mainPackage.game_parts.direction;

public class RingPhysics extends PhysicsComponent {
    private RingCollider collider;
    private int maxDistance = 10;
    private Point spawn;

    public RingPhysics(Entity o, ArrayList<Rectangle> tList, PlayerPhysics pp){
        super(0, 0, o, tList);
        collider = new RingCollider(tList, this, pp);
        spawn = new Point(owner.getComponent(TransformComponent.class).getX(), owner.getComponent(TransformComponent.class).getY());
    }

    public void update(float deltaTime){
        super.update(deltaTime);
        collider.checkCollisions();
        if (xSpeed > 0){
            if (owner.getComponent(TransformComponent.class).getX() - spawn.getX() >= maxDistance || owner.getComponent(TransformComponent.class).getX() - spawn.getX() <= -maxDistance){
                xSpeed = 0;
            }  
            if (owner.getComponent(TransformComponent.class).getY() - spawn.getY() >= maxDistance || owner.getComponent(TransformComponent.class).getY() - spawn.getY() <= -maxDistance){
                ySpeed = 0;
            }
            collider.getSensor().translate(xSpeed, ySpeed);
            owner.getComponent(TransformComponent.class).moveX(xSpeed);
            owner.getComponent(TransformComponent.class).moveY(ySpeed);
        }
    }

    public void bounce(Rectangle tileMet){
        if (tileMet.getY() >= owner.getComponent(TransformComponent.class).getY() + owner.getComponent(TransformComponent.class).getHeight() ||
            tileMet.getY() <= owner.getComponent(TransformComponent.class).getY()){
            ySpeed = -ySpeed;
        }
        else {
            xSpeed = -xSpeed;
        }
    }

    public void spredOut(){
        xSpeed = (int)(Math.random() * 4 - 0);
        ySpeed = (int)(Math.random() * 4 - 0);
    }

    @Override
    public void setMovement(direction dir, boolean b) {}
}
