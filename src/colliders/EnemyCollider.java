package colliders;
import javax.naming.TimeLimitExceededException;
import game_parts.action;
import entities.Enemy;
import entities.Sonic;
import org.dyn4j.geometry.Rectangle;

public class EnemyCollider extends Collider{
    private Sonic sonic;
    public EnemyCollider(ArrayList<Tile> list, Enemy enemy, Sonic s){
        super(enemy.getWidth(), enemy.getHeight(), list, enemy);
        sonic = s;
    }

    @Override
    public void checkCollisions(){
        if (sensor.intersects(s.getSensor())){
            collision();
        }
    }
    @Override
    public void collision(){
        if(s.getPlayerAction() == jumping){
            owner.die();
        } else {
            sonic.takeDamage();
        }
    }
}
