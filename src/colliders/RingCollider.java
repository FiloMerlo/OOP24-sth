package colliders;
import entities.Ring;
import entities.Sonic;

public class RingCollider extends Collider{
    private Sonic sonic;
    public RingCollider(ArrayList<Tile> list, Ring r, Sonic s) {
        super(r.getWidth(), r.getHeight(), list, r);
        sonic = s;
    }

    public void checkCollisions(){
        /*Check if it has been picked up by sonic */
        if (sensor.intersects(sonic.getSensor())){
            pickUp();
        } else{ /*Check if it collides with a surface. 
                if so, it bounces */
            for (Tile t : tiles) {
                if (sensor.intersects(t.getSpace())) {
                    collision();
                }
            }
        }
    }
    public void collision(){ /*collision must be used because it's an abstract method */
        (Ring(owner)).getPhysics().bounce();
    }
    public void pickUp(){
        sonic.gotRing();
        owner.die();
    }
}
