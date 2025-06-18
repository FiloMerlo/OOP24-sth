package  org.mainPackage.engine.components;

public class TransformComponent implements Component{
    private float x, y;
    
    public TransformComponent(float x, float y){
        this.x = x; 
        this.y = y;   
    }
    public float getX(){
        return this.x;
    }
    public float getY(){
        return this.y;
    }

    @SuppressWarnings(value = { "Passive component" })
    public void update(float deltaTime) {
        // Does nothing, 'passive' component
    }

}
