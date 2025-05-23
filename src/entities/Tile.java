package entities;
import game_parts.TileType;
import org.dyn4j.geometry.Rectangle;
public class Tile {
    private TileType type;
    private Rectangle space;
    
    public Tile(TileType t, float height, float width){
        this.type = t;
        space = new Rectangle(width, height);
    }

    public getType(){
        return type;
    }
    public getSpace(){
        return space;
    }
}