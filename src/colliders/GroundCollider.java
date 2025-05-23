package colliders;
import game_parts.direction;
public class GroundCollider extends Collider {
    public GroundCollider(int x, int y, int width, int height, ArrayList<Floor> list, Sonic sonic) {
        super(x, y, width, height, list, sonic);
    }
    @Override
    public void collision(){
        super.collision();
        //sonic deve smettere di cadere
        sonic.setMovement(down, false);
    }
    @Override
    public void collEnded(){
        super.collEnded();
        sonic.setMovement(down, true);
    }
}
