package colliders;
import entities.Ring;
import entities.Sonic;

public class RingCollider extends Collider{
    private PlayerPhysics sonicPh;
    public RingCollider(ArrayList<Tile> list, RingPhysics rP, PlayerPhysics s) {
        super(list, rP);
        sonicPh = s;
        sensor = physic.getHitbox();
    }

    public void checkCollisions(){
        /*SE I COLLISORI NON FUNZIONANO COME DOVREBBERO, PROVATE PRIMA DI TUTTO A DECOMMENTARE QUESTA PARTE DI CODICE E COMMENTARE L'IF ATTUALMENTE IN USO 
            Point ringGlobal = SwingUtilities.convertPoint(physic.getBody(), sensor.getLocation(), physic.getBody().getOwner().getFrame());
            Point sonicGlobal = SwingUtilities.convertPoint(sonic.getComponent(Body.class), sonic.getComponent(Physics.class).getHitbox().getLocation(), sonic.getFrame());
            Rectangle sonicRect = new Rectangle(sonicGlobal.x, sonicGlobal.y, sonicGlobal.width, sonicGlobal.height);
            Rectangle ingRect = new Rectangle(ringGlobal.x, ringGlobal.y, ringGlobal.width, ringGlobal.height);
            if (ringRect.intersects(sonicRect)){
                pickUp();
            } else {
             for (Tile t : tiles) {
                if (ringRect.intersects(t.getSpace())) {
                collision();
            }
            }
            }
        */
        if (sonic.getComponent(Physics.class).getHitbox().intersects(sensor)) {
            pickUp();
        } else {
            for (Tile t : tiles) {
                if (sensor.intersects(t.getSpace())) {
                    physic.bounce();//bounce
                }
            }
        }
    }
    public void pickUp(){
        sonic.getComponent(Physics.class).gotRing();
        physic.kill();
    }
}
