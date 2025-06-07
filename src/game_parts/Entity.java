package game_parts;
import javax.awt.Rectangle;
import components;
public class Entity {
    private HashMap<ComponentType, Component> components = new HashMap<>();
    private boolean deleted = false;
    private int xPos, yPos, width, height;

    public Entity(int x, int y, int w, int h, HashMap<ComponentType, Component> c){
        xPos = x;
        yPos = y;
        width = w;
        height = h;
       components = c;
    }

    public void delete(){
        deleted = true;
    }

    public void updateComponents(){
        for (Component comp : components.values()){
            comp.update();
        }
    }
    public void getComponent(ComponentType type){
        return components.get(type);
    }
    public int getWidth(){ return width; }
    public int getHeight(){ return height; }
    public int getX(){ return xPos; }
    public int getY(){ return yPos; }
}
