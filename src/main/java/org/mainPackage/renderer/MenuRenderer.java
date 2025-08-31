package org.mainPackage.renderer;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;


/**
 * Renderer for the main menu.
 * <p>
 * This class handles the rendering of the background, the main title, the animated logo,
 * and the blinking instruction text. It supports responsive font sizing based on window
 * dimensions and smooth animations such as scrolling backgrounds and breathing effects.
 * <p>
 * This class implements the Renderer interface and provides methods to draw
 * the menu state on the screen.
 */

public class MenuRenderer implements Renderer {

    private Font title, instruction, logo;
    
    // --- Screen dimensions used to calculate responsive font size ---
    private int currentWidth, currentHeight;
    
    private BufferedImage background;
    
    // --- Animation and state variables ---
    private int backgroundScroll = 0;
    private boolean showInstruction = true;
    private long startTime;
    private long lastBlinkTime = 0;
  
    // ---  Responsive scaling constants ---
    private static final float TITLE_FONT_HEIGHT = 0.12f;
    private static final float INSTRUCTION_FONT_HEIGHT = 0.04f;
    private static final float LOGO_FONT_HEIGHT = 0.10f;
    
    private static final float TITLE_FONT_WIDTH = 0.08f;
    private static final float INSTRUCTION_FONT_WIDTH = 0.025f;
    private static final float LOGO_FONT_WIDTH = 0.07f;

    // --- Color constants ---
    private static final Color TITLE_COLOR = Color.YELLOW;
    private static final Color INSTRUCTION_COLOR = Color.BLACK;
    private static final Color LOGO_COLOR = Color.WHITE;
    
    // --- Animation constants ---
    private static final int SCROLL_SPEED = 1;
    private static final long BLINK_TIME = 500; 
    private static final float BREATH_STRENGTH = 0.1f;
    private static final float BREATH_SPEED = 5.0f;
    private static final float LOGO_DEFAULT_SCALA = 1.0f;
    
    // --- Resource path ---
    private static final String FONT_PATH = "/font/NiseSegaSonic.TTF";
    private static final String BACKGROUND_PATH = "/backgrounds/menu_background.jpg";

    // --- Constants for positioning and centering ---
    private static final int CENTER_DEFAULT = 2;
    private static final int TITLE_CENTER_Y = 5;
    private static final float LOGO_POSITION_FACTOR = 2.7f;

    private static final double NANOSECONDS_PER_SECOND = 1_000_000_000.0;

    /**
     * Constructor for MenuRenderer.
     * Initializes the renderer by loading the font and background image.
     */

    public MenuRenderer() {
        startTime = System.nanoTime();
        loadFont();
        loadBackground();
    }
    
    /**
     * Loads the custom font from resources.
     * <p>
     * If the font is not found, defaults to Arial.
     */

    private void loadFont(){
        try (InputStream importFont = getClass().getResourceAsStream(FONT_PATH)){
            if (importFont == null){
                throw new IOException("FONT NOT FOUND");
            }
            Font myFont = Font.createFont(Font.TRUETYPE_FONT, importFont);
            title = myFont.deriveFont(Font.BOLD, 72f); 
            logo =myFont.deriveFont(Font.BOLD, 72f);
            instruction = myFont.deriveFont(Font.PLAIN, 24f);
        }
         catch (Exception e) {
            System.out.println("FONT DEFAULT " + e.getMessage());
            title = new Font("Arial", Font.BOLD, 72);
            logo = new Font("Arial", Font.BOLD, 72);
            instruction = new Font("Arial", Font.PLAIN, 24);
        }
    }
    
    /**
     * Loads the background image from resources.
     * <p>
     * If the image is not found, sets the background to null and prints an error message.
     */

    private void loadBackground(){
         try (InputStream importImagine = getClass().getResourceAsStream(BACKGROUND_PATH)) {
            if (importImagine == null) {
                throw new IOException("BACKGROUND NOT FOUND");
            }
            background = ImageIO.read(importImagine);
            
        } catch (Exception e) {
            System.out.println("BACKGROUND NOT FOUND" + e.getMessage());
            background = null;
        }
    }
    
    /**
     * Updates the animation state for the menu.
     * <p>
     * Handles background scrolling and the blinking instruction text.
     */

    public void updateAnimation() {
        backgroundScroll -= SCROLL_SPEED;

        // --- Reset scrolling when background fully leaves the screen ---
        if (background != null && backgroundScroll <= -background.getWidth()) { 
            backgroundScroll = 0;
        }
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastBlinkTime > BLINK_TIME) {
            showInstruction = !showInstruction;
            lastBlinkTime = currentTime;
        }
    }

    /**
     * Updates the font sizes proportionally to the current screen dimensions.
     *
     * @param width  Current window width
     * @param height Current window height
     */

    private void updateFontSize(int width, int height){
        
        if (width != currentWidth || height != currentHeight) {
            currentWidth = width;
            currentHeight = height;
            
            float newTitle = Math.min((height * TITLE_FONT_HEIGHT), (width * TITLE_FONT_WIDTH));
            float newLogo = Math.min((height * LOGO_FONT_HEIGHT), (width * LOGO_FONT_WIDTH));
            float newInstruction = Math.min((height * INSTRUCTION_FONT_HEIGHT), (width * INSTRUCTION_FONT_WIDTH));
            
            title = title.deriveFont(newTitle);
            logo = logo.deriveFont(newLogo);
            instruction = instruction.deriveFont(newInstruction);
        }
    }

    /**
     * Draws the background image or a solid color if the image is not available.
     *
     * @param g2d    Graphics2D context for drawing
     * @param width  Current window width
     * @param height Current window height
     */

    private void drawBackground(Graphics2D g2d, int width, int height) {
        if (background != null) {
            g2d.drawImage(background, backgroundScroll, 0, null);
            g2d.drawImage(background, backgroundScroll + background.getWidth(), 0, null);
        } else {
            g2d.setColor(Color.BLACK);
            g2d.fillRect(0, 0, width, height);
        }
    }

    /**
     * Draws the main title "SONIC" at the top of the screen.
     *
     * @param g2d    Graphics2D context for drawing
     * @param width  Current window width
     * @param height Current window height
     */

    private void drawTitle(Graphics2D g2d, int width, int height) {
        g2d.setFont(title);
        g2d.setColor(TITLE_COLOR);
        
        String title = "SONIC";
        FontMetrics metrics = g2d.getFontMetrics();
        
        int titleX = (width - metrics.stringWidth(title)) / CENTER_DEFAULT; 
        int titleY = (height / TITLE_CENTER_Y) + metrics.getAscent();
        
        g2d.drawString(title, titleX, titleY);
    }
    
    /**
     * Renders the animated game logo with a "breathing" effect.
     * 
     * <p>This method calculates the animation scale factor to simulate
     * a smooth expansion and contraction of the logo over time,
     * creating the illusion of breathing. The scale oscillates between
     * {@code 1 - BREATH_STRENGTH} and {@code 1 + BREATH_STRENGTH},
     * giving the logo a dynamic and fluid appearance.</p>
     * 
     * <p>The calculated scale is applied to the graphics context before
     * rendering the logo image, ensuring the animation is visually
     * centered and natural.</p>
     */

    private void drawLogo(Graphics2D g2d, int width, int height) {
        g2d.setFont(logo);
        g2d.setColor(LOGO_COLOR);
        
        String logoText = "the Hedgehog";
        FontMetrics metrics = g2d.getFontMetrics();
        
        double timeInSeconds = (System.nanoTime() - startTime) / NANOSECONDS_PER_SECOND;
        float scale = LOGO_DEFAULT_SCALA + (BREATH_STRENGTH * (float) Math.sin(timeInSeconds * BREATH_SPEED));
        
        AffineTransform originalTransform = g2d.getTransform();
        
        int centerX = width / CENTER_DEFAULT;
        float centerY = (height / LOGO_POSITION_FACTOR);
        g2d.translate(centerX, centerY);
        g2d.scale(scale, scale);
        
        int textX = -metrics.stringWidth(logoText) / CENTER_DEFAULT;
        int textY = metrics.getAscent() / CENTER_DEFAULT;
        g2d.drawString(logoText, textX, textY);
        
        g2d.setTransform(originalTransform);
    }
     
    /**
     * Draws the blinking "Press ENTER to start" instruction at 3/4 of the screen height.
     */
    
     private void drawInstructions(Graphics2D g2d, int width, int height) {
        if (showInstruction) {
            g2d.setFont(instruction);
            g2d.setColor(INSTRUCTION_COLOR);
            
            String message = "Press ENTER to start";
            FontMetrics metrics = g2d.getFontMetrics();
            
            int messageX = (width - metrics.stringWidth(message)) / CENTER_DEFAULT;
            int messageY = (height * 3) / 4;
            
            g2d.drawString(message, messageX, messageY);
        }
    }

    /**
     * Main rendering method.
     * <p>
     * Draws all menu elements: background, title, animated logo, and blinking instructions.
     * Also updates font sizes according to current screen dimensions.
     *
     * @param g2d   The graphics context
     * @param width The width of the rendering area
     * @param height The height of the rendering area
     */
    
    @Override
    public void render(Graphics2D g2d, int width, int height) {
        drawBackground(g2d, width, height);
        drawTitle(g2d, width, height);
        drawLogo(g2d, width, height);
        drawInstructions(g2d, width, height);
        updateFontSize(width, height);
    }
}
