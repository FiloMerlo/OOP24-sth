package sth.engine.components;

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
    @Override
    public void update(float deltaTime) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

}
