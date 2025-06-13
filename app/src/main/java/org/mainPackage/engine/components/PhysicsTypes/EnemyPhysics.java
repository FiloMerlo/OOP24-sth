package org.mainPackage.engine.components.PhysicsTypes;

import java.util.ArrayList;
import java.util.HashMap;
import java.awt.geom.Rectangle2D;

import org.mainPackage.engine.entities.api.*;
import org.mainPackage.enums.action;
import org.mainPackage.enums.direction;
import org.mainPackage.colliders.EnemyCollider;
import org.mainPackage.engine.components.PhysicsComponent;
import org.mainPackage.engine.components.TransformComponent;

public class EnemyPhysics extends PhysicsComponent{
        private Entity sonic;
        private double maxChaseDistance = 320, spawnX;
        private action enemyAction = action.idle;
        private direction enemyDirection = direction.left;
        private HashMap<direction, Boolean> canMove = new HashMap<>();

        public EnemyPhysics(int xS, Entity o, ArrayList<Rectangle2D.Float>tList, Entity s){
        super(xS, 3, o, tList); /*the falling speed is always 3 by default, the horizontal speed determines if its a chasingEnemy or staticEnemy*/
        collider = new EnemyCollider(tiles, this, (PlayerPhysics)s.getComponent(PhysicsComponent.class));
        sonic = s;
        spawnX = owner.getComponent(TransformComponent.class).getX();
        canMove.put(direction.left, true);
        canMove.put(direction.right, true);
        canMove.put(direction.down, true);
        canMove.put(direction.up, true);
    }

    @Override
    public void update(float deltaTime){
        super.update(deltaTime);
        collider.checkCollisions();
        if (xSpeed > 0){
            chase();
        }
    }

    public void chase(){
        /*If sonic is in range, the enemy chases him with all his speed. If not, it tries to return to his spawn point*/
        if (Math.abs(sonic.getComponent(TransformComponent.class).getX() - owner.getComponent(TransformComponent.class).getX()) <= maxChaseDistance) {
            moveX(sonic.getComponent(TransformComponent.class).getX());
        } else {
            if (owner.getComponent(TransformComponent.class).getX() != spawnX){
                moveX(spawnX);
            }
        }
        moveY();
    }

    public void moveX(double goTo){
        if (goTo > owner.getComponent(TransformComponent.class).getX() && canMove.get(direction.left)){
            owner.getComponent(TransformComponent.class).moveX(xSpeed);
            enemyAction = action.walking;
            enemyDirection = direction.right;
        }
        else if (goTo < owner.getComponent(TransformComponent.class).getX() && canMove.get(direction.right)){
            owner.getComponent(TransformComponent.class).moveX(-xSpeed);
            enemyAction = action.walking;
            enemyDirection = direction.left;
        }
    }
    private void moveY() {
        if (canMove.get(direction.down)){
            owner.getComponent(TransformComponent.class).moveY(ySpeed);
        }
    }
    
    public action getAction(){ return enemyAction; }
    public direction getDirection(){ return enemyDirection; }
    public void setMovement(direction dir, boolean bool){ canMove.replace(dir, bool); }
}
