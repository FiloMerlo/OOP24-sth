package colliders;
import components.PhysicsTypes.PlayerPhysics;
import game_parts.direction;
public class PlayerCollider extends Collider {
    private boolean colliding = false;
    private direction direction;
    public PlayerCollider(ArrayList<Rectangle> list, PlayerPhysics s, direction d, int offX, int offY) {
        super(, list, s);
        direction = d;
        offsetX = offX;
        offsetY = offY;
    }

    public void checkCollisions() {
        for (Rectangle t : tiles) {
            if (sensor.intersects(t) && !colliding) {
                collisionStarted();
            } else if (!sensor.intersects(t) && colliding){
                collisionEnded();
            }
        }
    }
    public void collisionStarted(){
        colliding = true;
        physic.setMovement(direction, false);
    }
    public void collEnded(){
        colliding = false;
        physic.setMovement(direction, true);
    }
}
