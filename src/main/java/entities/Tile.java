package objects;

import javax.swing.JPanel;
import game_parts.TileType;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Tile extends JPanel {
    private Rectangle space;
    private TileType type;
    private BufferedImage sprite;
    
    public Tile(TileType t){
        type = t;
        space = new Rectangle(0, 0, 5, 5);
        
       
        try {
            sprite = ImageIO.read(new File("ground.png"));
        } catch (IOException e) {
            System.err.println(e.getMessage());
            sprite = null;
        }
    }
    
    public TileType getType(){ 
        return type; 
    }
    
    public Rectangle getSpace(){ 
        return space; 
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        /* 
         *  g.setColor(Color.BLUE);
            g.drawRect(space.x, space.y, space.width, space.height);
        */
       
        
        
        if (sprite != null) {
            g.drawImage(sprite, space.x, space.y, getWidth(), getHeight(), this);
        }
    }
}