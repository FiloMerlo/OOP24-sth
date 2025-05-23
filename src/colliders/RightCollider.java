package colliders;
import game_parts.direction;

public class RightCollider extends Collider {
    public RightCollider(int x, int y, int width, int height, ArrayList<Wall> list, Sonic sonic) {
        super(x, y, width, height, list, sonic);
    }

    @Override
    public void collision(){
        super.collision();
        sonic.setMovement(right, false);
    }
        @Override
    public void collEnded(){
        super.collENded();
        sonic.setMovement(right, true);
    }
}
