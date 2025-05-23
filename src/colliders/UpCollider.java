package colliders;
import org.checkerframework.checker.units.qual.s;

public class UpCollider extends Collider{
    public UpCollider(int x, int y, int width, int height, ArrayList<Ceiling> ceilings, Sonic sonic) {
        super(x, y, width, height, ceilings, sonic);
    }
    @Override
    public void collision(){
        super.collision();
        //Sonic ha battuto la testa sul soffitto! quindi deve iniziare a scendere
        System.out.println("Ouch! Ho sbattuto la testa ...");
        sonic.setMovement(up, false);
        sonic.fall();
    }
    @Override
    public void collEnded(){
        super.collEnded();
        sonic.setMovement(up, true);
    }
}
