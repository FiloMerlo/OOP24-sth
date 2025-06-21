package org.mainPackage.engine.components.PhysicsTypes;

import java.util.ArrayList;
import java.util.HashMap;
import java.awt.geom.Rectangle2D;


import org.mainPackage.engine.entities.impl.EntityImpl;
import org.mainPackage.engine.events.api.EventType;
import org.mainPackage.engine.events.impl.GameEvent;
import org.mainPackage.enums.action;
import org.mainPackage.enums.direction;
import org.mainPackage.engine.components.PhysicsComponent;
import org.mainPackage.engine.components.TransformComponent;

public class EnemyPhysics extends PhysicsComponent{
        private EntityImpl sonic;
        private float maxChaseDistance = 320, spawnX, fallSpeed = 0.2f;
        private action enemyAction = action.idle;
        private direction enemyDirection = direction.left;

        public EnemyPhysics(float xS, EntityImpl o, ArrayList<Rectangle2D.Float>tList, EntityImpl s){
        super(o, tList); /*the falling speed is always 3 by default, the horizontal speed determines if its a chasingEnemy or staticEnemy*/
        xSpeed = -xS;
        ySpeed = fallSpeed;
        sonic = s;
        spawnX = owner.getComponent(TransformComponent.class).getX();
    }

    @Override
    public void update(float deltaTime){
        if (checkIntersection(sonic.getComponent(TransformComponent.class))){
            if (sonic.getComponent(PlayerPhysics.class).getAction() == action.jumping){
                notifyObservers(new GameEvent(EventType.ENTITY_DEAD, this.owner));   
                System.out.println("Enemy killed!");
            } else {
                sonic.getComponent(PlayerPhysics.class).hit();
            }
        }
        if (xSpeed != 0){
            chase();
        }
        moveY();
    }

    public void chase(){
        TransformComponent playerTransform = sonic.getComponent(TransformComponent.class);
        TransformComponent ownTransform = owner.getComponent(TransformComponent.class);
        /*If sonic is in range, the enemy chases him with all his speed. If not, it tries to return to his spawn point*/
        if (Math.abs(playerTransform.getX() - ownTransform.getX()) <= maxChaseDistance) {
            moveX(playerTransform.getX());
        } else {
            if (ownTransform.getX() != spawnX){
                moveX(spawnX);
            }
        }
    }

    public void moveX(double goTo){
        /*determine enemy direction and speed needed to get to goTo*/
        if (goTo > owner.getComponent(TransformComponent.class).getX()){
            if (enemyDirection != direction.right){
                enemyDirection = direction.right;
                xSpeed = Math.abs(xSpeed);
            }
        } else if (goTo < owner.getComponent(TransformComponent.class).getX()){
            if (enemyDirection != direction.left){
                enemyDirection = direction.left;
                if (xSpeed > 0){
                    xSpeed *= -1;
                }
            }
        }

        /*check for collisions before moving*/
        if (canGoThere(enemyDirection, xSpeed)){
            enemyAction = action.walking;
            owner.getComponent(TransformComponent.class).moveX(xSpeed);
        }
    }
    private void moveY() {
        if (canGoThere(direction.down, fallSpeed) && ySpeed == 0){
            ySpeed = fallSpeed;
        } 
        if(canGoThere(direction.down, ySpeed)){
            owner.getComponent(TransformComponent.class).moveY(ySpeed);
        } else {
            landing();
        }
    }
    
    public action getAction(){ return enemyAction; }
    public direction getDirection(){ return enemyDirection; }
}
