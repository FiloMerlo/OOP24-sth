import javax.naming.TimeLimitExceededException;

import org.dyn4j.geometry.Rectangle;

public class EnemyCollider {
    private Sonic sonic;
    private Entity enemy;
    private Rectangle sensor;
    public EnemyCollider(Entity e, Sonic s){
        enemy = e;
        sonic = s;
    }

    @Override
    public void check(){
        if (sensor.intersects(s.getSensor())){
            entity.collision();
        }
    }
}
