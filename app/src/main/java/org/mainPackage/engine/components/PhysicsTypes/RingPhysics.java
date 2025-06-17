package org.mainPackage.engine.components.PhysicsTypes;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import org.mainPackage.colliders.Collider;
import org.mainPackage.colliders.RingCollider;
import org.mainPackage.engine.components.PhysicsComponent;
import org.mainPackage.engine.components.TransformComponent;
import org.mainPackage.engine.entities.impl.EntityImpl;
import org.mainPackage.enums.direction;

public class RingPhysics extends PhysicsComponent {
    private double maxDistance = 10, spawnX, spawnY;

    public RingPhysics(EntityImpl o, ArrayList<Rectangle2D.Float> tList, PlayerPhysics pp){
        super(0, 0, o, tList);
        colliders.add(new RingCollider(tList, this, hitbox, pp));
        spawnX = owner.getComponent(TransformComponent.class).getX();
        spawnY = owner.getComponent(TransformComponent.class).getY();
    }

    public void update(float deltaTime){
        super.update(deltaTime);
        if (xSpeed > 0){
            if (owner.getComponent(TransformComponent.class).getX() - spawnX >= maxDistance || owner.getComponent(TransformComponent.class).getX() - spawnX <= -maxDistance){
                xSpeed = 0;
                ySpeed = 0;
            }  else if (owner.getComponent(TransformComponent.class).getY() - spawnY >= maxDistance || owner.getComponent(TransformComponent.class).getY() - spawnY <= -maxDistance){
                xSpeed = 0;
                ySpeed = 0;
            }
            for (Collider coll : colliders) {
                coll.getSensor().x += xSpeed;
                coll.getSensor().y += ySpeed;
            }
            owner.getComponent(TransformComponent.class).moveX(xSpeed);
            owner.getComponent(TransformComponent.class).moveY(ySpeed);
        }
    }

    public void bounce(Rectangle2D.Float tileMet){
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
