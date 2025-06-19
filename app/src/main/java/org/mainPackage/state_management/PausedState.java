package org.mainPackage.state_management;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent; 

import org.mainPackage.util.SizeView;
import org.mainPackage.renderer.PausedRenderer;



public class PausedState extends GameState {
    
    private PausedRenderer pausedRenderer;
    private Rectangle exitButtonBounds;
    
    private static final Color BUTTON_COLOR_DEFAULT = Color.YELLOW; // Colore di default per il bottone
    private static final Color BUTTON_COLOR_HOVER = Color.RED;     // Colore quando ci si passa sopra
    private static final Color BUTTON_TEXT_COLOR = Color.BLACK;
    
    private boolean hoveringExit = false; 
    
    
    public PausedState(GameStateManager gameStateManager, SizeView sizeView) {
        super(gameStateManager, sizeView);
        System.out.println("PausedState inizializzato.");
        pausedRenderer = new PausedRenderer();

    }
    
    @Override
    public void update() {}
    
    @Override
    public void draw(Graphics g) { 
      
     
            Graphics2D g2d = (Graphics2D) g;
            int panelWidth = sizeView.getSizeWidth(); 
            int panelHeight = sizeView.getSizeHeight();
            
           
           
            pausedRenderer.render(g2d, panelWidth, panelHeight);
            drawExitButton(g2d, panelWidth, panelHeight);
        }
    
    private void drawExitButton(Graphics2D g2d, int panelWidth, int panelHeight) {
            int buttonWidth = (int) (panelWidth * 0.2);
            int buttonHeight = (int) (panelHeight * 0.1);
            int buttonX = (panelWidth - buttonWidth) / 2;
            int buttonY = (int) (panelHeight * 0.7); 
       
            exitButtonBounds = new Rectangle(buttonX, buttonY, buttonWidth, buttonHeight); 

            g2d.setColor(hoveringExit ? BUTTON_COLOR_HOVER : BUTTON_COLOR_DEFAULT);
            g2d.fillRect(exitButtonBounds.x, exitButtonBounds.y, exitButtonBounds.width, exitButtonBounds.height);
            
            // Disegna il bordo del bottone (opzionale)
            g2d.setColor(BUTTON_TEXT_COLOR); // Colore del bordo
            g2d.drawRect(exitButtonBounds.x, exitButtonBounds.y, exitButtonBounds.width, exitButtonBounds.height);

            // Disegna il testo "EXIT" centrato nel bottone
            // Devi recuperare il font corretto dal PausedRenderer o definirlo qui.
            // Se PausedRenderer ha un getter per exitButtonFont:
            Font exitFont = pausedRenderer.getExitButtonFont(panelWidth, panelHeight); // Assicurati che questo getter esista e sia pubblico
            pausedRenderer.drawCenteredText(g2d, "EXIT", exitFont, BUTTON_TEXT_COLOR, exitButtonBounds); 
    }   
    
    





    /* Metodi per gli input  */
    @Override
    public void keyPressed(KeyEvent e) { 
        if (e.getKeyCode() == KeyEvent.VK_P) 
        gameStateManager.setState(GameStateManager.State.PLAYING);
        //gameStateManager.getGameLoop().resumeLoop();
    }

    @Override
    public void mouseMoved(MouseEvent e){
        
        if (exitButtonBounds != null) {
            hoveringExit = exitButtonBounds.contains(e.getPoint());
            
            System.out.println(hoveringExit);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (exitButtonBounds != null && exitButtonBounds.contains(e.getPoint())) {
            System.out.println("si");
            gameStateManager.gameShutdown();
        }
    } 
}
