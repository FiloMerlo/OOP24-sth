package colliders;
import entities.Sonic;
import entities.Tile;
import game_parts.direction;
public class PlayerCollider extends Collider {
    private boolean colliding = false;
    private direction direction;
    public PlayerCollider(ArrayList<Tile> list, Sonic s, direction d, float offX, float offY) {
        super(1, 1, list, s);
        direction = d;
        offsetX = offX;
        offsetY = offY;
    }

    public void checkCollisions() {
        for (Tile t : tiles) {
            /*SE I COLLISORI NON FUNZIONANO COME DOVREBBERO, PROVATE PRIMA DI TUTTO A DECOMMENTARE QUESTA PARTE DI CODICE E COMMENTARE L'IF ATTUALMENTE IN USO 
            Point sonicGlobal = SwingUtilities.convertPoint(physic.getBody(), physic.getHitbox().getLocation(), physic.getBody().getOwner().getFrame());
            Rectangle sonicRect = new Rectangle(sonicGlobal.x, sonicGlobal.y, sonicGlobal.width, sonicGlobal.height);
            if (sonicRect.intersects(t.getSpace()) && !colliding) {
                collision();
            } else if (!sonicRect.intersects(t.getSpace()) && colliding){
                collisionEnded();
            }
        */
            if (sensor.intersects(t.getSpace()) && !colliding) {
                collision();
            } else if (!sensor.intersects(t.getSpace()) && colliding){
                collisionEnded();
            }
        }
    }
    public void collision(){
        colliding = true;
        physic.setMovement(direction, false);
    }
    public void collEnded(){
        colliding = false;
        physic.setMovement(direction, true);
    }

    @Override
    public void update(){
        sensor.setLocation(owner.getX() + offsetX, owner.getY() + offsetY);
    }
}
