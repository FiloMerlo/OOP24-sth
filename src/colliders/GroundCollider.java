package colliders;
public class GroundCollider extends Collider {
    public GroundCollider(int x, int y, int width, int height, ArrayList<Floor> list, Sonic sonic) {
        super(x, y, width, height, list, sonic);
    }
    @Override
    public void collision(){
        super.collision();
        //sonic deve smettere di cadere
        ((Sonic)entity).setMovement("down", flase);
        ((Sonic)entity).grounded(); //azzera la velocità verticale di sonic
    }
    @Override
    public void collEnded(){
        super.collEnded();
        ((Sonic)entity).setMovement("down", true);
        ((Sonic)entity).fall();
    }
}
