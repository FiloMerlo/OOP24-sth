package colliders;
import entities.Sonic;
import entities.Tile;
import game_parts.direction;
public class PlayerCollider extends Collider {
    private boolean colliding = false;
    private direction direction;
    public PlayerCollider(ArrayList<Tile> list, Sonic s, direction d) {
        super(1, 1, list, s);
        direction = d;
    }

    public void checkCollisions() {
        for (Tile t : tiles) {
            if (sensor.intersects(t.getSpace()) && !colliding) {
                collision();
            } else if (!sensor.intersects(t.getSpace()) && colliding){
                collisionEnded();
            }
        }
    }
    public void collision(){
        colliding = true;
        ((Sonic)owner).getPhysics().setMovement(direction, false);
    }
    public void collEnded(){
        colliding = false;
        ((Sonic)owner).getPhysics().setMovement(direction, true);
    }
}
