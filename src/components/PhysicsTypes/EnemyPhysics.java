package components.PhysicsTypes;
import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;

import colliders.EnemyCollider;
import components.Physics;
import game_parts.*;

public class EnemyPhysics extends Physics{
        private EnemyCollider collider;
        private Entity sonic;
        private int maxChaseDistance = 40, speed, spawnX;
        private action enemyAction = idle;
        private direction enemyDirection = left;
        private HashMap<direction, Boolean> canMove = new HashMap<>();

        public EnemyPhysics(int xS, Entity o, ArrayList<Rectangle>tList, Entity s){
        super(xS, 0, o, tlist);
        collider = new EnemyCollider(tiles, this, s.getComponent(PHYSICS));
        sonic = s;
        this.speed = speed;
        canMove.put(left, true);
        canMove.put(right, true);
    }

    @Override
    public void update(){
        checkCollisions();
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
        if (goTo > owner.getX() && canMove.get(left)){
            owner.moveX(speed);
            enemyAction = walking;
            enemyDirection = right;
        }
        else if (goTo < owner.getX() && canMove.get(right)){
            owner.moveX(-speed);
            enemyAction = walking;
            enemyDirection = left;
        }
    }
    public action getAction(){ return enemyAction; }
    public direction getDirection(){ return enemyDirection; }
    public void setMovement(direction dir, boolean bool){ canMove.replace(dir, bool); }
}
