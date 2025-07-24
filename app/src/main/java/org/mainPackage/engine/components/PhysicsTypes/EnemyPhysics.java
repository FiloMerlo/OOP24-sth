package org.mainPackage.engine.components.PhysicsTypes;

import java.util.ArrayList;
import java.awt.geom.Rectangle2D;


import org.mainPackage.engine.entities.impl.EntityImpl;
import org.mainPackage.engine.events.api.EventType;
import org.mainPackage.engine.events.impl.GameEvent;
import org.mainPackage.enums.action;
import org.mainPackage.enums.direction;
import org.mainPackage.engine.components.PhysicsComponent;
import org.mainPackage.engine.components.TransformComponent;

public class EnemyPhysics extends PhysicsComponent{
        private EntityImpl sonic; /*a reference to the player the Enemies need to chase*/
        private float maxChaseDistance = 300, spawnX, fallSpeed = 0.4f;
        private action enemyAction = action.idle;
        private direction enemyDirection = direction.left;

        public EnemyPhysics(float xS, EntityImpl o, ArrayList<Rectangle2D.Float>tList, EntityImpl s){
        super(o, tList); /*the falling speed is always 0.2 by default, the horizontal speed determines if its a chasingEnemy or staticEnemy*/
        xSpeed = -xS;
        ySpeed = fallSpeed;
        sonic = s;
        spawnX = owner.getComponent(TransformComponent.class).getX();
    }

    @Override
    public void update(float deltaTime){
        /*Checking if the Enemy is touching the player has the priority over the collisions with the surroundings*/
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
        /*If sonic is in range, the enemy chases him with all his speed. 
        If not, it tries to return to his spawn point*/
        if (Math.abs(playerTransform.getX() - ownTransform.getX()) <= maxChaseDistance) {
            moveX(playerTransform.getX());
        } else {
            if (ownTransform.getX() != spawnX){
                moveX(spawnX);
            }
        }
    }

    public void moveX(double goTo){
        /*Determine enemy direction and speed needed to get to the horizontal position "goTo"*/
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

        /*Check for collisions before moving*/
        if (canGoThere(enemyDirection, xSpeed)){
            enemyAction = action.walking;
            owner.getComponent(TransformComponent.class).moveX(xSpeed);
        }
    }
    
    private void moveY() {
        if (canGoThere(direction.down, fallSpeed)){
            if (ySpeed < fallSpeed){
                ySpeed = fallSpeed;
            }
        } 
        /*LANDING.    if Sonic is't already on the ground, he lands*/
        else if (canGoThere(direction.down, Float.MIN_VALUE)){
            landing();
        }
        else{
            ySpeed = fallSpeed;
        }
        owner.getComponent(TransformComponent.class).moveY(ySpeed);
    }
    
    public action getAction(){ return enemyAction; }
    public direction getDirection(){ return enemyDirection; }
}
