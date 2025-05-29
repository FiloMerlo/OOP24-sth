package components.PhysicsTypes;
public class RingPhysics extends Physics {
    private RingCollider collider;
    public RingPhysics(MovableBody b) {
        super(0, 0, b);
    }

    public void bounce(){
        xSpeed = xSpeed * (-1);
        ySpeed = ySpeed * (-1);
    }

    public void spredOut(){
        /*Quando sonic viene colpito, creiamo tanti anelli quanti quelli nel campo Sonic.rings, e ciascun anello lancia questo metodo così inizia a muoversi. 
        Eventualmente ci vorrebbe una distanza massima di movimento*/
        //Imposta velocità randomica (separatemente per x e y, così che l'anello possa muoversi anche diagonalmente)
    }

    public void update(){
        body.moveX(xSpeed);
        body.moveY(ySpeed);
        collider.checkCollisions();
    }
}
