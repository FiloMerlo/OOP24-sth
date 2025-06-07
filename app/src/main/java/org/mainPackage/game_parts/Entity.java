package org.mainPackage.game_parts;
import org.mainPackage.components.*;

import java.util.HashMap;
public class Entity {
    private HashMap<ComponentType, Component> components = new HashMap<>();
    private int xPos, yPos, width, height;
    private boolean deleted = false;

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

    public void moveX(int distance){
        xPos += distance;
    }
    public void moveY(int distance){
        yPos += distance;
    }

    public Component getComponent(ComponentType type){
        return components.get(type);
    }
    public int getWidth(){ return width; }
    public int getHeight(){ return height; }
    public int getX(){ return xPos; }
    public int getY(){ return yPos; }

}
