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
        /*SE I COLLISORI NON FUNZIONANO COME DOVREBBERO, PROVATE PRIMA DI TUTTO A DECOMMENTARE QUESTA PARTE DI CODICE E COMMENTARE L'IF ATTUALMENTE IN USO 
            Point sensorGlobal = SwingUtilities.convertPoint(body, sensor.getLocation(), physic.getBody().getOwner().getFrame());
            Point sonicHitboxGlobal = SwingUtilities.convertPoint(sonic, sonicHitbox.getLocation(), physic.getBody().getOwner().getFrame());
            Rectangle globalSensor = new Rectangle(sensorGlobal.x, sensorGlobal.y, sensorGlobal.width, sensorGlobal.height);
            Rectangle globalSonic = new Rectangle(sonicHitboxGlobal.x, sonicHitboxGlobal.y, sonicHitboxGlobal.width, sonicHitboxGlobal.height);
            if (globalSensor.intersects(globalSonic)){
                collision();
            }
        */
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

    public int getLimit(direction d){
        return directionalLimit.get(d);
    }
}
