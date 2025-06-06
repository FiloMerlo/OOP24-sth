package objects;
import javax.swing.JPanel;
import game_parts.TileType;
import javax.awt.Rectangle;
public class Tile extends JPanel {
    private Rectangle space;
    private TileType type;
    
    public Tile(TileType t){
        type = t;
        space = new Rectangle(0, 0, 5, 5);
    }

    public TileType getType(){ return type; }
    public Rectangle getSpace(){ return space; }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g); 
        g.setColor(Color.BLUE);
        g.drawRect(space.x, space.y, space.width, space.height);
    }
}