package components.PhysicsTypes;
import java.util.HashMap;

import colliders.EnemyCollider;
import game_parts.action;
import game_parts.direction;

public class EnemyPhysics extends Physics{
        private HashMap<direction, Float> directionalLimit = new HashMap<>();
        private EnemyCollider collider;
        private MovableBody sonic;
        private int maxChaseDistance = 40, speed, spawnX;
        private action enemyAction = idle;
        private direction enemyDirection = left;
        private HashMap<direction, Boolean> canMove = new HashMap<>();

        public EnemyPhysics(MovableBody b, MovableBody s, int speed){
        super(0, 0, b);
        /*collider = new RingCollider(TODO deve passare la lista di tiles, this, this.getBody().getOwner().getManager().getEnList().get(0);*/
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
        if (sonic.getX() - body.getX() <= maxChaseDistance) {
            move(sonic.getX());
        } else {
            if (body.getX() != spawnX){
                move(spawnX);
            }
        }
    }
    public void move(int goTo){
        if (goTo > body.getX() && canMove.get(left)){
            body.moveX(speed);
            enemyAction = walking;
            enemyDirection = right;
        }
        else if (goTo < body.getX() && canMove.get(right)){
            body.moveX(-speed);
            enemyAction = walking;
            enemyDirection = left;
        }
    }
    public action getAction(){ return enemyAction; }
    public direction getDirection(){ return enemyDirection; }
    public void setMovement(direction dir, boolean bool){ canMove.replace(dir, bool); }
}
