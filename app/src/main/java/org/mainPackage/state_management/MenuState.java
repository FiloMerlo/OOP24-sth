package org.mainPackage.state_management;

import java.awt.*;

import java.awt.event.KeyEvent;

import org.mainPackage.util.SizeView;
import org.mainPackage.engine.systems.GameStateManager;
import org.mainPackage.renderer.MenuRenderer;


/**
 * Represents the menu state of the game.
 * <p>
 * This state is responsible for displaying the menu screen, updating menu animations,
 * and handling user input specific to the menu (e.g., starting the game with ENTER).
 * All rendering and animation logic is delegated to {@link MenuRenderer}.
 * </p>
 */

public class MenuState extends GameState {
    private MenuRenderer menuRenderer;
   
    /**
     * Constructs a new {@code MenuState}.
     *
     * @param gameStateManager the manager responsible for handling state transitions
     * @param sizeView utility for screen dimensions and responsive scaling
     */

    public MenuState(GameStateManager gameStateManager, SizeView sizeView) {
        super(gameStateManager,sizeView);
        menuRenderer = new MenuRenderer();
    }
   
    /**
     * Updates the menu state.
     * <p>
     * Currently, this method updates the menu animations via the {@link MenuRenderer}.
     * No game logic or entity updates are performed in this state.
     * </p>
     */
    
    @Override
    public void update() {
       menuRenderer.updateAnimation();
    }

    /**
     * Draws the menu on the screen.
     * <p>
     * Rendering is delegated to {@link MenuRenderer}, which handles all visual
     * elements such as title, background, and animated components.
     * </p>
     *
     * @param g the {@link Graphics} context to draw on
     */

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int panelWidth = sizeView.getSizeWidth(); 
        int panelHeight = sizeView.getSizeHeight();
        menuRenderer.render(g2d, panelWidth, panelHeight); 
    }

    /**
     * Handles key press events for the menu.
     * <p>
     * Pressing ENTER transitions the game from {@code MenuState} to {@code PlayingState}.
     * </p>
     *
     * @param e the {@link KeyEvent} representing the key press
     */
    
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) 
        gameStateManager.setState(GameStateManager.State.PLAYING);
    }
    
    /**
     * Handles key release events for the menu.
     * <p>
     * This is primarily kept for debugging purposes and to maintain interface
     * consistency with other game states.
     * </p>
     *
     * @param e the {@link KeyEvent} representing the key release
     */

    @Override
    public void keyReleased(KeyEvent e) {
        //System.out.println("DEBUG: MenuState - keyReleased: " + KeyEvent.getKeyText(e.getKeyCode()));
    }
}
