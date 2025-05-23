package colliders;
import game_parts.direction;

public class LeftCollider extends Collider {
    public LeftCollider(int x, int y, int width, int height, ArrayList<Wall> list, Sonic sonic) {
        super(x, y, width, height, list, sonic);
    }

    @Override
    public void collision(){
        super.collision();
        sonic.setMovement(left, false);
    }
        @Override
    public void collEnded(){
        super.collENded();
        sonic.setMovement(left, true);
    }
}

