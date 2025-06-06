package game_parts;
import javax.awt.Rectangle;
public class GamePanel extends JPanel{
    private static final int tileWidth = 5;
    private static final int tileHeight = 5;
    private static final int nColumns = 10;
    private static final int nRows = 5;
    private ArrayList<Rectangle> tiles;
    private int[][] grid = {
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    }
    private BufferedImage tileImage;

    public GamePanel() {
        setPreferredSize(new Dimension(nColumns * tileWidth, nRows * tileHeight));
        setFocusable(true);
    }
    
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        /*Draw background*/
        g2.setColor(Color.CYAN);
        g2.fillRect(0, 0, getWidth(), getHeight());
        /*Draw tiles*/
        for (int r = 0; r < nRows; r++) {
            for (int c = 0; c < nColumns; c++) {
              if (grid[r][c] == 1) {
                int tileX = c * tileWidth;
                int tileY = r * tileHeight;
                space = new Rectangle(tileX, tileY, tileWidth, tileHeight);
                tiles.add(space);

                g2.setColor(Color.LIGHT_GRAY);
                g2.fill(space);
                if (tileImage != null) {
                    g2.drawImage(tileImage, tileX, tileY, tileWidth, tileHeight, null);
                }
              }
            }
        }   
    }

    public ArrayList<Rectangle> getTileList(){ return tiles }
}
