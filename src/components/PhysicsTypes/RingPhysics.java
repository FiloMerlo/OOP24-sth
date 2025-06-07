package components.PhysicsTypes;
public class RingPhysics extends Physics {
    private RingCollider collider;
    public RingPhysics(MovableBody b) {
        super(0, 0, b);
        /*collider = new RingCollider(TODO deve passare la lista di tiles, this, this.getBody().getOwner().getManager().getEnList().get(0);*/
    }

    public void bounce(){
        xSpeed = xSpeed * (-1);
        ySpeed = ySpeed * (-1);
    }

    public void spredOut(){
        xSpeed = (Math.random() * 4 - 0);
        ySpeed = (Math.random() * 4 - 0);
    }

    public void update(){
        body.moveX(xSpeed);
        body.moveY(ySpeed);
        collider.checkCollisions();
    }
}
