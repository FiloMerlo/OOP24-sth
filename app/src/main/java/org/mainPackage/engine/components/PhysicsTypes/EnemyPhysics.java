package org.mainPackage.engine.components.PhysicsTypes;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.Rectangle;

import org.mainPackage.engine.entities.api.*;
import org.mainPackage.colliders.EnemyCollider;
import org.mainPackage.engine.components.PhysicsComponent;
import org.mainPackage.engine.components.TransformComponent;
import org.mainPackage.game_parts.action;
import org.mainPackage.game_parts.direction;

public class EnemyPhysics extends PhysicsComponent{
        private EnemyCollider collider;
        private Entity sonic;
        private int maxChaseDistance = 40, speed, spawnX;
        private action enemyAction = action.idle;
        private direction enemyDirection = direction.left;
        private HashMap<direction, Boolean> canMove = new HashMap<>();

        public EnemyPhysics(int xS, Entity o, ArrayList<Rectangle>tList, Entity s){
        super(xS, 3, o, tList);
        collider = new EnemyCollider(tiles, this, (PlayerPhysics)s.getComponent(PhysicsComponent.class));
        sonic = s;
        canMove.put(direction.left, true);
        canMove.put(direction.right, true);
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
        if (Math.abs(sonic.getComponent(TransformComponent.class).getX() - owner.getComponent(TransformComponent.class).getX()) <= maxChaseDistance) {
            moveX(sonic.getComponent(TransformComponent.class).getX());
        } else {
            if (owner.getComponent(TransformComponent.class).getX() != spawnX){
                moveX(spawnX);
            }
        }
        moveY();
    }

    public void moveX(int goTo){
        if (goTo > owner.getComponent(TransformComponent.class).getX() && canMove.get(direction.left)){
            owner.getComponent(TransformComponent.class).moveX(speed);
            enemyAction = action.walking;
            enemyDirection = direction.right;
        }
        else if (goTo < owner.getComponent(TransformComponent.class).getX() && canMove.get(direction.right)){
            owner.getComponent(TransformComponent.class).moveX(-speed);
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
