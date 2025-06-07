package renderer;



import java.awt.*;
//import java.awt.event.MouseEvent; 
import java.io.InputStream;

public class PausedRenderer implements Renderer {

    private Font pauseFont;
    
    
    public PausedRenderer() {
        pauseFont = loadFont("/res/fonts/NiseSegaSonic.TTF", 36f);
    }

    private Font loadFont(String path, float size) {
        try (InputStream is = getClass().getResourceAsStream(path)) {
            if (is != null) {
                Font customFont = Font.createFont(Font.TRUETYPE_FONT, is);
                GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(customFont);
                return customFont.deriveFont(Font.BOLD, size);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Font("Arial", Font.BOLD, (int) size);
    }

    @Override
    public void render(Graphics2D g2d, int width, int height) { // Riceve width, height
        
        g2d.setColor(new Color(0, 0, 0, 150));
        g2d.fillRect(0, 0, width, height);

        // Scritta "PAUSED"
        drawCenteredText(g2d, "PAUSED", pauseFont, Color.WHITE, width, height / 4);

        // Disegno del pulsante Exit           
        /* 
        int buttonWidth = (int) (width * 0.2);
        int buttonHeight = (int) (height * 0.1);
        int buttonX = (width - buttonWidth) / 2;
        int buttonY = (int) (height * 0.7); 

        
        g2d.setColor(Color.YELLOW);

        g2d.fillRect(buttonX, buttonY, buttonWidth, buttonHeight);
        
        int exitFontSize = (int) (0.05 * Math.min(width, height));
        drawCenteredText(g2d, "EXIT", pauseFont.deriveFont(Font.PLAIN, (float)Math.max(16, exitFontSize)), Color.BLACK, 
                         new Rectangle(buttonX, buttonY, buttonWidth, buttonHeight));

     */
    }
    



    //Metodi di aiuto
    private void drawCenteredText(Graphics2D g2d, String text, Font font, Color color, int width, int y) {
        g2d.setFont(font);
        g2d.setColor(color);
        int textWidth = g2d.getFontMetrics().stringWidth(text);
        g2d.drawString(text, (width - textWidth) / 2, y);
    }

    private void drawCenteredText(Graphics2D g2d, String text, Font font, Color color, Rectangle rect) {
        g2d.setFont(font);
        g2d.setColor(color);
        FontMetrics fm = g2d.getFontMetrics();
        int x = rect.x + (rect.width - fm.stringWidth(text)) / 2;
        int y = rect.y + (rect.height + fm.getAscent()) / 2;
        g2d.drawString(text, x, y);
    }
    

    /* 
    public void render(Graphics2D g2d, int width, int height, boolean isHoveringExit) {
        
        g2d.setColor(isHoveringExit ? Color.RED : Color.YELLOW);
        int buttonWidth = (int) (width * 0.2);
        int buttonHeight = (int) (height * 0.1);
        int buttonX = (width - buttonWidth) / 2;
        int buttonY = (int) (height * 0.7); 
        g2d.fillRect(buttonX, buttonY, buttonWidth, buttonHeight);

        int exitFontSize = (int) (0.05 * Math.min(width, height));
        drawCenteredText(g2d, "EXIT", pauseFont.deriveFont(Font.PLAIN, (float)Math.max(16, exitFontSize)), Color.BLACK, new Rectangle(buttonX, buttonY, buttonWidth, buttonHeight));
    }
        */
}
