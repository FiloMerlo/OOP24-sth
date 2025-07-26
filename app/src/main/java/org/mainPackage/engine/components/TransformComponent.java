package org.mainPackage.engine.components;

public class TransformComponent implements Component{
    private float x, y, width, height;
    
    public TransformComponent(float x, float y, float width, float height){
        this.x = x; 
        this.y = y;   
        this.width = width;
        this.height = height;
    }
    public float getX(){
        return this.x;
    }
    public float getY(){
        return this.y;
    }
    public void moveX(float x){
        this.x += x;
    }
    public void moveY(float y){
        this.y += y;
    }
    public float getWidth(){
        return this.width;
    }
    public float getHeight(){
        return this.height;
    }

    public void setX(float x){
        this.x = x;
    }

    public void setY(float y){
        this.y = y;
    }
    
    /**
     * Does nothing, the only {@link Component} which does not require to be {@link update}d
     */
    @SuppressWarnings(value = { "Passive component" })
    public void update(float deltaTime) {
    }

}
