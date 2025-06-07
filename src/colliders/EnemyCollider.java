package colliders;
import java.util.HashMap;

import javax.naming.TimeLimitExceededException;
import javax.swing.text.html.parser.Entity;

import game_parts.action;
import game_parts.direction;
import entities.Enemy;
import entities.Sonic;
import org.dyn4j.geometry.Rectangle;

public class EnemyCollider extends Collider{
    private Entity sonic;
    public EnemyCollider(ArrayList<Rectangle> list, EnemyPhysics phy, Entity s){
        super(list, phy);
        sonic = s;
        sensor = physic.getHitbox();
    }

    @Override
    public void checkCollisions(){
        Rectangle sonicHitbox = sonic.getComponent(SonicPhysics.class).getHitbox();

        /*First, i convert the local coordinates in global coordinates*/
        Rectangle globalHitbox = SwingUtilities.convertRectangle(sonic.getComponent(Body.class), sonicHitbox, sonic.getPanel());
        if (sensor.intersects(sonicHitbox)){
            collision();
        }
        else {
            for (Rectangle tile : tiles) {
                if (sensor.intersects(tile)){
                    physic.setMovement(physic.getDirection(), false);
                }
            }
        }
    }
    @Override
    public void collision(){
        if(s.getPlayerAction() == jumping){
            physic.die();
        } else {
            sonic.getComponent(SonicPhysics.class).takeDamage();
        }
    }
}
