package org.mainPackage.components.PhysicsTypes;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.Rectangle;

import org.mainPackage.colliders.EnemyCollider;
import org.mainPackage.components.Physics;
import org.mainPackage.game_parts.*;

public class EnemyPhysics extends Physics{
        private EnemyCollider collider;
        private Entity sonic;
        private int maxChaseDistance = 40, speed, spawnX;
        private action enemyAction = action.idle;
        private direction enemyDirection = direction.left;
        private HashMap<direction, Boolean> canMove = new HashMap<>();

        public EnemyPhysics(int xS, Entity o, ArrayList<Rectangle>tList, Entity s, int sp){
        super(xS, 0, o, tList);
        collider = new EnemyCollider(tiles, this, (PlayerPhysics)s.getComponent(ComponentType.PHYSICS));
        sonic = s;
        this.speed = sp;
        canMove.put(direction.left, true);
        canMove.put(direction.right, true);
    }

    @Override
    public void update(){
        collider.checkCollisions();
        if (speed > 0){
            chase();
        }
    }

    public void chase(){
        if (sonic.getX() - owner.getX() <= maxChaseDistance) {
            move(sonic.getX());
        } else {
            if (owner.getX() != spawnX){
                move(spawnX);
            }
        }
    }
    public void move(int goTo){
        if (goTo > owner.getX() && canMove.get(direction.left)){
            owner.moveX(speed);
            enemyAction = action.walking;
            enemyDirection = direction.right;
        }
        else if (goTo < owner.getX() && canMove.get(direction.right)){
            owner.moveX(-speed);
            enemyAction = action.walking;
            enemyDirection = direction.left;
        }
    }
    public action getAction(){ return enemyAction; }
    public direction getDirection(){ return enemyDirection; }
    public void setMovement(direction dir, boolean bool){ canMove.replace(dir, bool); }
}
