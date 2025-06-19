package org.mainPackage.renderer;


/* Sistemare i magic number */
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class PausedRenderer implements Renderer {

    private Font pauseFont;
    private Font exitButtonFont;
    private int currentWidth, currentHeight;
    private BufferedImage pausedBackground;
    
    private static final String FONT_PAUSED = "/font/NiseSegaSonic.TTF";
    private static final String BACKGROUND_PAUSED ="/backgrounds/paused_background.jpg";

    /* Design responsive */
    private static final float PAUSE_FONT_HEIGHT = 0.1f;
    private static final float PAUSE_FONT_WIDTH = 0.075f;
    private static final float BUTTON_FONT_HEIGHT = 0.04f;
    private static final float BUTTON_FONT_WIDTH = 0.025f;

    private static final Color PAUSED_COLOR = Color.WHITE;
    
    
    public PausedRenderer() {
        
        try (InputStream importFont = getClass().getResourceAsStream(FONT_PAUSED)) {
            if (importFont == null) {
                throw new IOException("FONT NON TROVATO" + FONT_PAUSED);
            }
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, importFont);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(customFont);
            
            // Inizializza i font con una dimensione base, verranno aggiornati da updateFontSizes
            pauseFont = customFont.deriveFont(Font.BOLD, 60f); 
            exitButtonFont = customFont.deriveFont(Font.PLAIN, 24f);

        } catch (Exception e) {
            System.err.println("Imposto default" + e.getMessage());
            pauseFont = new Font("Arial", Font.BOLD, 60);
            exitButtonFont = new Font("Arial", Font.PLAIN, 24);
        }

        try (InputStream importImagine = getClass().getResourceAsStream(BACKGROUND_PAUSED)) {
            if (importImagine != null) {
                pausedBackground = ImageIO.read(importImagine);
            }
        } catch (IOException e) {
            System.err.println("Failed to load paused background image: " + e.getMessage());
        }
    }
    
    
    private void updateFontSize(int width, int height){
        if (width != currentWidth || height != currentHeight) { //if in realtà potrebbe non servire
            currentWidth = width;
            currentHeight = height;

            // Calcola le nuove dimensioni basate sull'altezza dello schermo
            float newPauseFont = Math.min((height * PAUSE_FONT_HEIGHT), (width * PAUSE_FONT_WIDTH));
            float newExitButtonFont = Math.min(height * BUTTON_FONT_HEIGHT, width * BUTTON_FONT_WIDTH);
            
            // Crea nuovi font con le dimensioni calcolate
            pauseFont = pauseFont.deriveFont(newPauseFont);
            exitButtonFont = exitButtonFont.deriveFont(newExitButtonFont);
            System.out.println("Font aggiornato");
        }
    }



    @Override
    public void render(Graphics2D g2d, int width, int height) {
        drawBackgroundPaused(g2d, width, height);
        drawPaused(g2d, width, height); 
        updateFontSize(width, height);
    }

 
    private void drawBackgroundPaused (Graphics2D g2d, int width, int height){
        
        if (pausedBackground != null) {
            int imgWidth = pausedBackground.getWidth();
            int imgHeight = pausedBackground.getHeight();

            double scaleX = (double) width / imgWidth;
            double scaleY = (double) height / imgHeight;

            // Usa il fattore di scala maggiore per "coprire" l'immagine
            double scale = Math.max(scaleX, scaleY);

            int scaledWidth = (int) (imgWidth * scale);
            int scaledHeight = (int) (imgHeight * scale);

            // Centra l'immagine 
            
            int x = (width - scaledWidth) / 2;
            int y = (height - scaledHeight) /2;

            g2d.drawImage(pausedBackground, x, y, scaledWidth, scaledHeight, null);
        }
    }

    private void drawPaused (Graphics2D g2d, int width, int height){
        g2d.setFont(pauseFont);
        g2d.setColor(PAUSED_COLOR);

        String pauseFont = "PAUSED";
        FontMetrics metrics = g2d.getFontMetrics();

        int pauseX = (width - metrics.stringWidth(pauseFont))/ 2;
        int pauseY = (height / 4) + metrics.getAscent();

        g2d.drawString(pauseFont, pauseX, pauseY);
    }


    

    public void drawCenteredText(Graphics2D g2d, String text, Font font, Color color, Rectangle rect) {
        g2d.setFont(font);
        g2d.setColor(color);
        FontMetrics fm = g2d.getFontMetrics();
        int x = rect.x + (rect.width - fm.stringWidth(text)) / 2;
        int y = rect.y + (rect.height + fm.getAscent()) / 2 - fm.getDescent(); // Aggiustamento per centrare meglio verticalmente
        g2d.drawString(text, x, y);
    }



    public Font getExitButtonFont(int width, int height){
        return exitButtonFont;
    }
}



