package colliders;
import java.util.HashMap;

import javax.naming.TimeLimitExceededException;
import javax.swing.text.html.parser.Entity;

import game_parts.action;
import game_parts.direction;
import entities.Enemy;
import entities.Sonic;
import org.dyn4j.geometry.Rectangle;

import components.PhysicsTypes.PlayerPhysics;

public class EnemyCollider extends Collider{
    private PlayerPhysics sonicPh;
    public EnemyCollider(ArrayList<Rectangle> list, EnemyPhysics phy, PlayerPhysics s){
        super(list, phy);
        sonic = s;
        sensor = physic.getHitbox();
    }

    @Override
    public void checkCollisions(){
        /*check collisions with the player*/
        if (sensor.intersects(sonic.getHitbox())){
            playerCollision();
        }
        else {
            /*check collisions with the surroundings*/
            for (Rectangle tile : tiles) {
                if (sensor.intersects(tile)){
                    physic.setMovement(physic.getDirection(), false);
                }
            }
        }
    }
    @Override
    public void playerCollision(){
        if(s.getPlayerAction() == jumping){
            physic.die();
        } else {
            sonic.takeDamage();
        }
    }
}
