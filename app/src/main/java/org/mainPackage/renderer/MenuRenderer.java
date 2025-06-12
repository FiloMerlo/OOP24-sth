package org.mainPackage.renderer;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

/* Renderer per il menu principale 
 * Si occupa del renderer del background e delle scritte animate
 */

public class MenuRenderer implements Renderer {

    private Font title, instruction, logo;
    private int currentWidth, currentHeight;
    private BufferedImage background;
    
    
    /* Variabili di update */
    private int backgroundScroll = 0;
    private boolean showInstruction = true;
    private long startTime;
    private long lastBlinkTime = 0;
  
    /* Dimensioni responsive */
    private static final float TITLE_FONT_HEIGHT = 0.12f;
    private static final float INSTRUCTION_FONT_HEIGHT = 0.04f;
    private static final float LOGO_FONT_HEIGHT = 0.10f;
    
    private static final float TITLE_FONT_WIDTH = 0.08f;
    private static final float INSTRUCTION_FONT_WIDTH = 0.025f;
    private static final float LOGO_FONT_WIDTH = 0.07f;

    /* Colori */
    private static final Color TITLE_COLOR = Color.YELLOW;
    private static final Color INSTRUCTION_COLOR = Color.BLACK;
    private static final Color LOGO_COLOR = Color.WHITE;
    
    /* Animazioni */
    private static final int SCROLL_SPEED = 1;
    private static final long BLINK_TIME = 500; 
    private static final float BREATH_STRENGTH = 0.1f;
    private static final float BREATH_SPEED = 5.0f;
    private static final float LOGO_DEFAULT_SCALA = 1.0f;
    
    /* Path dei file */
    private static final String FONT_PATH = "resources/font/NiseSegaSonic.TTF";
    private static final String BACKGROUND_PATH = "resources/backgrounds/menu_background.jpg";

    /* Costanti */
    private static final int CENTER_DEFAULT = 2;
    private static final int TITLE_CENTER_Y = 5;
    private static final float LOGO_POSITION_FACTOR = 2.7f;

    public MenuRenderer() {
        startTime = System.nanoTime();
        loadFont();
        loadBackground();
    }
        
    private void loadFont(){
        try (InputStream importFont = getClass().getResourceAsStream(FONT_PATH)){
            if (importFont == null){
                throw new IOException("FONT NON TROVATO");
            }
            Font myFont = Font.createFont(Font.TRUETYPE_FONT, importFont); //creo il font da usare a partire dal file.ttf
            /* creo delle nuove istanze di un oggetto font con modifche specifiche */
            title = myFont.deriveFont(Font.BOLD, 72f); 
            logo =myFont.deriveFont(Font.BOLD, 72f);
            instruction = myFont.deriveFont(Font.PLAIN, 24f);
        }
         catch (Exception e) {
            System.out.println("IMPOSTO FONT DI DEFAULT " + e.getMessage());
            title = new Font("Arial", Font.BOLD, 72);
            logo = new Font("Arial", Font.BOLD, 72);
            instruction = new Font("Arial", Font.PLAIN, 24);
        }
    }
    
    private void loadBackground(){
         try (InputStream importImagine = getClass().getResourceAsStream(BACKGROUND_PATH)) {
            if (importImagine == null) {
                throw new IOException("SFONDO NON TROVATO");
            }
            background = ImageIO.read(importImagine);
            
        } catch (Exception e) {
            System.out.println("SFONDO NON IMPOSTATO" + e.getMessage());
            background = null;
        }
    }
    
    
    public void updateAnimation() {
        backgroundScroll -= SCROLL_SPEED;
        /* Controlla se background esiste e se è uscito completamente dal lato sinistro dello schermo */
        if (background != null && backgroundScroll <= -background.getWidth()) { 
            backgroundScroll = 0;
        }
        /* Animazione lampeggio */
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastBlinkTime > BLINK_TIME) {
            showInstruction = !showInstruction;
            lastBlinkTime = currentTime;
        }
    }


    /* Aggiornamento dei font in base alla dimensione dello schermo */

    private void updateFontSize(int width, int height){
        
        if (width != currentWidth || height != currentHeight) { //if in realtà potrebbe non servire
            currentWidth = width;
            currentHeight = height;

            // Calcola le nuove dimensioni basate sull'altezza dello schermo
            float newTitle = Math.min((height * TITLE_FONT_HEIGHT), (width * TITLE_FONT_WIDTH));
            float newLogo = Math.min((height * LOGO_FONT_HEIGHT), (width * LOGO_FONT_WIDTH));
            float newInstruction = Math.min((height * INSTRUCTION_FONT_HEIGHT), (width * INSTRUCTION_FONT_WIDTH));
            // Crea nuovi font con le dimensioni calcolate
            title = title.deriveFont(newTitle);
            logo = logo.deriveFont(newLogo);
            instruction = instruction.deriveFont(newInstruction);
        }
    }

    private void drawBackground(Graphics2D g2d, int width, int height) {
        if (background != null) {
            g2d.drawImage(background, backgroundScroll, 0, null);
            g2d.drawImage(background, backgroundScroll + background.getWidth(), 0, null);
        } else {
            g2d.setColor(Color.BLACK);
            g2d.fillRect(0, 0, width, height);
        }
    }

    private void drawTitle(Graphics2D g2d, int width, int height) {
        g2d.setFont(title);
        g2d.setColor(TITLE_COLOR);
        
        String title = "SONIC";
        FontMetrics metrics = g2d.getFontMetrics(); //serve per calcolare le dimensioni del testo
        
        /* Centra il titolo orizzontalmente */
        int titleX = (width - metrics.stringWidth(title)) / CENTER_DEFAULT; 
        /* drawString allinea il testo sulla linea di base, quindi aggiungo l'ascent
        /per posizionare correttamente la parte visibile del testo
        */
        int titleY = (height / TITLE_CENTER_Y) + metrics.getAscent(); // Posiziona al 25% dell'altezza
        
        g2d.drawString(title, titleX, titleY);
    }

          
        
        

    
    /**
     * Disegna il logo "the Hedgehog" con animazione di respirazione
     */
    private void drawLogo(Graphics2D g2d, int width, int height) {
        g2d.setFont(logo);
        g2d.setColor(LOGO_COLOR);
        
        String logoText = "the Hedgehog";
        FontMetrics metrics = g2d.getFontMetrics();
        
    /**
     * Calcola la scala di animazione per l'effetto "respirazione" del logo.
     * 
     * Il valore di scala oscilla nel tempo tra 
     * 1 - BREATH_STRENGTH e 1 + BREATH_STRENGTH, simulando un'animazione fluida
     * di espansione e contrazione.
     * 
     * @return valore di scala corrente da applicare alla trasformazione grafica
     */
        double timeInSeconds = (System.nanoTime() - startTime) / 1_000_000_000.0; //tempo passato dall'inizio dell'animazione
        float scale = LOGO_DEFAULT_SCALA + (BREATH_STRENGTH * (float) Math.sin(timeInSeconds * BREATH_SPEED));
        
        // Salva la trasformazione corrente
        AffineTransform originalTransform = g2d.getTransform();
        
        // Applica la trasformazione di scala centrata
        int centerX = width / CENTER_DEFAULT;
        float centerY = (height / LOGO_POSITION_FACTOR);
        g2d.translate(centerX, centerY);
        g2d.scale(scale, scale);
        
        // Disegna il testo centrato
        int textX = -metrics.stringWidth(logoText) / CENTER_DEFAULT;
        int textY = metrics.getAscent() / CENTER_DEFAULT;
        g2d.drawString(logoText, textX, textY);
        
        // Ripristina la trasformazione originale
        g2d.setTransform(originalTransform);
    }

 
    
    /**
     * Disegna le istruzioni lampeggianti
     */
    private void drawInstructions(Graphics2D g2d, int width, int height) {
        if (showInstruction) {
            g2d.setFont(instruction);
            g2d.setColor(INSTRUCTION_COLOR);
            
            String message = "Press ENTER to start";
            FontMetrics metrics = g2d.getFontMetrics();
            
            // Centra il messaggio orizzontalmente e posiziona a 3/4 dell'altezza
            int messageX = (width - metrics.stringWidth(message)) / CENTER_DEFAULT;
            int messageY = (height * 3) / 4;
            
            g2d.drawString(message, messageX, messageY);
        }
    }


    @Override
     public void render(Graphics2D g2d, int width, int height) {
        
        drawBackground(g2d, width, height);
        drawTitle(g2d, width, height);
        drawLogo(g2d, width, height);
        drawInstructions(g2d, width, height);
        updateFontSize(width, height);
    }

}
