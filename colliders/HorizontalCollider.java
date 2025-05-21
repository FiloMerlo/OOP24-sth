
package colliders;
public enum direction {left, right};
public class HorizontalCollider extends Collider{
    direction dir;
    public HorizontalCollider(int x, int y, int width, int height, ArrayList<Wall> list, Sonic sonic) {
        super(x, y, width, height, list, sonic);
    }
    @Override
    public void collision(){
        super.collision();
        dir = ((Sonic)entity).getDirection();
        if (dir == left){
            ((Sonic)entity).setMovement("left", false);
        } else {
            ((Sonic)entity).setMovement("right", false);
        }
    }
    @Override
    public void collEnded(){
        super.collENded();
        dir = ((Sonic)entity).getDirection();
        if (dir == left){
            ((Sonic)entity).setMovement("right", true);
        } else {
            ((Sonic)entity).setMovement("left", true);
        }
    }
}
