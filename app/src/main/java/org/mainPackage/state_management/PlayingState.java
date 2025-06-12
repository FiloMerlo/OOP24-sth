package org.mainPackage.state_management;

import java.awt.*;
import java.awt.event.KeyEvent;

import org.mainPackage.util.SizeView;

/* Character classe locale per debug
 * il render dello stato è delegato al PlayingRenderer
 */

public class PlayingState extends GameState {

    private Character character; 
    private int left = -1; 
    private int right = 1; 
    
    
    
    public PlayingState(GameStateManager gameStateManager, SizeView sizeView) {
        super(gameStateManager, sizeView);
        System.out.println("PlayingState inizializzato.");
        character = new Character('a');
    }
    
    @Override
    public void update() {
        // character.update(); // Chiama il metodo update del personaggio

    }
    
    @Override
    public void draw(Graphics g) { 
        // g.drawString("GIOCO IN CORSO...", 150, 100); //debug
        // character.draw(g);
    }
    
   @Override
    public void keyPressed(KeyEvent e) {
        
        // switch (e.getKeyCode()) {
        //     case KeyEvent.VK_A:
        //         character.moveX(left); // Muovi a sinistra
        //         break;
        //     case KeyEvent.VK_D:
        //         character.moveX(right); // Muovi a destra
        //         break;
        //     case KeyEvent.VK_SPACE:
        //         character.jump(); // Fai saltare il personaggio
        //         break;
        //     case KeyEvent.VK_P: // Tasto Pausa
        //         gameStateManager.setState(GameStateManager.State.PAUSED);
        //         //gameStateManager.getGameLoop().pauseLoop();
                
        //     break;
            
        //     default:
               
        //     break;
        // }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
        // switch (e.getKeyCode()) {
        //     case KeyEvent.VK_A:
        //         character.brake(); 
        //         break;
        //     case KeyEvent.VK_D:
        //         character.brake(); 
        //         break;
        //     default:
               
        //     break;
        // }
    }
  

}