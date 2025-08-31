package org.mainPackage.renderer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

/**
 * PausedRenderer is responsible for rendering the paused state of the game.
 * It displays a "PAUSED" message and a background image.
 * <p>
 * This class implements the Renderer interface and provides methods to draw
 * the paused state on the screen.
 */

public class PausedRenderer implements Renderer {

    private Font pauseFont;
    private Font exitButtonFont;
    
    // --- Screen dimensions used to calculate responsive font size ---
    private int currentWidth, currentHeight;
    
    private BufferedImage pausedBackground;
    
    // --- Resource path ---
    private static final String FONT_PAUSED = "/font/NiseSegaSonic.TTF";
    private static final String BACKGROUND_PAUSED ="/backgrounds/paused_background.jpg";

    // --- Design responsive ---
    private static final float PAUSE_FONT_HEIGHT = 0.1f;
    private static final float PAUSE_FONT_WIDTH = 0.075f;
    private static final float BUTTON_FONT_HEIGHT = 0.04f;
    private static final float BUTTON_FONT_WIDTH = 0.025f;

    private static final Color PAUSED_COLOR = Color.WHITE;
    
    /**
     * Constructor for PausedRenderer.
     * Initializes the fonts and loads the paused background image.
     */

    public PausedRenderer() {
        try (InputStream importFont = getClass().getResourceAsStream(FONT_PAUSED)) {
            if (importFont == null) {
                throw new IOException("FONT NOT FOUND" + FONT_PAUSED);
            }
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, importFont);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(customFont);
            pauseFont = customFont.deriveFont(Font.BOLD, 60f); 
            exitButtonFont = customFont.deriveFont(Font.PLAIN, 24f);
        } catch (Exception e) {
            System.err.println("Default font" + e.getMessage());
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
    
    /*
     * Updates the font size based on the current screen dimensions.
     */

    private void updateFontSize(int width, int height){
        if (width != currentWidth || height != currentHeight) {
            currentWidth = width;
            currentHeight = height;
            
            float newPauseFont = Math.min((height * PAUSE_FONT_HEIGHT), (width * PAUSE_FONT_WIDTH));
            float newExitButtonFont = Math.min(height * BUTTON_FONT_HEIGHT, width * BUTTON_FONT_WIDTH);
            
            // --- Derive new fonts with updated sizes ---
            pauseFont = pauseFont.deriveFont(newPauseFont);
            exitButtonFont = exitButtonFont.deriveFont(newExitButtonFont);
        }
    }

    /**
     * Main render method that draws the paused state.
     * <p>
     * It draws the background and the "PAUSED" message.
     * 
     * @param g2d   The graphics context
     * @param width The width of the rendering area
     * @param height The height of the rendering area
     */

    @Override
    public void render(Graphics2D g2d, int width, int height) {
        drawBackgroundPaused(g2d, width, height);
        drawPaused(g2d, width, height); 
        updateFontSize(width, height);
    }

    /**
     * Draws the paused background image.
     * <p>
     * The image is scaled to fit the current screen dimensions while maintaining
     * its aspect ratio.
     */
 
    private void drawBackgroundPaused (Graphics2D g2d, int width, int height){
        
        if (pausedBackground != null) {
            int imgWidth = pausedBackground.getWidth();
            int imgHeight = pausedBackground.getHeight();

            double scaleX = (double) width / imgWidth;
            double scaleY = (double) height / imgHeight;
            // --- Calculate the maximum scale factor to maintain aspect ratio ---
            double scale = Math.max(scaleX, scaleY);

            int scaledWidth = (int) (imgWidth * scale);
            int scaledHeight = (int) (imgHeight * scale);

            // --- Center the image ---
            int x = (width - scaledWidth) / 2;
            int y = (height - scaledHeight) /2;

            g2d.drawImage(pausedBackground, x, y, scaledWidth, scaledHeight, null);
        }
    }

    /**
     * Draws the "PAUSED" message in the center of the screen.
     * <p>
     * The message is drawn using the pauseFont and is centered both horizontally
     * and vertically.
     */

    private void drawPaused (Graphics2D g2d, int width, int height){
        g2d.setFont(pauseFont);
        g2d.setColor(PAUSED_COLOR);

        String pauseFont = "PAUSED";
        FontMetrics metrics = g2d.getFontMetrics();

        int pauseX = (width - metrics.stringWidth(pauseFont))/ 2;
        int pauseY = (height / 4) + metrics.getAscent();

        g2d.drawString(pauseFont, pauseX, pauseY);
    }

    /**
     * Draws centered text within a specified rectangle.
     * <p>
     * This method calculates the position to center the text both horizontally
     * and vertically within the given rectangle.
     *
     * @param g2d   The graphics context
     * @param text  The text to draw
     * @param font  The font to use for the text
     * @param color The color of the text
     * @param rect  The rectangle within which to center the text
     */

    public void drawCenteredText(Graphics2D g2d, String text, Font font, Color color, Rectangle rect) {
        g2d.setFont(font);
        g2d.setColor(color);
        FontMetrics fm = g2d.getFontMetrics();
        int x = rect.x + (rect.width - fm.stringWidth(text)) / 2;
        int y = rect.y + (rect.height + fm.getAscent()) / 2 - fm.getDescent();
        g2d.drawString(text, x, y);
    }

    /**
     * Returns the font used for the "PAUSED" message.
     * <p>
     * This method is used to retrieve the font for external use, such as
     * rendering in other components or for testing purposes.
     *
     * @param width  The current window width
     * @param height The current window height
     * @return The font used for the "PAUSED" message
     */

    public Font getExitButtonFont(int width, int height){
        return exitButtonFont;
    }
}



