package org.mainPackage.engine.components;

public class TransformComponent implements Component{
    private int x, y, width, height;
    
    public TransformComponent(int x, int y, int width, int height){
        this.x = x; 
        this.y = y;   
        this.width = width;
        this.height = height;
    }
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    public void moveX(int x){
        this.x += x;
    }
    public void moveY(int y){
        this.y += y;
    }
    public int getWidth(){
        return this.width;
    }
    public int getHeight(){
        return this.height;
    }
    @SuppressWarnings(value = { "Passive component" })
    public void update(float deltaTime) {
        // Does nothing, 'passive' component
    }

}
